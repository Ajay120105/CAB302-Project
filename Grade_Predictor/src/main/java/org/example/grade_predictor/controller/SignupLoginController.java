package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.example.grade_predictor.HelloApplication;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.DegreeService;
import org.example.grade_predictor.service.EnrollmentService;
import org.example.grade_predictor.service.FormValidator;

import java.io.IOException;
import java.util.List;

public class SignupLoginController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logInButton;

    @FXML
    private VBox enrollmentSection;

    @FXML
    private ComboBox<Degree> degreeComboBox;

    @FXML
    private TextField firstYearField;

    @FXML
    private TextField firstSemesterField;

    @FXML
    private TextField currentYearField;

    @FXML
    private TextField currentSemesterField;

    private final AuthenticateService authService = AuthenticateService.getInstance();
    private final DegreeService degreeService = new DegreeService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private boolean isSignUpMode = false;
    private List<Degree> allDegrees;

    @FXML
    protected void handleSignUp() {
        if (!isSignUpMode) {
            // Show additional fields for sign up
            firstNameField.setVisible(true);
            lastNameField.setVisible(true);
            phoneField.setVisible(true);
            firstNameLabel.setVisible(true);
            lastNameLabel.setVisible(true);
            phoneLabel.setVisible(true);

            // Move email and password fields down
            emailLabel.setLayoutY(226);
            emailField.setLayoutY(227);
            passwordLabel.setLayoutY(266);
            passwordField.setLayoutY(267);

            // Show enrollment section below phone field
            enrollmentSection.setVisible(true);
            degreeComboBox.setVisible(true);

            // Position buttons side by side in signup mode (lower to avoid overlap)
            signUpButton.setLayoutX(380);
            signUpButton.setLayoutY(520);
            logInButton.setLayoutX(520);
            logInButton.setLayoutY(520);

            isSignUpMode = true;
        } else {
            // Handle sign up logic
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Degree selectedDegree = degreeComboBox.getValue();
            String firstYear = firstYearField.getText();
            String firstSemester = firstSemesterField.getText();
            String currentYear = currentYearField.getText();
            String currentSemester = currentSemesterField.getText();

            if (selectedDegree == null) {
                showAlert("Error", "Please select a degree.");
                return;
            }
            String degreeId = selectedDegree.getDegree_ID();
            String degreeName = selectedDegree.getDegree_Name();

            System.out.println("Debug:");
            System.out.println("Name: " + firstName + lastName);
            System.out.println("Email: " + email);
            System.out.println("Degree ID: " + degreeId);
            System.out.println("Degree Name: " + degreeName);
            System.out.println("First Year: " + firstYear);
            System.out.println("First Semester: " + firstSemester);
            System.out.println("Current Year: " + currentYear);
            System.out.println("Current Semester: " + currentSemester);

            FormValidator.ValidationResult registrationResult =
                    FormValidator.validateRegistration(firstName, lastName, email, phone, password);

            if (!registrationResult.isValid()) {
                showAlert("Error", registrationResult.getErrorMessage());
                return;
            }

            FormValidator.ValidationResult degreeInfoResult =
                    FormValidator.validateDegreeInfo(degreeId, degreeName);
            if (!degreeInfoResult.isValid()) {
                showAlert("Error", degreeInfoResult.getErrorMessage());
                return;
            }

            FormValidator.ValidationResult enrollmentYearsResult = FormValidator.validateEnrollmentYears(firstYear, firstSemester, currentYear, currentSemester);
            if(!enrollmentYearsResult.isValid()){
                showAlert("Error", enrollmentYearsResult.getErrorMessage());
                return;
            }
            
            degreeId = degreeId.toUpperCase();

            try {
                Degree existingDegree = degreeService.getDegreeById(degreeId);

                if (existingDegree != null) {
                    // If degree exists, verify the name matches
                    // TODO: Allow user to update degree name?
                    if (!existingDegree.getDegree_Name().equals(degreeName)) {
                        showAlert("Error", "Degree ID and Name don't match. Please provide correct information.");
                        return;
                    }
                } else {
                    degreeService.addDegree(degreeName, degreeId);
                }

                try {
                    // Register the user
                    User newUser = authService.registerUser(firstName, lastName, email, phone, password);

                    // Create the enrollment with the degree ID
                    Enrollment enrollment = enrollmentService.createEnrollment(newUser, degreeId, Integer.parseInt(firstYear), Integer.parseInt(firstSemester), Integer.parseInt(currentYear), Integer.parseInt(currentSemester));

                    if (enrollment != null) {
                        showAlert("Success", "Sign Up successful!");
                        try {
                            HelloApplication.switchToHomePage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showAlert("Error", "Failed to create enrollment. Please try again.");
                        authService.logoutUser();
                        try {
                            authService.deleteUser(newUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlert("Error", "Failed to delete user: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed during signup process: " + e.getMessage());
                    authService.logoutUser();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed during signup process: " + e.getMessage());
                authService.logoutUser();
                return;
            }
        }
    }

    @FXML
    protected void handleLogIn() {
        if (isSignUpMode) {
            // Hide additional fields for log in
            firstNameField.setVisible(false);
            lastNameField.setVisible(false);
            phoneField.setVisible(false);
            firstNameLabel.setVisible(false);
            lastNameLabel.setVisible(false);
            phoneLabel.setVisible(false);

            // Move email and password fields back up
            emailLabel.setLayoutY(232);
            emailField.setLayoutY(232);
            passwordLabel.setLayoutY(272);
            passwordField.setLayoutY(272);

            // Hide enrollment section
            enrollmentSection.setVisible(false);
            degreeComboBox.setVisible(false);

            // Restore buttons to original stacked position for login mode
            signUpButton.setLayoutX(440);
            signUpButton.setLayoutY(325);
            logInButton.setLayoutX(440);
            logInButton.setLayoutY(362);

            isSignUpMode = false;
        } else {
            // Handle log in logic
            String email = emailField.getText();
            String password = passwordField.getText();

            FormValidator.ValidationResult loginResult =
                    FormValidator.validateLogin(email, password);

            if (!loginResult.isValid()) {
                showAlert("Error", loginResult.getErrorMessage());
                return;
            }

            if (authService.loginUser(email, password)) {
                try {
                    HelloApplication.switchToHomePage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Log In failed. Invalid email or password.");
            }
        }
    }

    @FXML
    public void initialize() {
        allDegrees = degreeService.getAllDegrees();

        degreeComboBox.setConverter(new StringConverter<Degree>() {
            @Override
            public String toString(Degree degree) {
                if (degree == null) {
                    return null;
                } else {
                    return degree.getDegree_Name() + " (" + degree.getDegree_ID() + ")";
                }
            }

            @Override
            public Degree fromString(String string) {
                // Not needed for a non-editable ComboBox
                return null;
            }
        });

        degreeComboBox.setCellFactory(param -> new ListCell<Degree>() {
            @Override
            protected void updateItem(Degree item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDegree_Name() + " (" + item.getDegree_ID() + ")");
                }
            }
        });

        degreeComboBox.getItems().addAll(allDegrees);
        degreeComboBox.setVisible(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}