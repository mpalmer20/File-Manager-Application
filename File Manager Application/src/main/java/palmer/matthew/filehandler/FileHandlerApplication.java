package palmer.matthew.filehandler;

import static palmer.matthew.filehandler.util.ResourceUtils.FILE_MANAGER_FXML_PATH;
import static palmer.matthew.filehandler.util.ResourceUtils.PRIMARY_FXML_PATH;
import static palmer.matthew.filehandler.util.ResourceUtils.REGISTER_USER_FXML_PATH;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import palmer.matthew.filehandler.controller.FileManagerController;
import palmer.matthew.filehandler.controller.PrimaryController;
import palmer.matthew.filehandler.controller.RegisterController;

/**
 * JavaFX FileHandlerApplication
 */
public class FileHandlerApplication extends Application {

  private static final Logger LOGGER = Logger.getLogger(FileHandlerApplication.class.getName());

  @Override
  public void start(Stage primaryStage) throws IOException {

    try {

      Stage fileManagerStage = new Stage();
      FXMLLoader fileManagerLoader = new FXMLLoader(getClass().getResource(FILE_MANAGER_FXML_PATH));
      Parent fileManagerComponent = fileManagerLoader.load();
      Scene fileManagerScene = new Scene(fileManagerComponent);
      fileManagerStage.setScene(fileManagerScene);
      fileManagerStage.setTitle("Register a New User");
      LOGGER.log(Level.INFO, "User register controller initialized successfully");
      FileManagerController fileManagerController = fileManagerLoader.getController();
      fileManagerStage.setOnShown(event -> fileManagerController.onstageShown());


      Stage registerUserStage = new Stage();
      FXMLLoader registerWindowLoader =
          new FXMLLoader(getClass().getResource(REGISTER_USER_FXML_PATH));
      Parent registerComponent = registerWindowLoader.load();
      Scene scene = new Scene(registerComponent);
      registerUserStage.setScene(scene);
      registerUserStage.setTitle("Register a New User");
      RegisterController registerController = registerWindowLoader.getController();
      registerController.setFileManagerStage(fileManagerStage);

      LOGGER.log(Level.INFO, "User register controller initialized successfully");

      // Load the primary layout from FXML file
      FXMLLoader primaryWindowLoader = new FXMLLoader(getClass().getResource(PRIMARY_FXML_PATH));
      Parent primaryWindowRoot = primaryWindowLoader.load();
      // Set the scene to the stage
      Scene primaryWindowScene = new Scene(primaryWindowRoot);
      primaryStage.setScene(primaryWindowScene);
      primaryStage.setMaximized(true);
      PrimaryController controller = primaryWindowLoader.getController();

      controller.setRegisterUserStage(registerUserStage);
      controller.setFileManagerStage(fileManagerStage);
      fileManagerController.setPrimaryStage(primaryStage);
      primaryStage.setTitle("Generate PDF For Insurance Payments");
      primaryStage.setResizable(true);
      // Show the primary stage
      primaryStage.show();


    } catch (

    Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void init() {}

  @Override
  public void stop() {}

  public static void main(String[] args) {
    launch(args);
  }
}
