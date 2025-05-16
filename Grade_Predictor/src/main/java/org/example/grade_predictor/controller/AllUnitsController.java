package org.example.grade_predictor.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;
import org.example.grade_predictor.service.UnitService;

import java.io.IOException;
import java.util.List;

public class AllUnitsController {

    // Label for greeting the user
    @FXML
    private Label welcomeLabel;

    // VBox container defined in the FXML to list all available units.
    @FXML
    private VBox unitsVBox;

    private UnitService unitService;
    private AuthenticateService authenticateService;
    private EnrollmentService enrollmentService;

    public AllUnitsController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.unitService = new UnitService();
        this.enrollmentService = new EnrollmentService(authenticateService);
    }

    @FXML
    public void initialize() {
        // Retrieve the currently logged-in user.
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getFirst_name() + " " + currentUser.getLast_name();
            welcomeLabel.setText("Welcome, " + fullName + "!");
        } else {
            welcomeLabel.setText("Welcome!");
        }
        loadAllUnits();
    }

    /**
     * Loads all available units asynchronously from the UnitDAO and populates the VBox.
     */
    private void loadAllUnits() {
        Task<List<Unit>> loadTask = new Task<>() {
            @Override
            protected List<Unit> call() {
                return unitService.getAllUnits();
            }
        };

        loadTask.setOnSucceeded(event -> {
            List<Unit> units = loadTask.getValue();
            unitsVBox.getChildren().clear();
            if (units.isEmpty()) {
                unitsVBox.getChildren().add(new Label("No units available."));
            } else {
                for (Unit unit : units) {
                    // For each available unit, create an HBox row with details and an "Enroll" button.
                    unitsVBox.getChildren().add(createUnitRow(unit));
                }
            }
        });

        loadTask.setOnFailed(event -> {
            showAlert("Error", "Could not load available units.");
        });

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Creates a UI row (HBox) for a unit with a Label for unit info and an "Enroll" button.
     * When clicked, the current user is enrolled in that unit.
     */
    private Node createUnitRow(Unit unit) {
        // Declare row as an HBox.
        HBox row = new HBox(10);
        Label unitInfo = new Label(unit.getUnit_code() + ": " + unit.getUnit_name());
        Button enrollButton = new Button("Enroll");

        enrollButton.setOnAction(event -> {
            // Get the current user.
            User currentUser = authenticateService.getCurrentUser();
            if (currentUser == null) {
                showAlert("Error", "Please log in to enroll in units.");
                return;
            }
            
            Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
            if (firstEnrollment == null) {
                showAlert("Error", "You need to be enrolled in a program to add units.");
                return;
            }
            
            // TODO: Actual year and semester
            int defaultYear = 2025;
            int defaultSemester = 1;
            int defaultWeeklyHours = 5;
            
            EnrolledUnit enrolledUnit = enrollmentService.addEnrolledUnit(
                unit.getUnit_code(),
                defaultYear,
                defaultSemester,
                defaultWeeklyHours
            );
            
            if (enrolledUnit != null) {
                showAlert("Success", "Enrolled in " + unit.getUnit_code() + " successfully!");
            } else {
                showAlert("Error", "Failed to enroll in " + unit.getUnit_code());
            }
        });

        row.getChildren().addAll(unitInfo, enrollButton);
        return row;
    }

    @FXML
    protected void handleHome() {
        try {
            HelloApplication.switchToHomePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Home page.");
        }
    }

    @FXML
    protected void handleAllUnits() {
        showAlert("All Units", "You are already on the All Units page.");
    }

    @FXML
    protected void handleEditUnit() {
        try {
            HelloApplication.switchToEditUnitPage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Enrolled Units page.");
        }
    }

    @FXML
    protected void handleSettings() {
        showAlert("Settings", "Settings page is under construction.");
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
    protected void handleLogout() {
        authenticateService.logoutUser();
        showAlert("Log Out", "You have been logged out.");
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void goToPredictGrade() {
        try {
            HelloApplication.switchToPredictGradePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Predict Grade page.");
        }
    }
}
