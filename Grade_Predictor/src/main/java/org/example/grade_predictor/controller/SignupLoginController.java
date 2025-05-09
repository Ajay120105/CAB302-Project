package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.UserSession;

import java.io.IOException;

public class SignupLoginController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logInButton;

    private final SqliteUserDAO userDAO = new SqliteUserDAO();
    private boolean isSignUpMode = false;

    @FXML
    protected void handleSignUp() {
        if (!isSignUpMode) {
            // Show additional fields for sign up
            firstNameField.setVisible(true);
            lastNameField.setVisible(true);
            phoneField.setVisible(true);
            firstNameLabel.setVisible(true);
            lastNameLabel.setVisible(true);
            phoneLabel.setVisible(true);

            // Move email and password fields down
            emailLabel.setLayoutY(226);
            emailField.setLayoutY(227);
            passwordLabel.setLayoutY(266);
            passwordField.setLayoutY(267);

            // Move buttons down
            signUpButton.setLayoutY(329);
            logInButton.setLayoutY(367);

            isSignUpMode = true;
        } else {
            // Handle sign up logic
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert("Error", "All fields must be filled out.");
                return;
            }

            User newUser = new User(firstName, lastName, email, phone, password);
            userDAO.addUser(newUser);

            // Store the newly signed-up user as the current user.
            UserSession.setCurrentUser(newUser);

            showAlert("Success", "Sign Up successful!");
            try {
                HelloApplication.switchToHomePage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleLogIn() {
        if (isSignUpMode) {
            // Hide additional fields for log in
            firstNameField.setVisible(false);
            lastNameField.setVisible(false);
            phoneField.setVisible(false);
            firstNameLabel.setVisible(false);
            lastNameLabel.setVisible(false);
            phoneLabel.setVisible(false);

            // Move email and password fields back up
            emailLabel.setLayoutY(106);
            emailField.setLayoutY(107);
            passwordLabel.setLayoutY(146);
            passwordField.setLayoutY(147);

            // Move buttons back up
            signUpButton.setLayoutY(209);
            logInButton.setLayoutY(247);

            isSignUpMode = false;
        } else {
            // Handle log in logic
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Email and password cannot be empty.");
                return;
            }

            User user = userDAO.getAllUsers().stream()
                    .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                // Set the current user in session
                UserSession.setCurrentUser(user);

                showAlert("Success", "Log In successful!");
                try {
                    HelloApplication.switchToHomePage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Log In failed. Invalid email or password.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}