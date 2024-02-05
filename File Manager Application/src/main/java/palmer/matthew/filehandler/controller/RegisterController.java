/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
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

/**
 * Controller for handling user registration in a JavaFX application.
 */
public class RegisterController {

  @FXML
  private TextField userNameField;

  @FXML
  private TextField emailField;

  @FXML
  private TextField userIdField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private PasswordField retypedPasswordField;

  @FXML
  private Button registerButton;

  private Stage fileManagerStage;

  private AuthenticationService authService;

  /**
   * Initializes the controller class and the authentication service.
   */
  public RegisterController() {
    // Initialize the authentication service with DAO implementations.
    this.authService =
        new AuthenticationService(new UserCredentialDAOImpl(), new UserInfoDAOImpl());
  }

  @FXML
  private void initialize() {
    // other initialization for the controller.
  }

  public void setFileManagerStage(Stage homePageStage) {
    this.fileManagerStage = homePageStage;
  }

  /**
   * Handles the registration button click event. Validates the input fields and registers the user
   * if validation passes.
   *
   * @param event The action event triggered by clicking the register button.
   */
  @FXML
  private void registerUser(ActionEvent event) {
    try {
      if (validateInput()) {
        boolean registrationSuccess = authService.registerUser(userIdField.getText(),
            userNameField.getText(), emailField.getText(), passwordField.getText());
        if (registrationSuccess) {
          showAlert("Registration Successful", "The user has been registered successfully.");
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          SessionManager.getInstance().login(userIdField.getText());
          fileManagerStage.show();
          stage.close();
        } else {
          showAlert("Registration Failed", "An error occurred during registration.");
        }
      }
    } catch (Exception e) {
      showAlert("Registration Failed", e.getMessage());
    }
  }

  /**
   * Validates the user input in the registration form.
   *
   * @return true if the input is valid, otherwise false.
   */
  private boolean validateInput() {
    if (userIdField.getText().isEmpty() || emailField.getText().isEmpty()
        || userNameField.getText().isEmpty() || passwordField.getText().isEmpty()
        || !passwordField.getText().equals(retypedPasswordField.getText())) {

      showAlert("Validation Error",
          "Please ensure all fields are filled correctly and passwords match.");
      return false;
    }
    return true;
  }

  /**
   * Shows an alert dialog with the specified title and content.
   *
   * @param title The title of the alert dialog.
   * @param content The content message of the alert dialog.
   */
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
