package org.example.grade_predictor.controller.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.model.EnrolledUnit;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SemesterComponentFactory {

    @FunctionalInterface
    public interface SemesterSelectionHandler {
        void onSelect(int year, int semester);
    }

    public static TitledPane createSemesterNode(int year, int semester, List<EnrolledUnit> units, SemesterSelectionHandler selectionHandler) {
        VBox unitsDisplayVBox = new VBox(5);
        for (EnrolledUnit unit : units) {
            Node unitNode = EnrolledUnitComponentFactory.createSimpleEnrolledUnitNode(unit);
            unitsDisplayVBox.getChildren().add(unitNode);
        }

        TitledPane semesterPane = new TitledPane("Year: " + year + ", Semester: " + semester, unitsDisplayVBox);
        semesterPane.setAnimated(false);
        semesterPane.setExpanded(false);

        semesterPane.setOnMouseClicked(event -> {
            if (selectionHandler != null) {
                selectionHandler.onSelect(year, semester);
            }
        });
        return semesterPane;
    }
} 