package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.example.grade_predictor.HelloApplication;

import java.io.IOException;

public class PredictGradeController {
    @FXML
    protected void handleHome() {
        try{
            HelloApplication.switchToHomePage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Home page");
        }
    }

    @FXML
    protected void handleProfile() {
        showAlert("Profile", "Profile page is under construction.");
    }

    @FXML
    protected void handleSettings() {
        showAlert("Settings", "Settings page is under construction.");
    }

    @FXML
    protected void handleLogout() {
        showAlert("Log Out", "You have been logged out.");
        // Implement log out logic here
    }

    @FXML
    protected void goToEditUnit() {
        try {
            HelloApplication.switchToEditUnitPage();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
        }
    }

    @FXML
    protected void goToPredictGrade() {
            showAlert(" Predict Grade","You are already on Predict Grade Page");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
