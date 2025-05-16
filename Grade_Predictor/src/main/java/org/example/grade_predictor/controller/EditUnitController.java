package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;
import org.example.grade_predictor.service.UnitService;

import java.util.List;

public class EditUnitController {

    // Container for dynamically added enrollment panels.
    @FXML
    private VBox unitsVBox;

    // Label added to show welcome message, e.g., "Welcome, John Doe!"
    @FXML
    private Label welcomeLabel;
    
    // Services
    private final AuthenticateService authenticateService;
    private final EnrollmentService enrollmentService;
    private final UnitService unitService;

    public EditUnitController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.enrollmentService = new EnrollmentService(authenticateService);
        this.unitService = new UnitService();
    }

    @FXML
    public void initialize() {
        // Retrieve the currently logged-in user.
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getFirst_name() + " " + currentUser.getLast_name();
            welcomeLabel.setText("Welcome, " + fullName + "!");
            displayEnrolledUnits();
            
            addNewUnitComponent();
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

    /**
     * Displays all enrolled units for the current user from their enrollments
     */
    private void displayEnrolledUnits() {
        Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
        
        if (firstEnrollment == null) {
            unitsVBox.getChildren().clear();
            unitsVBox.getChildren().add(new Label("No enrolled units found."));
            return;
        }
        
        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(firstEnrollment);
        
        unitsVBox.getChildren().clear();
        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            unitsVBox.getChildren().add(new Label("No enrolled units found."));
        } else {
            for (EnrolledUnit eu : enrolledUnits) {
                TitledPane pane = createEnrolledUnitTitledPane(eu);
                unitsVBox.getChildren().add(pane);
            }
        }
    }
    
    /**
     * Adds a component to the UI for adding new enrolled units
     */
    private void addNewUnitComponent() {
        TitledPane addNewUnitPane = new TitledPane();
        addNewUnitPane.setText("Add New Unit");
        
        AnchorPane contentPane = new AnchorPane();
        
        TextField codeField = new TextField();
        codeField.setPromptText("Unit Code");
        codeField.setLayoutX(20);
        codeField.setLayoutY(14);
        
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        yearField.setLayoutX(20);
        yearField.setLayoutY(50);
        
        TextField semesterField = new TextField();
        semesterField.setPromptText("Semester");
        semesterField.setLayoutX(20);
        semesterField.setLayoutY(86);
        
        TextField hoursField = new TextField();
        hoursField.setPromptText("Weekly Hours");
        hoursField.setLayoutX(20);
        hoursField.setLayoutY(122);
        
        Button addButton = new Button("Add Unit");
        addButton.setLayoutX(150);
        addButton.setLayoutY(122);
        addButton.setOnAction(evt -> {
            try {
                String unitCode = codeField.getText();
                int year = Integer.parseInt(yearField.getText());
                int semester = Integer.parseInt(semesterField.getText());
                int weeklyHours = Integer.parseInt(hoursField.getText());
                
                if (unitCode == null || unitCode.trim().isEmpty()) {
                    showAlert("Input Error", "Unit code cannot be empty.");
                    return;
                }
                
                EnrolledUnit newUnit = enrollmentService.addEnrolledUnit(unitCode, year, semester, weeklyHours);
                if (newUnit != null) {
                    codeField.clear();
                    yearField.clear();
                    semesterField.clear();
                    hoursField.clear();
                    
                    // Refresh UI
                    displayEnrolledUnits();
                } else {
                    showAlert("Error", "Could not add the unit. Please check your enrollment status.");
                }
                
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numeric values for year, semester, and weekly hours.");
            }
        });
        
        contentPane.getChildren().addAll(codeField, yearField, semesterField, hoursField, addButton);
        
        addNewUnitPane.setContent(contentPane);
        addNewUnitPane.setAnimated(false);
        addNewUnitPane.setExpanded(false);
        
        unitsVBox.getChildren().add(0, addNewUnitPane);
    }

    /**
     * Creates a TitledPane for an enrolled unit, displaying key details.
     */
    private TitledPane createEnrolledUnitTitledPane(EnrolledUnit eu) {
        AnchorPane contentPane = new AnchorPane();

        // Create a TextField for unit code (if you'd like them to be editable).
        TextField codeField = new TextField(eu.getUnit_code());
        codeField.setLayoutX(20);
        codeField.setLayoutY(14);

        // Create a TextField for enrollment year.
        TextField yearField = new TextField(String.valueOf(eu.getYear_enrolled()));
        yearField.setLayoutX(20);
        yearField.setLayoutY(50);

        // Create a TextField for semester enrolled.
        TextField semesterField = new TextField(String.valueOf(eu.getSemester_enrolled()));
        semesterField.setLayoutX(20);
        semesterField.setLayoutY(86);

        // Create a TextField for weekly hours.
        TextField hoursField = new TextField(String.valueOf(eu.getWeekly_hours()));
        hoursField.setLayoutX(20);
        hoursField.setLayoutY(122);
        
        Button saveButton = new Button("Save");
        saveButton.setTextFill(Color.GREEN);
        saveButton.setLayoutX(150);
        saveButton.setLayoutY(86);
        saveButton.setOnAction(evt -> {
            try {
                String newUnitCode = codeField.getText();
                int newYear = Integer.parseInt(yearField.getText());
                int newSemester = Integer.parseInt(semesterField.getText());
                int newWeeklyHours = Integer.parseInt(hoursField.getText());
                
                boolean success = enrollmentService.updateEnrolledUnit(eu, newUnitCode, newYear, newSemester, newWeeklyHours);
                if (success) {
                    displayEnrolledUnits();
                } else {
                    showAlert("Error", "Failed to update the unit.");
                }
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numeric values for year, semester, and weekly hours.");
            }
        });

        // Create a delete button.
        Button deleteButton = new Button("Delete");
        deleteButton.setTextFill(Color.RED);
        deleteButton.setLayoutX(150);
        deleteButton.setLayoutY(122);
        deleteButton.setOnAction(evt -> {
            boolean success = enrollmentService.deleteEnrolledUnit(eu);
            if (success) {
                displayEnrolledUnits();
            } else {
                showAlert("Error", "Failed to delete the unit.");
            }
        });

        // Add controls to the content pane.
        contentPane.getChildren().addAll(codeField, yearField, semesterField, hoursField, saveButton, deleteButton);

        // Create a TitledPane that uses the unit_code as the title.
        TitledPane pane = new TitledPane(eu.getUnit_code(), contentPane);
        pane.setAnimated(false);
        return pane;
    }

    // Navigation and alert methods:
    @FXML
    protected void handleHome() {
        try {
            HelloApplication.switchToHomePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Home page");
        }
    }

    @FXML
    protected void goToEditUnit() {
        showAlert("Edit Units", "You are already on the Edit Units Page.");
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
        authenticateService.logoutUser();
        showAlert("Log Out", "You have been logged out.");
    }

    @FXML
    protected void goToPredictGrade() {
        try {
            HelloApplication.switchToPredictGradePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Predict Grade page.");
        }
    }

    public void goToAllUnits(ActionEvent actionEvent) {
        try {
            HelloApplication.switchToAllUnitsPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
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
