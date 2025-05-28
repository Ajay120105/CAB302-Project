package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;
import org.example.grade_predictor.service.UnitService;
import org.example.grade_predictor.service.DegreeService;
import org.example.grade_predictor.HelloApplication;
import java.io.IOException;
import javafx.event.ActionEvent;

public abstract class BaseController {
    
    @FXML
    protected Label welcomeLabel;
    
    @FXML
    protected Label degreeNameLabel;
    
    protected final AuthenticateService authenticateService;
    protected final EnrollmentService enrollmentService;
    protected final UnitService unitService;
    protected final DegreeService degreeService;
    
    public BaseController() {
        this.authenticateService = AuthenticateService.getInstance();
        this.enrollmentService = new EnrollmentService(authenticateService);
        this.unitService = new UnitService();
        this.degreeService = new DegreeService();
    }
    
    /**
     * Template method - defines the initialization process
     */
    @FXML
    public final void initialize() {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser != null) {
            initializeWelcomeMessage(currentUser);
            initializeDegreeName(currentUser);
        }
        
        // Hook method for subclasses to implement
        initializePageSpecificContent();
        
        // Hook method for loading data
        loadPageData();
    }
    
    /**
     * Sets up the welcome message
     */
    protected void initializeWelcomeMessage(User user) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getFirst_name() + " " + user.getLast_name() + "!");
        }
    }

    /**
     * Sets up the degree name label
     */
    protected void initializeDegreeName(User user) {
        if (degreeNameLabel != null) {
            Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
            if (firstEnrollment != null) {
                Degree degree = degreeService.getDegreeById(firstEnrollment.getDegree_ID());
                if (degree != null) {
                    degreeNameLabel.setText(degree.getDegree_Name());
                } else {
                    degreeNameLabel.setText("Degree not found.");
                }
            } else {
                degreeNameLabel.setText("No enrollment information available.");
            }
        }
    }
    
    /**
     * Hook method for page-specific initialization
     * Override in subclasses for custom behavior
     */
    protected abstract void initializePageSpecificContent();
    
    /**
     * Hook method for loading page data
     * Override in subclasses to load specific data
     */
    protected abstract void loadPageData();
    
    /**
     * Common utility method for showing alerts
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Common method to check if user is authenticated
     */
    protected boolean isUserAuthenticated() {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser == null) {
            showAlert("Error", "Please log in to access this feature.");
            return false;
        }
        return true;
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
    protected void handleSettings() {
        try {
            HelloApplication.switchToSettingsPage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Settings page.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleProfile() {
        try {
            HelloApplication.switchToProfilePage();
        } catch (IOException e) {
            showAlert("Navigation Error", " Could not open profile page");
            e.printStackTrace();
        }
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
    protected void goToPredictGrade() {
        try {
            HelloApplication.switchToPredictGradePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Predict Grade page.");
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
    public void goToAllUnits(ActionEvent actionEvent) {
        try {
            HelloApplication.switchToAllUnitsPage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open All Units page.");
            e.printStackTrace();
        }
    }
} 