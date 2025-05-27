package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;

import java.io.IOException;
import java.util.List;

public class HomeController extends BaseController {

    // Container for displaying enrolled units.
    @FXML
    private VBox unitsVBox;

    @Override
    protected void initializePageSpecificContent() {
    }

    @Override
    protected void loadPageData() {
        // Display enrolled units using the service
        displayEnrolledUnits();
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
