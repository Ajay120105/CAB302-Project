package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.Collections;
import java.util.List;

public class PredictGradeController extends BaseController {

    // The VBox container for showing enrolled units.
    @FXML
    private VBox unitsVBox;

    @FXML
    private Button predictButton;

    @FXML
    private Label selectedUnitCodeLabel;

    @FXML
    private Label selectedUnitYearLabel;

    @FXML
    private Label selectedUnitSemesterLabel;

    @FXML
    private TextField studyHoursField;

    @FXML
    private TextField studyEfficiencyField;

    private final SqliteDegreeDAO degreeDAO;
    private final OllamaGradePredictionService ollamaService;
    private EnrolledUnit selectedEnrolledUnit;

    public PredictGradeController() {
        super();
        this.degreeDAO = new SqliteDegreeDAO();
        this.ollamaService = new OllamaGradePredictionService();
    }

    @Override
    protected void initializePageSpecificContent() {
        selectedUnitCodeLabel.setText("N/A");
        selectedUnitYearLabel.setText("N/A");
        selectedUnitSemesterLabel.setText("N/A");
        studyHoursField.clear();
        studyEfficiencyField.clear();
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
            selectedEnrolledUnit = null;
            updateSelectedUnitDetailsDisplay();
            return;
        }
        
        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(firstEnrollment);
        
        unitsVBox.getChildren().clear();
        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            unitsVBox.getChildren().add(new Label("You have not enrolled in any classes yet."));
        } else {
            for (EnrolledUnit eu : enrolledUnits) {
                Node node = EnrolledUnitComponentFactory.createSelectableEnrolledUnitNode(eu, this::handleUnitSelection);
                unitsVBox.getChildren().add(node);
            }
        }
        selectedEnrolledUnit = null;
        updateSelectedUnitDetailsDisplay();
    }

    private void handleUnitSelection(EnrolledUnit eu) {
        this.selectedEnrolledUnit = eu;
        updateSelectedUnitDetailsDisplay();
    }

    private void updateSelectedUnitDetailsDisplay() {
        if (selectedEnrolledUnit != null) {
            selectedUnitCodeLabel.setText(selectedEnrolledUnit.getUnit_code());
            selectedUnitYearLabel.setText(String.valueOf(selectedEnrolledUnit.getYear_enrolled()));
            selectedUnitSemesterLabel.setText(String.valueOf(selectedEnrolledUnit.getSemester_enrolled()));
            studyHoursField.setText(String.valueOf(selectedEnrolledUnit.getWeekly_hours()));
            studyEfficiencyField.clear();
        } else {
            selectedUnitCodeLabel.setText("N/A");
            selectedUnitYearLabel.setText("N/A");
            selectedUnitSemesterLabel.setText("N/A");
            studyHoursField.clear();
            studyEfficiencyField.clear();
        }
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

        if (selectedEnrolledUnit == null) {
            showAlert("Error", "Please select a unit from the list.");
            return;
        }
        
        System.out.println("Starting grade prediction: " + selectedEnrolledUnit.getUnit_code());
        
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment == null) {
            showAlert("Error", "Could not find enrollment record for current user.");
            return;
        }
        
        List<EnrolledUnit> allEnrolledUnits = enrollmentService.getEnrolledUnits(enrollment);
        
        Degree degree = degreeDAO.getDegree(String.valueOf(enrollment.getDegree_ID()));
        if (degree == null) {
            showAlert("Error", "Could not find degree.");
            return;
        }
        
        int studyHours;
        int studyEfficiency;
        try {
            studyHours = Integer.parseInt(studyHoursField.getText());
            studyEfficiency = Integer.parseInt(studyEfficiencyField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for study hours and study efficiency.");
            return;
        }
        
        ollamaService.predictGrade(
            enrollment, 
            degree, 
            selectedEnrolledUnit, 
            allEnrolledUnits, 
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
