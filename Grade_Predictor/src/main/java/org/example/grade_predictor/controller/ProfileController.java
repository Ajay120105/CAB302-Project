package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;

import java.io.IOException;

public class ProfileController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Button saveButton;

    private final SqliteUserDAO userDAO = new SqliteUserDAO();
    private User currentUser;

    // Services
    private final AuthenticateService authenticateService;
    private final EnrollmentService enrollmentService;

    public ProfileController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.enrollmentService = new EnrollmentService(authenticateService);
    }

    @FXML
    public void initialize() {
        currentUser = authenticateService.getCurrentUser();
        if (currentUser != null) {
            firstNameField.setText(currentUser.getFirst_name());
            lastNameField.setText(currentUser.getLast_name());
            emailField.setText(currentUser.getEmail());
            phoneField.setText(currentUser.getPhone());
        } else {
            showAlert("Error", "No user logged in.");
        }
    }

    @FXML
    public void handleSaveChanges() {
        if (currentUser == null) {
            showAlert("Error", "Cannot update profile—user not found.");
            return;
        }

        // Update user info
        currentUser.setFirst_name(firstNameField.getText());
        currentUser.setLast_name(lastNameField.getText());
        currentUser.setEmail(emailField.getText());
        currentUser.setPhone(phoneField.getText());

        // Save updates to the database
        try {
            userDAO.updateUser(currentUser);
            showAlert("Success", "Profile updated successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to update profile.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleProfile() {
        showAlert("Profile", "You are already on the Profile page.");
    }

    @FXML
    protected void handleSettings() {
        showAlert("Settings", "Settings page is under construction.");
    }

    @FXML
    protected void handleLogout() {
        authenticateService.logoutUser();
        showAlert("Log Out", "You have been logged out.");

        // Redirect to the first page (signup/login)
        try {
            HelloApplication.switchToSignup_LoginPage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Signup/Login page.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleHome() {
        try {
            HelloApplication.switchToHomePage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Home page");
        }
    }

    @FXML
    protected void goToEditUnit() {
        try {
            HelloApplication.switchToEditUnitPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
        }
    }

    @FXML
    protected void goToPredictGrade() {
        try {
            HelloApplication.switchToPredictGradePage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Predict Grade page.");
        }
    }

    public void goToAllUnits(ActionEvent actionEvent) {
        try {
            HelloApplication.switchToAllUnitsPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open All Units page.");
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
