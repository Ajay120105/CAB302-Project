package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.dto.GradeResponseDTO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.SQLiteDAO.SqliteDegreeDAO;
import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.OllamaGradePredictionService;

import java.io.IOException;
import java.util.List;

public class PredictGradeController extends BaseController {

    // The VBox container for showing enrolled units.
    @FXML
    private VBox unitsVBox;

    @FXML
    private Button predictButton;

    private final SqliteDegreeDAO degreeDAO;
    private final OllamaGradePredictionService ollamaService;

    public PredictGradeController() {
        super();
        this.degreeDAO = new SqliteDegreeDAO();
        this.ollamaService = new OllamaGradePredictionService();
    }

    @Override
    protected void initializePageSpecificContent() {
    }

    @Override
    protected void loadPageData() {
        displayEnrolledUnits();
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
        return EnrolledUnitComponentFactory.createSimpleEnrolledUnitNode(eu);
    }

    /**
     * Handler for the Predict Grade button click
     */
    @FXML
    protected void predictGrade() {
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
}
