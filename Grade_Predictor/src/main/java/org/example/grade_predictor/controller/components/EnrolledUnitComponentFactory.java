package org.example.grade_predictor.controller.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Unit;
import javafx.scene.layout.HBox;

/**
 * Factory class for creating UI components for EnrolledUnit display
 */
public class EnrolledUnitComponentFactory {
    
    /**
     * Creates a simple view-only component for an enrolled unit
     * Used in pages like PredictGrade where editing is not needed
     */
    public static Node createSimpleEnrolledUnitNode(EnrolledUnit eu) {
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
     * Creates an editable component for an enrolled unit
     * Used in EditUnit page where users can modify unit details
     */
    public static Node createEditableEnrolledUnitNode(EnrolledUnit eu, 
                                                     EnrolledUnitActionHandler actionHandler) {
        AnchorPane contentPane = new AnchorPane();

        // Create editable fields
        TextField codeField = new TextField(eu.getUnit_code());
        codeField.setLayoutX(20);
        codeField.setLayoutY(14);

        TextField yearField = new TextField(String.valueOf(eu.getYear_enrolled()));
        yearField.setLayoutX(20);
        yearField.setLayoutY(50);

        TextField semesterField = new TextField(String.valueOf(eu.getSemester_enrolled()));
        semesterField.setLayoutX(20);
        semesterField.setLayoutY(86);

        TextField hoursField = new TextField(String.valueOf(eu.getWeekly_hours()));
        hoursField.setLayoutX(20);
        hoursField.setLayoutY(122);
        
        // Create action buttons
        Button saveButton = new Button("Save");
        saveButton.setTextFill(Color.GREEN);
        saveButton.setLayoutX(150);
        saveButton.setLayoutY(86);
        saveButton.setOnAction(evt -> {
            try {
                String newUnitCode = codeField.getText();
                int newYear = Integer.parseInt(yearField.getText());
                int newSemester = Integer.parseInt(semesterField.getText());
                int newWeeklyHours = Integer.parseInt(hoursField.getText());
                
                actionHandler.onSave(eu, newUnitCode, newYear, newSemester, newWeeklyHours);
            } catch (NumberFormatException e) {
                actionHandler.onInputError("Please enter valid numeric values for year, semester, and weekly hours.");
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setTextFill(Color.RED);
        deleteButton.setLayoutX(150);
        deleteButton.setLayoutY(122);
        deleteButton.setOnAction(evt -> actionHandler.onDelete(eu));

        contentPane.getChildren().addAll(codeField, yearField, semesterField, hoursField, saveButton, deleteButton);

        TitledPane pane = new TitledPane(eu.getUnit_code(), contentPane);
        pane.setAnimated(false);
        return pane;
    }
    
    /**
     * Interface for handling actions on enrolled units
     */
    public interface EnrolledUnitActionHandler {
        void onSave(EnrolledUnit unit, String newCode, int newYear, int newSemester, int newWeeklyHours);
        void onDelete(EnrolledUnit unit);
        void onInputError(String errorMessage);
    }

    /**
     * Interface for handling enrollment action on available units.
     */
    public interface AvailableUnitEnrollHandler {
        void onEnroll(Unit unit);
    }

    /**
     * Creates a UI row (HBox) for an available unit with unit info and an "Enroll" button.
     */
    public static Node createAvailableUnitNode(Unit unit, AvailableUnitEnrollHandler enrollHandler) {
        HBox row = new HBox(10); // Using HBox as in the original createUnitRow
        Label unitInfo = new Label(unit.getUnit_code() + ": " + unit.getUnit_name());
        Button enrollButton = new Button("Enroll");

        enrollButton.setOnAction(event -> enrollHandler.onEnroll(unit));

        row.getChildren().addAll(unitInfo, enrollButton);
        return row;
    }
}