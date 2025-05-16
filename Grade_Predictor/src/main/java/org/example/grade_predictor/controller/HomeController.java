package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;

import java.io.IOException;
import java.util.List;

public class HomeController {

    // Container for displaying enrolled units.
    @FXML
    private VBox unitsVBox;

    // Label for displaying a welcome message.
    @FXML
    private Label welcomeLabel;
    
    // Services
    private final AuthenticateService authenticateService;
    private final EnrollmentService enrollmentService;

    public HomeController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.enrollmentService = new EnrollmentService(authenticateService);
    }

    @FXML
    public void initialize() {
        // Retrieve the current user.
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser == null) {
            showAlert("Error", "No user logged in.");
            return;
        }
        // Set the welcome message.
        String fullName = currentUser.getFirst_name() + " " + currentUser.getLast_name();
        welcomeLabel.setText("Welcome, " + fullName + "!");
        
        // Display enrolled units using the service
        displayEnrolledUnits();
    }

    /**
     * Displays all enrolled units for the current user from their enrollments
     */
    private void displayEnrolledUnits() {
        Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
        
        if (firstEnrollment == null) {
            unitsVBox.getChildren().clear();
            unitsVBox.getChildren().add(new Label("You have not enrolled in any classes yet."));
            return;
        }
        
        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(firstEnrollment);
        
        unitsVBox.getChildren().clear();
        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            unitsVBox.getChildren().add(new Label("You have not enrolled in any classes yet."));
        } else {
            for (EnrolledUnit eu : enrolledUnits) {
                Node node = createEnrolledUnitNode(eu);
                unitsVBox.getChildren().add(node);
            }
        }
    }

    /**
     * Creates a read-only TitledPane for an enrolled unit.
     * This pane displays the unit code as its title and details (year, semester, weekly hours)
     * as its content.
     *
     * @param eu the enrolled unit whose information is displayed
     * @return a Node (TitledPane) representing the enrolled unit
     */
    private Node createEnrolledUnitNode(EnrolledUnit eu) {
        TitledPane pane = new TitledPane();
        pane.setText(eu.getUnit_code());
        String content = "Year: " + eu.getYear_enrolled() + "\n" +
                "Semester: " + eu.getSemester_enrolled() + "\n" +
                "Weekly Hours: " + eu.getWeekly_hours();
        pane.setContent(new Label(content));
        pane.setAnimated(false);
        return pane;
    }

    // Navigation methods

    @FXML
    protected void handleHome() {
        showAlert("Home", "You are already on the Home page.");
    }

    @FXML
    protected void handleProfile() {
        try{
            HelloApplication.switchToProfilePage();
        } catch (IOException e) {
            showAlert("Navigation Error", " Could not open profile page");
        }
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
