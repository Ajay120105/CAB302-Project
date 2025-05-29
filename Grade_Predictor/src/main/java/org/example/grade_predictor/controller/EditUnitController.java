package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.controller.components.SemesterComponentFactory;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EditUnitController extends BaseController implements EnrolledUnitComponentFactory.EnrolledUnitActionHandler {

    @FXML
    private VBox unitsVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane unitsGridPane;

    @Override
    protected void initializePageSpecificContent() {
        addNewUnitComponent();
    }

    @Override
    protected void loadPageData() {
        displayEnrolledUnits();
    }

    @Override
    public void onSave(EnrolledUnit unit, int newYear, int newSemester, int newWeeklyHours, Double finalisedGpa) {
        boolean success = enrollmentService.updateEnrolledUnit(unit, unit.getUnit_code(), newYear, newSemester, newWeeklyHours, finalisedGpa);
        if (success) {
            displayEnrolledUnits();
        } else {
            showAlert("Error", "Failed to update the unit.");
        }
    }

    @Override
    public void onDelete(EnrolledUnit unit) {
        boolean success = enrollmentService.deleteEnrolledUnit(unit);
        if (success) {
            displayEnrolledUnits();
        } else {
            showAlert("Error", "Failed to delete the unit.");
        }
    }

    @Override
    public void onInputError(String errorMessage) {
        showAlert("Input Error", errorMessage);
    }

    /**
     * Displays all enrolled units grouped by year and semester, similar to the home page
     */
    private void displayEnrolledUnits() {
        Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
        unitsGridPane.getChildren().clear();

        if (firstEnrollment == null) {
            unitsGridPane.getChildren().add(new Label("No enrolled semesters found."));
            return;
        }

        List<EnrolledUnit> enrolledUnits = enrollmentService.getEnrolledUnits(firstEnrollment);

        if (enrolledUnits == null || enrolledUnits.isEmpty()) {
            unitsGridPane.getChildren().add(new Label("No enrolled semesters found."));
        } else {
            int maxColumns = 3; // Only three semesters per row

            Map<Integer, Map<Integer, List<EnrolledUnit>>> unitsByYearThenSemester = enrolledUnits.stream()
                    .collect(Collectors.groupingBy(EnrolledUnit::getYear_enrolled,
                            Collectors.groupingBy(EnrolledUnit::getSemester_enrolled)));

            int currentYear = firstEnrollment.getCurrentYear();
            int currentSemester = firstEnrollment.getCurrentSemester();

            int columnCount = 0;
            for (Map.Entry<Integer, Map<Integer, List<EnrolledUnit>>> yearEntry : unitsByYearThenSemester.entrySet()) {
                int year = yearEntry.getKey();
                Map<Integer, List<EnrolledUnit>> semesterMap = yearEntry.getValue();

                for (Map.Entry<Integer, List<EnrolledUnit>> semesterEntry : semesterMap.entrySet()) {
                    int semester = semesterEntry.getKey();
                    List<EnrolledUnit> units = semesterEntry.getValue();

                    boolean isCurrentSemester = (year == currentYear && semester == currentSemester);

                    TitledPane semesterPane = createEditableSemesterNode(year, semester, units, isCurrentSemester);
                    unitsGridPane.getChildren().add(semesterPane);

                    columnCount++;
                    if (columnCount >= maxColumns) {
                        columnCount = 0;
                    }
                }
            }
        }
    }

    /**
     * Creates a semester node with editable enrolled units
     */
    private TitledPane createEditableSemesterNode(int year, int semester, List<EnrolledUnit> units, boolean isCurrentSemester) {
        VBox unitsDisplayVBox = new VBox(10);
        
        for (EnrolledUnit unit : units) {
            Node unitNode = EnrolledUnitComponentFactory.createEditableEnrolledUnitNode(unit, this, isCurrentSemester);
            unitsDisplayVBox.getChildren().add(unitNode);
        }

        String titleText = "Year: " + year + ", Semester: " + semester;
        if (isCurrentSemester) {
            titleText += " (Current)";
        }
        
        TitledPane semesterPane = new TitledPane(titleText, unitsDisplayVBox);
        semesterPane.setAnimated(false);
        semesterPane.setExpanded(true); // Expanded by default for editing
        semesterPane.setPrefWidth(320);
        return semesterPane;
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
        
        unitsGridPane.getChildren().add(0, addNewUnitPane);
    }
}
