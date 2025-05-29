package org.example.grade_predictor.controller;

import javafx.application.Platform;
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
import org.example.grade_predictor.controller.components.PredictionResultDialog;
import org.example.grade_predictor.controller.components.SemesterComponentFactory;
import org.example.grade_predictor.dto.GradeResponseDTO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.SQLiteDAO.SqliteDegreeDAO;
import org.example.grade_predictor.service.OllamaGradePredictionService;
import org.example.grade_predictor.util.FormValidator;
import org.example.grade_predictor.util.OllamaConnectionChecker;

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

    @FXML
    private TextField semesterStudyHoursField;

    @FXML
    private TextField semesterStudyEfficiencyField;

    @FXML
    private VBox semesterPredictionContainer;

    @FXML
    private ProgressIndicator semesterLoadingIndicator;

    private final SqliteDegreeDAO degreeDAO;
    private final OllamaGradePredictionService ollamaService;

    public HomeController() {
        super();
        this.degreeDAO = new SqliteDegreeDAO();
        this.ollamaService = new OllamaGradePredictionService();
    }

    @Override
    protected void initializePageSpecificContent() {
        enrollmentService.refreshCurrentUserData();
        
        populateCurrentAcademicPeriodFields();
        // Initialize labels for selected semester
        selectedYearLabel.setText("N/A");
        selectedSemesterLabel.setText("N/A");
        
        if (semesterStudyHoursField != null) {
            semesterStudyHoursField.clear();
        }
        if (semesterStudyEfficiencyField != null) {
            semesterStudyEfficiencyField.clear();
        }
        
        checkOllamaStatus();
    }

    @Override
    protected void loadPageData() {
        displayEnrolledUnits();
    }

    private void checkOllamaStatus() {
        if (predictSemesterButton != null) {
            if (!OllamaConnectionChecker.isOllamaReadyForGeneration()) {
                predictSemesterButton.setDisable(true);
                String errorMessage = OllamaConnectionChecker.getStatusErrorMessage();
                
                Platform.runLater(() -> {
                    showAlert("Ollama Error", errorMessage);
                });
            } else {
                predictSemesterButton.setDisable(false);
            }
        }
    }

    private void populateCurrentAcademicPeriodFields() {
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment != null) {
            int currentYear = enrollment.getCurrentYear();
            int currentSemester = enrollment.getCurrentSemester();
            
            currentYearField.setText(String.valueOf(currentYear));
            currentSemesterField.setText(String.valueOf(currentSemester));
        } else {
            currentYearField.clear();
            currentSemesterField.clear();
        }
    }

    @FXML
    private void handleSaveCurrentAcademicPeriod() {
        String yearStr = currentYearField.getText();
        String semesterStr = currentSemesterField.getText();

        FormValidator.ValidationResult validation = FormValidator.validateCurrentAcademicPeriod(yearStr, semesterStr);
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
            
            boolean updateSuccess = enrollmentService.updateEnrollment(enrollment);
            
            if (updateSuccess) {
                enrollmentService.refreshCurrentUserData();
                
                populateCurrentAcademicPeriodFields();
                
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
        
        // Pre-fill study hours with average from selected semester units
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment != null) {
            List<EnrolledUnit> semesterUnits = getSemesterUnits(enrollment, year, semester);
            if (!semesterUnits.isEmpty()) {
                double averageHours = semesterUnits.stream()
                        .mapToInt(EnrolledUnit::getWeekly_hours)
                        .average()
                        .orElse(10.0);
                if (semesterStudyHoursField != null) {
                    semesterStudyHoursField.setText(String.valueOf((int) Math.round(averageHours)));
                }
            }
        }
    }

    /**
     * Get units for a specific semester
     */
    private List<EnrolledUnit> getSemesterUnits(Enrollment enrollment, int year, int semester) {
        List<EnrolledUnit> allUnits = enrollmentService.getEnrolledUnits(enrollment);
        return allUnits.stream()
                .filter(unit -> unit.getYear_enrolled() == year && unit.getSemester_enrolled() == semester)
                .collect(Collectors.toList());
    }

    /**
     * Set loading state for semester prediction
     */
    private void setSemesterLoadingState() {
        if (predictSemesterButton != null) {
            predictSemesterButton.setDisable(true);
        }
        if (semesterLoadingIndicator != null) {
            semesterLoadingIndicator.setVisible(true);
            semesterLoadingIndicator.setProgress(-1);
        }
    }

    /**
     * Set normal state for semester prediction
     */
    private void setSemesterNormalState() {
        if (predictSemesterButton != null) {
            predictSemesterButton.setDisable(!OllamaConnectionChecker.isOllamaReadyForGeneration());
        }
        if (semesterLoadingIndicator != null) {
            semesterLoadingIndicator.setVisible(false);
        }
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

        if (!OllamaConnectionChecker.isOllamaReadyForGeneration()) {
            showAlert("Ollama Error", OllamaConnectionChecker.getStatusErrorMessage());
            return;
        }

        String studyHoursText = semesterStudyHoursField != null ? semesterStudyHoursField.getText() : "";
        String studyEfficiencyText = semesterStudyEfficiencyField != null ? semesterStudyEfficiencyField.getText() : "";
        
        FormValidator.ValidationResult validation = FormValidator.validateStudyInputs(studyHoursText, studyEfficiencyText);
        if (!validation.isValid()) {
            showAlert("Invalid Input", validation.getErrorMessage());
            return;
        }
        
        int studyHours = Integer.parseInt(studyHoursText);
        int studyEfficiency = Integer.parseInt(studyEfficiencyText);

        System.out.println("Starting semester GPA prediction for Year: " + year + " Semester: " + semester);
        
        Enrollment enrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (enrollment == null) {
            showAlert("Error", "Could not find enrollment record for current user.");
            return;
        }
        
        Degree degree = degreeDAO.getDegree(String.valueOf(enrollment.getDegree_ID()));
        if (degree == null) {
            showAlert("Error", "Could not find degree information.");
            return;
        }
        
        int targetYear = Integer.parseInt(year);
        int targetSemester = Integer.parseInt(semester);
        
        List<EnrolledUnit> semesterUnits = getSemesterUnits(enrollment, targetYear, targetSemester);
        if (semesterUnits.isEmpty()) {
            showAlert("Error", "No units found for the selected semester.");
            return;
        }
        
        setSemesterLoadingState();
        
        ollamaService.predictSemesterGPA(
            enrollment,
            degree,
            targetYear,
            targetSemester,
            semesterUnits,
            studyHours,
            studyEfficiency,
            new OllamaGradePredictionService.GradePredictionCallback() {
                @Override
                public void onPredictionComplete(GradeResponseDTO response) {
                    Platform.runLater(() -> {
                        setSemesterNormalState();
                        PredictionResultDialog.showSemesterPredictionResult(targetYear, targetSemester, response);
                    });
                }
                
                @Override
                public void onPredictionFailed(String errorMessage) {
                    Platform.runLater(() -> {
                        setSemesterNormalState();
                        showAlert("Prediction Failed", "Semester GPA prediction failed: " + errorMessage);
                    });
                }
            }
        );
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
