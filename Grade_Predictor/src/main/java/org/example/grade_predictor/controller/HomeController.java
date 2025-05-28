package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.service.FormValidator;

import java.io.IOException;
import java.util.List;

public class HomeController extends BaseController {

    // Container for displaying enrolled units.
    @FXML
    private VBox unitsVBox;

    @FXML
    private TextField currentYearField;

    @FXML
    private TextField currentSemesterField;

    @FXML
    private Button saveCurrentAcademicPeriodButton;

    @Override
    protected void initializePageSpecificContent() {
        populateCurrentAcademicPeriodFields();
    }

    @Override
    protected void loadPageData() {
        // Display enrolled units using the service
        displayEnrolledUnits();
    }

    private void populateCurrentAcademicPeriodFields() {
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment != null) {
            currentYearField.setText(String.valueOf(enrollment.getCurrentYear()));
            currentSemesterField.setText(String.valueOf(enrollment.getCurrentSemester()));
        }
    }

    @FXML
    private void handleSaveCurrentAcademicPeriod() {
        String yearStr = currentYearField.getText();
        String semesterStr = currentSemesterField.getText();

        FormValidator.ValidationResult validation = FormValidator.validateEnrollmentYears("2000", "1", yearStr, semesterStr); // Dummy first year/sem
        if (!validation.isValid()) {
            showAlert("Invalid Input", validation.getErrorMessage());
            return;
        }

        int currentYear = Integer.parseInt(yearStr);
        int currentSemester = Integer.parseInt(semesterStr);

        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment != null) {
            enrollment.setCurrentYear(currentYear);
            enrollment.setCurrentSemester(currentSemester);
            if (enrollmentService.updateEnrollment(enrollment)) {
                showAlert("Success", "Current academic period updated successfully.");
            } else {
                showAlert("Error", "Failed to update current academic period.");
            }
        } else {
            showAlert("Error", "No active enrollment found to update.");
        }
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
     * Creates a read-only UI component for an enrolled unit using the factory.
     *
     * @param eu the enrolled unit whose information is displayed
     * @return a Node representing the enrolled unit
     */
    private Node createEnrolledUnitNode(EnrolledUnit eu) {
        return EnrolledUnitComponentFactory.createSimpleEnrolledUnitNode(eu);
    }
}
