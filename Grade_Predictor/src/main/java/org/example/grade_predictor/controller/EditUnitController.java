package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;

import java.io.IOException;
import java.util.List;

public class EditUnitController extends BaseController implements EnrolledUnitComponentFactory.EnrolledUnitActionHandler {

    // Container for dynamically added enrollment panels.
    @FXML
    private VBox unitsVBox;

    @Override
    protected void initializePageSpecificContent() {
        addNewUnitComponent();
    }

    @Override
    protected void loadPageData() {
        displayEnrolledUnits();
    }

    @Override
    public void onSave(EnrolledUnit unit, int newYear, int newSemester, int newWeeklyHours) {
        boolean success = enrollmentService.updateEnrolledUnit(unit, unit.getUnit_code(), newYear, newSemester, newWeeklyHours);
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
                Node pane = EnrolledUnitComponentFactory.createEditableEnrolledUnitNode(eu, this);
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
}
