package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.controller.components.SemesterComponentFactory;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.util.FormValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.Comparator;

public class HomeController extends BaseController {

    @FXML
    private VBox semestersVBox;

    @FXML
    private ScrollPane semestersScrollPane;

    @FXML
    private GridPane semestersGridPane;

    @FXML
    private Accordion semestersAccordion;

    @FXML
    private TilePane semestersTilePane;

    @FXML
    private FlowPane semestersFlowPane;

    @FXML
    private Label selectedYearLabel;

    @FXML
    private Label selectedSemesterLabel;

    @FXML
    private Button predictSemesterButton;

    @FXML
    private TextField currentYearField;

    @FXML
    private TextField currentSemesterField;

    @FXML
    private Button saveCurrentAcademicPeriodButton;

    @Override
    protected void initializePageSpecificContent() {
        populateCurrentAcademicPeriodFields();
        // Initialize labels for selected semester
        selectedYearLabel.setText("N/A");
        selectedSemesterLabel.setText("N/A");
    }

    @Override
    protected void loadPageData() {
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
        semestersFlowPane.getChildren().clear();

        if (firstEnrollment == null) {
            semestersFlowPane.getChildren().add(new Label("No enrolled semesters found."));
            return;
        }

        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(firstEnrollment);

        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            semestersFlowPane.getChildren().add(new Label("No enrolled semesters found."));
        } else {
            int maxColumns = 3; // Only three semesters per row

            Map<Integer, Map<Integer, List<EnrolledUnit>>> unitsByYearThenSemester = enrolledUnits.stream()
                    .collect(Collectors.groupingBy(EnrolledUnit::getYear_enrolled,
                            Collectors.groupingBy(EnrolledUnit::getSemester_enrolled)));

            unitsByYearThenSemester.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Map<Integer, List<EnrolledUnit>>>comparingByKey().reversed())
                    .forEach(yearEntry -> {
                        yearEntry.getValue().entrySet().stream()
                                .sorted(Map.Entry.<Integer, List<EnrolledUnit>>comparingByKey().reversed())
                                .forEach(semesterEntry -> {
                                    int year = yearEntry.getKey();
                                    int semester = semesterEntry.getKey();
                                    List<EnrolledUnit> unitsInSemester = semesterEntry.getValue();

                                    TitledPane semesterNode = SemesterComponentFactory.createSemesterNode(
                                            year, semester, unitsInSemester, this::handleSemesterSelection);

                                    // Control layout: Ensure only 3 per row
                                    semesterNode.setMaxWidth(180); // Adjusted for fit
                                    semestersFlowPane.getChildren().add(semesterNode);
                                });
                    });
        }
    }

    /**
     * Handles the selection of a semester.
     * Updates the UI to show the selected semester for prediction.
     * @param year The selected year.
     * @param semester The selected semester.
     */
    private void handleSemesterSelection(int year, int semester) {
        selectedYearLabel.setText(String.valueOf(year));
        selectedSemesterLabel.setText(String.valueOf(semester));
    }


    /**
     * Handles the prediction of grades for a selected semester.
     */
    @FXML
    private void handlePredictForSemester(ActionEvent event) {
        String year = selectedYearLabel.getText();
        String semester = selectedSemesterLabel.getText();

        if ("N/A".equals(year) || "N/A".equals(semester)) {
            showAlert("No Semester Selected", "Please click on a semester from the list to select that semester for predict grades.");
            return;
        }
        System.out.println("Dummy Year: " + year + " Semester: " + semester);
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
