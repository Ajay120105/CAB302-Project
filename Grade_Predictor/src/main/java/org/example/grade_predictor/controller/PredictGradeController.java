package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.dto.GradeResponseDTO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.SQLiteDAO.SqliteDegreeDAO;
import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;
import org.example.grade_predictor.service.UnitService;
import org.example.grade_predictor.service.OllamaGradePredictionService;

import java.io.IOException;
import java.util.List;

public class PredictGradeController {

    // The VBox container for showing enrolled units.
    @FXML
    private VBox unitsVBox;

    // Label for greeting the current user.
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button predictButton;

    // Services
    private final AuthenticateService authenticateService;
    private final EnrollmentService enrollmentService;
    private final UnitService unitService;
    
    // TODO: Data Access Objects that don't have services yet
    private final SqliteDegreeDAO degreeDAO;
    
    private final OllamaGradePredictionService ollamaService;

    public PredictGradeController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.enrollmentService = new EnrollmentService(authenticateService);
        this.unitService = new UnitService();
        this.degreeDAO = new SqliteDegreeDAO();
        this.ollamaService = new OllamaGradePredictionService();
    }

    @FXML
    public void initialize() {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getFirst_name() + " " + currentUser.getLast_name();
            welcomeLabel.setText("Welcome, " + fullName + "!");
            displayEnrolledUnits();
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

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
     * Creates a simple UI node for each enrolled unit using a TitledPane.
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

    /**
     * Handler for the Predict Grade button click
     */
    @FXML
    protected void handlePredictGrade() {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser == null) {
            showAlert("Error", "No user is currently logged in.");
            return;
        }
        
        System.out.println("Starting grade prediction...");
        
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment == null) {
            showAlert("Error", "Could not find enrollment record for current user.");
            return;
        }
        
        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(enrollment);
        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            showAlert("Error", "You are not enrolled in any units.");
            return;
        }
        
        List<Unit> allUnits = unitService.getAllUnits();
        
        Degree degree = degreeDAO.getDegree(String.valueOf(enrollment.getDegree_ID()));
        if (degree == null) {
            // Fallback to a default degree if needed
            degree = new Degree("Default Degree", String.valueOf(enrollment.getDegree_ID()));
        }
        
        // Default study values - get from model in future
        int studyHours = 10; // Default 10 hours
        int studyEfficiency = 5; // Default 5 out of 10
        
        ollamaService.predictGrade(
            enrollment, 
            degree, 
            enrolledUnits, 
            allUnits, 
            studyHours, 
            studyEfficiency,
            new OllamaGradePredictionService.GradePredictionCallback() {
                @Override
                public void onPredictionComplete(GradeResponseDTO response) {
                    System.out.println(response.toString());
                }
                
                @Override
                public void onPredictionFailed(String errorMessage) {
                    System.err.println("Grade prediction failed: " + errorMessage);
                }
            }
        );
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
<<<<<<< HEAD
        // Clear the user session
        UserSession.clearSession();

        // Show logout confirmation
        showAlert("Log Out", "You have been logged out.");

        // Redirect to the first page (signup/login)
        try {
            HelloApplication.switchToSignup_LoginPage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Signup/Login page.");
            e.printStackTrace();
        }
=======
        authenticateService.logoutUser();
        showAlert("Log Out", "You have been logged out.");
>>>>>>> OOP-refactoring
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

    public void goToAllUnits(ActionEvent actionEvent) {
        try {
            HelloApplication.switchToAllUnitsPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
        }
    }

    @FXML
    protected void goToPredictGrade() {
        showAlert("Predict Grade", "You are already on the Predict Grade page.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
