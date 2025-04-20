package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.User;

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

    private final SqliteUserDAO userDAO = new SqliteUserDAO();
    private boolean isSignUpMode = false;

    @FXML
    protected void handleSignUp() {
        if (!isSignUpMode) {
            // Show additional fields for sign up
            firstNameField.setVisible(true);
            lastNameField.setVisible(true);
            phoneField.setVisible(true);
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
