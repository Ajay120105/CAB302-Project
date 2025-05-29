package org.example.grade_predictor.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.grade_predictor.model.EnrolledUnit;

public class EditableEnrolledUnitItemController {

    @FXML private Label unitCodeLabel;
    @FXML private TextField yearField;
    @FXML private TextField semesterField;
    @FXML private TextField hoursField;
    @FXML private TextField finalisedGpaField;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;

    private EnrolledUnit enrolledUnit;
    private EnrolledUnitComponentFactory.EnrolledUnitActionHandler actionHandler;
    private boolean isCurrentSemester;

    public void initializeData(EnrolledUnit unit, EnrolledUnitComponentFactory.EnrolledUnitActionHandler handler, boolean isCurrentSemester) {
        this.enrolledUnit = unit;
        this.actionHandler = handler;
        this.isCurrentSemester = isCurrentSemester;

        unitCodeLabel.setText(unit.getUnit_code());
        yearField.setText(String.valueOf(unit.getYear_enrolled()));
        semesterField.setText(String.valueOf(unit.getSemester_enrolled()));
        hoursField.setText(String.valueOf(unit.getWeekly_hours()));
        
        if (unit.getFinalised_gpa() != null) {
            finalisedGpaField.setText(String.valueOf(unit.getFinalised_gpa()));
        }
        
        if (isCurrentSemester) {
            finalisedGpaField.setDisable(true);
            finalisedGpaField.setPromptText("Current semester");
        } else {
            finalisedGpaField.setPromptText("Enter final GPA");
        }
    }

    @FXML
    private void handleSave() {
        if (actionHandler != null && enrolledUnit != null) {
            try {
                int newYear = Integer.parseInt(yearField.getText());
                int newSemester = Integer.parseInt(semesterField.getText());
                int newWeeklyHours = Integer.parseInt(hoursField.getText());
                Double finalisedGpa = null;
                if (!finalisedGpaField.getText().trim().isEmpty()) {
                    finalisedGpa = Double.parseDouble(finalisedGpaField.getText());
                }
                actionHandler.onSave(enrolledUnit, newYear, newSemester, newWeeklyHours, finalisedGpa);
            } catch (NumberFormatException e) {
                if (actionHandler != null) {
                    actionHandler.onInputError("Please enter valid numeric values.");
                }
            }
        }
    }

    @FXML
    private void handleDelete() {
        if (actionHandler != null && enrolledUnit != null) {
            actionHandler.onDelete(enrolledUnit);
        }
    }
} 