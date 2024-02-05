package palmer.matthew.filehandler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import palmer.matthew.filehandler.persistence.impl.UserCredentialDAOImpl;
import palmer.matthew.filehandler.persistence.impl.UserInfoDAOImpl;
import palmer.matthew.filehandler.service.AuthenticationService;
import palmer.matthew.filehandler.service.SessionManager;

public class PrimaryController {

  @FXML
  private Button registerBtn;

  @FXML
  private TextField userIdField;

  @FXML
  private PasswordField passPasswordField;

  private Stage registerStage;

  private Stage fileManagerStage;

  private AuthenticationService authService;

  public PrimaryController() {
    // Initialize the authentication service with DAO implementations.
    this.authService =
        new AuthenticationService(new UserCredentialDAOImpl(), new UserInfoDAOImpl());
  }

  @FXML
  private void initialize() {
    // Initialization logic if needed.
  }

  public void setRegisterUserStage(Stage registerUserStage) {
    this.registerStage = registerUserStage;
  }

  public void setFileManagerStage(Stage homePageStage) {
    this.fileManagerStage = homePageStage;
  }

  @FXML
  private void registerBtnHandler(ActionEvent event) throws Exception {
    registerStage.show();
  }

  @FXML
  private void loginBtnHandler(ActionEvent event) {
    String userId = userIdField.getText();
    String password = passPasswordField.getText();

    // Authenticate the user
    boolean loginSuccess = authService.loginUser(userId, password);
    if (loginSuccess) {
      showAlert("Login Success", userId + " : " + password);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      SessionManager.getInstance().login(userId);
      fileManagerStage.show();
      stage.close();
    } else {
      // Login failed: show an alert
      showAlert("Login Failed", "Invalid username or password. Please try again.");
    }
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

}
