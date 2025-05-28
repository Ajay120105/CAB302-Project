package org.example.grade_predictor.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.controller.components.EnrolledUnitComponentFactory;
import org.example.grade_predictor.controller.components.IntakeDialogController;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AllUnitsController extends BaseController implements EnrolledUnitComponentFactory.AvailableUnitEnrollHandler {

    // VBox container defined in the FXML to list all available units.
    @FXML
    private VBox unitsVBox;

    @Override
    protected void initializePageSpecificContent() {
    }

    @Override
    protected void loadPageData() {
        loadAllUnits();
    }

    /**
     * Loads all available units asynchronously from the UnitDAO and populates the VBox.
     */
    private void loadAllUnits() {
        Task<List<Unit>> loadTask = new Task<>() {
            @Override
            protected List<Unit> call() {
                return unitService.getAllUnits();
            }
        };

        loadTask.setOnSucceeded(event -> {
            List<Unit> units = loadTask.getValue();
            unitsVBox.getChildren().clear();
            if (units.isEmpty()) {
                unitsVBox.getChildren().add(new Label("No units available."));
            } else {
                for (Unit unit : units) {
                    unitsVBox.getChildren().add(EnrolledUnitComponentFactory.createAvailableUnitNode(unit, this));
                }
            }
        });

        loadTask.setOnFailed(event -> {
            showAlert("Error", "Could not load available units.");
        });

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Just handle the enrollment action from the component factory.
     */
    @Override
    public void onEnroll(Unit unit) {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser == null) {
            showAlert("Error", "Please log in to enroll in units.");
            return;
        }
        
        Enrollment firstEnrollment = enrollmentService.getCurrentUserFirstEnrollment();
        if (firstEnrollment == null) {
            showAlert("Error", "You need to be enrolled in a program to add units.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("components/IntakeDialog.fxml"));
            GridPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Intake Year and Semester");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(unitsVBox.getScene().getWindow()); // Set owner if unitsVBox is available and part of a scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            IntakeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                IntakeDialogController.IntakeData intakeData = controller.getIntakeData();
                int intakeYear = intakeData.getYear();
                int intakeSemester = intakeData.getSemester();
                //TODO: Default weekly hours should be made edit somewhere else
                int defaultWeeklyHours = 5;

                EnrolledUnit enrolledUnit = enrollmentService.addEnrolledUnit(
                    unit.getUnit_code(),
                    intakeYear,
                    intakeSemester,
                    defaultWeeklyHours
                );
                
                if (enrolledUnit != null) {
                    showAlert("Success", "Enrolled in " + unit.getUnit_code() + " successfully!");
                } else {
                    showAlert("Error", "Failed to enroll in " + unit.getUnit_code() + ".");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open intake dialog.");
        }
    }
}

