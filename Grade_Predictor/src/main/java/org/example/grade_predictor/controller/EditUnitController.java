package org.example.grade_predictor.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.UserSession;

import java.io.IOException;
import java.util.List;

public class EditUnitController {

    // Container for dynamically added enrollment panels.
    @FXML
    private VBox unitsVBox;

    // Label added to show welcome message, e.g., "Welcome, John Doe!"
    @FXML
    private Label welcomeLabel;

    // Use the SQLite enrollment DAO to work with enrolled units.
    private SqliteEnrolledUnitDAO enrolledUnitDAO = new SqliteEnrolledUnitDAO();

    @FXML
    public void initialize() {
        // Retrieve the currently logged-in user.
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
     * Loads the enrolled units for the given student ID asynchronously
     * and populates the VBox with a TitledPane for each enrollment.
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
                unitsVBox.getChildren().add(new Label("No enrolled units found."));
            } else {
                for (EnrolledUnit eu : enrolledUnits) {
                    TitledPane pane = createEnrolledUnitTitledPane(eu);
                    unitsVBox.getChildren().add(pane);
                }
            }
        });

        loadTask.setOnFailed(event -> {
            showAlert("Error", "Could not load enrolled units.");
        });

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Creates a TitledPane for an enrolled unit, displaying key details.
     */
    private TitledPane createEnrolledUnitTitledPane(EnrolledUnit eu) {
        AnchorPane contentPane = new AnchorPane();

        // Create a TextField for unit code (if you'd like them to be editable).
        TextField codeField = new TextField(eu.getUnit_code());
        codeField.setLayoutX(20);
        codeField.setLayoutY(14);

        // Create a TextField for enrollment year.
        TextField yearField = new TextField(String.valueOf(eu.getYear_enrolled()));
        yearField.setLayoutX(20);
        yearField.setLayoutY(50);

        // Create a TextField for semester enrolled.
        TextField semesterField = new TextField(String.valueOf(eu.getSemester_enrolled()));
        semesterField.setLayoutX(20);
        semesterField.setLayoutY(86);

        // Create a TextField for weekly hours.
        TextField hoursField = new TextField(String.valueOf(eu.getWeekly_hours()));
        hoursField.setLayoutX(20);
        hoursField.setLayoutY(122);

        // Create a delete button.
        Button deleteButton = new Button("Delete");
        deleteButton.setTextFill(Color.RED);
        deleteButton.setLayoutX(150);
        deleteButton.setLayoutY(122);
        deleteButton.setOnAction(evt -> deleteEnrolledUnit(eu));

        // Add controls to the content pane.
        contentPane.getChildren().addAll(codeField, yearField, semesterField, hoursField, deleteButton);

        // Create a TitledPane that uses the unit_code as the title.
        TitledPane pane = new TitledPane(eu.getUnit_code(), contentPane);
        pane.setAnimated(false);
        return pane;
    }

    /**
     * Deletes an enrolled unit record using the DAO and removes it from the UI.
     */
    private void deleteEnrolledUnit(EnrolledUnit eu) {
        enrolledUnitDAO.deleteEnrolledUnit(eu.getStudent_ID(), eu.getUnit_code());
        Platform.runLater(() -> {
            unitsVBox.getChildren().removeIf(node -> {
                if (node instanceof TitledPane) {
                    TitledPane tp = (TitledPane) node;
                    return tp.getText().equals(eu.getUnit_code());
                }
                return false;
            });
        });
    }

    // Navigation and alert methods:
    @FXML
    protected void handleHome() {
        try {
            HelloApplication.switchToHomePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Home page");
        }
    }

    @FXML
    protected void goToEditUnit() {
        showAlert("Edit Units", "You are already on the Edit Units Page.");
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
        // Clear the user session
        UserSession.clearSession();

        // Show logout confirmation
        showAlert("Log Out", "You have been logged out.");

        // Redirect to the first page (signup/login)
        try {
            HelloApplication.switchToSignup_LoginPage();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open Signup/Login page.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void goToPredictGrade() {
        try {
            HelloApplication.switchToPredictGradePage();
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open Predict Grade page.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
