package palmer.matthew.filehandler.controller;

import com.jcraft.jsch.ChannelSftp;
import java.io.File;
import java.nio.file.Files;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import palmer.matthew.filehandler.service.FileService;
import palmer.matthew.filehandler.service.SessionManager;
import palmer.matthew.filehandler.view.FileModel;

public class FileManagerController {

  private static final Logger LOGGER = Logger.getLogger(FileManagerController.class.getName());

  @FXML
  private TableView<FileModel> fileTable;
  @FXML
  private TableColumn<FileModel, String> fileNameColumn;
  @FXML
  private TableColumn<FileModel, String> fileSizeColumn;
  @FXML
  private TableColumn<FileModel, String> lastModifiedColumn;

  private ObservableList<FileModel> fileData = FXCollections.observableArrayList();


  private FileService fileService;

  private Stage primaryStage;

  public FileManagerController() throws Exception {
    this.fileService = new FileService();
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  @FXML
  public void initialize() {
    try {
      // Bind the table columns to the FileModel properties
      fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
      fileSizeColumn.setCellValueFactory(cellData -> cellData.getValue().fileSizeProperty());
      lastModifiedColumn
          .setCellValueFactory(cellData -> cellData.getValue().lastModifiedProperty());

      // Populate the table
      fileTable.setItems(fileData);
      listFiles();
    } catch (Exception e) {
      e.printStackTrace();
      // Handle exceptions (e.g., show an alert dialog)
    }
  }

  public void onstageShown() {
    try {
      listFiles();
    } catch (Exception e) {
      showAlert("Error listing user files for " + SessionManager.getInstance().getLoggedInUserId(),
          e.getMessage());
    }
  }

  @FXML
  private void handleLogout(ActionEvent event) {
    SessionManager.getInstance().logout();
    redirectToLoginView(event);
  }

  private void redirectToLoginView(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    primaryStage.show();
    stage.close();
  }

  @FXML
  private void handleUpload(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    Stage stage = (Stage) fileTable.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      // Upload the file
      LOGGER.log(Level.INFO, "Uploading: {}", file.getAbsolutePath());
      try {
        fileService.uploadFile(file.getName(), Files.readAllBytes(file.toPath()));
        listFiles();
        showAlert("File uploaded", file.getPath());
      } catch (Exception e) {
        e.printStackTrace();
        showAlert("File could not be uploaded", e.getMessage());
      }
    }
  }

  @FXML
  private void handleUpdate(ActionEvent event) {
    String selectedFile = fileTable.getSelectionModel().getSelectedItem().getFileName();
    if (selectedFile != null) {
      // Prompt user to select a new file to overwrite the selected one
      FileChooser fileChooser = new FileChooser();
      File newFile = fileChooser.showOpenDialog((Stage) fileTable.getScene().getWindow());

      if (newFile != null) {
        // Upload the file
        LOGGER.log(Level.INFO, "Updating file : {}", newFile.getAbsolutePath());
        try {
          fileService.uploadFile(newFile.getName(), Files.readAllBytes(newFile.toPath()));
          listFiles();
          showAlert("File updated", newFile.getPath());
        } catch (Exception e) {
          e.printStackTrace();
          showAlert("File could not be uploaded", e.getMessage());
        }
      }
    }
  }

  @FXML
  private void handleDelete(ActionEvent event) {
    String selectedFile = fileTable.getSelectionModel().getSelectedItem().getFileName();
    if (selectedFile != null) {
      showAlertConfirm(selectedFile);
    }
  }

  private void listFiles() throws Exception {
    if (!SessionManager.getInstance().isLoggedIn())
      return;
    fileData.clear();
    Vector<ChannelSftp.LsEntry> files = fileService.listFiles();
    for (ChannelSftp.LsEntry file : files) {
      if (file.getAttrs().isDir())
        continue;
      fileData.add(new FileModel(file.getFilename(), file.getAttrs().getSize() / 1000.00,
          file.getAttrs().getMtimeString()));
    }
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void showAlertConfirm(String fileName) {
    Alert confirmAlert =
        new Alert(Alert.AlertType.CONFIRMATION, fileName, ButtonType.YES, ButtonType.NO);
    confirmAlert.setHeaderText(null); // You can set a custom header or use null for no header
    confirmAlert.setTitle("Confirm deleting " + fileName); // Customize the title as needed

    // Show the alert and wait for the user's response
    confirmAlert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.YES) {
        try {
          fileService.deleteFile(fileName);
          listFiles();
        } catch (Exception e) {
          showAlert("Error deleting file " + fileName, e.getMessage());
        }
      } else {
        LOGGER.log(Level.INFO, "Delete cancelled by user for file {}", fileName);
      }
    });
  }
}
