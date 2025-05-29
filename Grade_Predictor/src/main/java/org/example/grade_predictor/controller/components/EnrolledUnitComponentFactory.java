package org.example.grade_predictor.controller.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Unit;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Factory class for creating UI components for EnrolledUnit display
 */
public class EnrolledUnitComponentFactory {
    
    /**
     * Functional interface for handling selection of an enrolled unit.
     */
    @FunctionalInterface
    public interface EnrolledUnitSelectionHandler {
        void onSelect(EnrolledUnit unit);
    }
    
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
        
        if (eu.getFinalised_gpa() != null) {
            content += "\nFinal GPA: " + String.format("%.2f", eu.getFinalised_gpa());
        }
        
        pane.setContent(new Label(content));
        pane.setAnimated(false);
        return pane;
    }
    
    /**
     * Creates a selectable, view-only component for an enrolled unit.
     * When clicked, it invokes the provided selection handler.
     */
    public static Node createSelectableEnrolledUnitNode(EnrolledUnit eu, EnrolledUnitSelectionHandler selectionHandler) {
        TitledPane pane = new TitledPane();
        pane.setText(eu.getUnit_code() + " - Click to Select");
        
        String content = "Year: " + eu.getYear_enrolled() + "\n" +
                "Semester: " + eu.getSemester_enrolled() + "\n" +
                "Weekly Hours: " + eu.getWeekly_hours();
        
        if (eu.getFinalised_gpa() != null) {
            content += "\nFinal GPA: " + String.format("%.2f", eu.getFinalised_gpa());
        }
        
        Label contentLabel = new Label(content);
        pane.setContent(contentLabel);
        pane.setAnimated(false);
        
        // Make the TitledPane itself clickable for selection
        pane.setOnMouseClicked(event -> {
            if (selectionHandler != null) {
                selectionHandler.onSelect(eu);
            }
        });
        
        return pane;
    }
    
    /**
     * Creates an editable component for an enrolled unit
     * Used in EditUnit page where users can modify unit details
     */
    public static Node createEditableEnrolledUnitNode(EnrolledUnit eu, EnrolledUnitActionHandler actionHandler, boolean isCurrentSemester) {
        // Enlarged Pane for better layout
        Pane unitPane = new Pane();
        unitPane.setPrefSize(300, 200); // Increased height for GPA field
        unitPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 5; -fx-padding: 10;");

        // Unit Label
        Label unitLabel = new Label("Unit Code: " + eu.getUnit_code());
        unitLabel.setLayoutX(10);
        unitLabel.setLayoutY(10);
        unitLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Labels for each field
        Label yearLabel = new Label("Year:");
        yearLabel.setLayoutX(10);
        yearLabel.setLayoutY(35);

        Label semesterLabel = new Label("Semester:");
        semesterLabel.setLayoutX(160); // Increased spacing
        semesterLabel.setLayoutY(35);

        Label weeklyHoursLabel = new Label("Weekly Hours:");
        weeklyHoursLabel.setLayoutX(10);
        weeklyHoursLabel.setLayoutY(75);

        Label finalisedGpaLabel = new Label("Final GPA:");
        finalisedGpaLabel.setLayoutX(160);
        finalisedGpaLabel.setLayoutY(75);

        // Text Fields
        TextField yearField = new TextField(String.valueOf(eu.getYear_enrolled()));
        yearField.setLayoutX(10);
        yearField.setLayoutY(50);
        yearField.setPrefWidth(130); // Prevent overlapping

        TextField semesterField = new TextField(String.valueOf(eu.getSemester_enrolled()));
        semesterField.setLayoutX(160);
        semesterField.setLayoutY(50);
        semesterField.setPrefWidth(130); // Prevent overlapping

        TextField weeklyHoursField = new TextField(String.valueOf(eu.getWeekly_hours()));
        weeklyHoursField.setLayoutX(10);
        weeklyHoursField.setLayoutY(90);
        weeklyHoursField.setPrefWidth(130);

        TextField finalisedGpaField = new TextField();
        if (eu.getFinalised_gpa() != null) {
            finalisedGpaField.setText(String.valueOf(eu.getFinalised_gpa()));
        }
        finalisedGpaField.setLayoutX(160);
        finalisedGpaField.setLayoutY(90);
        finalisedGpaField.setPrefWidth(130);
        
        if (isCurrentSemester) {
            finalisedGpaField.setDisable(true);
            finalisedGpaField.setPromptText("Current semester");
        } else {
            finalisedGpaField.setPromptText("Enter final GPA");
        }

        // Save & Delete Buttons - Bottom row
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #309adc; -fx-text-fill: white;");
        saveButton.setLayoutX(10);
        saveButton.setLayoutY(130);
        saveButton.setPrefWidth(130);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #FF5555; -fx-text-fill: white;");
        deleteButton.setLayoutX(160);
        deleteButton.setLayoutY(130);
        deleteButton.setPrefWidth(130);

        saveButton.setOnAction(event -> {
            try {
                int newYear = Integer.parseInt(yearField.getText());
                int newSemester = Integer.parseInt(semesterField.getText());
                int newWeeklyHours = Integer.parseInt(weeklyHoursField.getText());
                Double finalisedGpa = null;
                if (!finalisedGpaField.getText().trim().isEmpty()) {
                    finalisedGpa = Double.parseDouble(finalisedGpaField.getText());
                }
                actionHandler.onSave(eu, newYear, newSemester, newWeeklyHours, finalisedGpa);
            } catch (NumberFormatException e) {
                actionHandler.onInputError("Please enter valid numeric values.");
            }
        });

        deleteButton.setOnAction(event -> actionHandler.onDelete(eu));

        // Add components to Pane
        unitPane.getChildren().addAll(unitLabel, yearLabel, semesterLabel, weeklyHoursLabel, finalisedGpaLabel,
                yearField, semesterField, weeklyHoursField, finalisedGpaField,
                saveButton, deleteButton);

        return unitPane;
    }


    
    /**
     * Interface for handling actions on enrolled units
     */
    public interface EnrolledUnitActionHandler {
        void onSave(EnrolledUnit unit, int newYear, int newSemester, int newWeeklyHours, Double finalisedGpa);
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
        Pane unitPane = new Pane();
        unitPane.setPrefSize(300, 80);
        unitPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 5; -fx-padding: 10;");

        // Unit Name Label (Auto-wrapping)
        Label unitLabel = new Label(unit.getUnit_code() + ": " + unit.getUnit_name());
        unitLabel.setLayoutX(10);
        unitLabel.setLayoutY(10);
        unitLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        unitLabel.setWrapText(true); // Enables automatic text wrapping
        unitLabel.setMaxWidth(280); // Ensures text stays within pane boundaries

        // Enroll Button
        Button enrollButton = new Button("Enroll");
        enrollButton.setLayoutX(200);
        enrollButton.setLayoutY(40);
        enrollButton.setStyle("-fx-background-color: #309adc; -fx-text-fill: white; -fx-padding: 5;");
        enrollButton.setOnAction(event -> enrollHandler.onEnroll(unit));

        // Add elements to Pane
        unitPane.getChildren().addAll(unitLabel, enrollButton);

        return unitPane;
    }

}