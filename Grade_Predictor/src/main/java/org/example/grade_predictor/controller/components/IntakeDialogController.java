package org.example.grade_predictor.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.grade_predictor.service.FormValidator;
import javafx.scene.control.Alert;

public class IntakeDialogController {

    @FXML
    private TextField intakeYearField;

    @FXML
    private TextField intakeSemesterField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;
    private IntakeData intakeData;

    public static class IntakeData {
        private final int year;
        private final int semester;

        public IntakeData(int year, int semester) {
            this.year = year;
            this.semester = semester;
        }

        public int getYear() {
            return year;
        }

        public int getSemester() {
            return semester;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public IntakeData getIntakeData() {
        return intakeData;
    }

    @FXML
    private void handleOk() {
        String yearStr = intakeYearField.getText();
        String semesterStr = intakeSemesterField.getText();
        String errorMessage = "";

        FormValidator.ValidationResult result = FormValidator.validateIntake(yearStr, semesterStr);
        if(result.isValid()) {
            intakeData = new IntakeData(Integer.parseInt(intakeYearField.getText()), Integer.parseInt(intakeSemesterField.getText()));
            okClicked = true;
            dialogStage.close();
        } else {
            errorMessage += result.getErrorMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
} 