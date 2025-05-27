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
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.function.Consumer;

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
    public static Node createEditableEnrolledUnitNode(EnrolledUnit eu, 
                                                     EnrolledUnitActionHandler actionHandler) {
        try {
            FXMLLoader loader = new FXMLLoader(EnrolledUnitComponentFactory.class.getResource("/org/example/grade_predictor/components/editable_enrolled_unit_item.fxml"));
            Node node = loader.load();
            EditableEnrolledUnitItemController controller = loader.getController();
            controller.initializeData(eu, actionHandler);

            TitledPane pane = new TitledPane(eu.getUnit_code(), node);
            pane.setAnimated(false);
            pane.setExpanded(true);
            return pane;
        } catch (IOException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading unit editor: " + eu.getUnit_code());
            return errorLabel;
        }
    }
    
    /**
     * Interface for handling actions on enrolled units
     */
    public interface EnrolledUnitActionHandler {
        void onSave(EnrolledUnit unit, int newYear, int newSemester, int newWeeklyHours);
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