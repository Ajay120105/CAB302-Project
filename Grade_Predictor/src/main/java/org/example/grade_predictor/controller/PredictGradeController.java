package org.example.grade_predictor.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.UserSession;

import java.io.IOException;
import java.util.List;

public class PredictGradeController {

    // The VBox container for showing enrolled units.
    @FXML
    private VBox unitsVBox;

    // Label for greeting the current user.
    @FXML
    private Label welcomeLabel;

    // Use the SQLite DAO for enrolled units.
    private SqliteEnrolledUnitDAO enrolledUnitDAO = new SqliteEnrolledUnitDAO();

    @FXML
    public void initialize() {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getFirst_name() + " " + currentUser.getLast_name();
            welcomeLabel.setText("Welcome, " + fullName + "!");
            loadEnrolledUnits(currentUser.getUser_ID());
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

    /**
     * Loads the enrolled units for the given student ID asynchronously and populates the VBox.
     */
    private void loadEnrolledUnits(int studentID) {
        Task<List<EnrolledUnit>> loadTask = new Task<>() {
            @Override
            protected List<EnrolledUnit> call() {
                return enrolledUnitDAO.getEnrolledUnitsForStudent(studentID);
            }
        };

        loadTask.setOnSucceeded(event -> {
            List<EnrolledUnit> enrolledUnits = loadTask.getValue();
            unitsVBox.getChildren().clear();
            if (enrolledUnits.isEmpty()) {
                unitsVBox.getChildren().add(new Label("You have not enrolled in any classes yet."));
            } else {
                for (EnrolledUnit eu : enrolledUnits) {
                    Node node = createEnrolledUnitNode(eu);
                    unitsVBox.getChildren().add(node);
                }
            }
        });

        loadTask.setOnFailed(event -> {
            showAlert("Error", "Could not load enrolled units from the backend.");
        });

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Creates a simple UI node for each enrolled unit using a TitledPane.
     */
    private Node createEnrolledUnitNode(EnrolledUnit eu) {
        TitledPane pane = new TitledPane();
        pane.setText(eu.getUnit_code());
        String content = "Year: " + eu.getYear_enrolled() + "\n" +
                "Semester: " + eu.getSemester_enrolled() + "\n" +
                "Weekly Hours: " + eu.getWeekly_hours();
        pane.setContent(new Label(content));
        pane.setAnimated(false);
        return pane;
    }

    @FXML
    protected void handleHome() {
        try {
            HelloApplication.switchToHomePage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Home page");
        }
    }

    @FXML
    protected void handleProfile() {
        showAlert("Profile", "Profile page is under construction.");
    }

    @FXML
    protected void handleSettings() {
        showAlert("Settings", "Settings page is under construction.");
    }

    @FXML
    protected void handleLogout() {
        showAlert("Log Out", "You have been logged out.");
        // Implement logout logic here.
    }

    @FXML
    protected void goToEditUnit() {
        try {
            HelloApplication.switchToEditUnitPage();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
        }
    }

    public void goToAllUnits(ActionEvent actionEvent) {
        try {
            HelloApplication.switchToAllUnitsPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open Edit Unit page.");
        }
    }

    @FXML
    protected void goToPredictGrade() {
        showAlert("Predict Grade", "You are already on the Predict Grade page.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
