package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private TextField degreeIdField;

    @FXML
    private TextField degreeNameField;

    @FXML
    private Label enrollmentHelperText;

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
            enrollmentHelperText.setVisible(true);

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
            String degreeId = degreeIdField.getText();
            String degreeName = degreeNameField.getText();

            System.out.println("Debug:");
            System.out.println("Name: " + firstName + lastName);
            System.out.println("Email: " + email);
            System.out.println("Degree ID: " + degreeId);
            System.out.println("Degree Name: " + degreeName);

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
                    User newUser = authService.registerUser(firstName, lastName, email, phone, password, null);

                    // Create the enrollment with the degree ID
                    Enrollment enrollment = enrollmentService.createEnrollment(newUser, degreeId);

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
            enrollmentHelperText.setVisible(false);

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
                showAlert("Success", "Log In successful!");
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
    protected void handleDegreeIdChanged() {
        System.out.println("handleDegreeIdChanged triggered");
        String degreeId = degreeIdField.getText();
        if (!degreeId.isEmpty()) {
            FormValidator.ValidationResult degreeIdResult = FormValidator.validateDegreeId(degreeId);
            if (!degreeIdResult.isValid()) {
                return;
            }

            Degree degree = degreeService.getDegreeById(degreeId);
            if (degree != null) {
                System.out.println("Degree found: " + degree.getDegree_Name());
                degreeNameField.setText(degree.getDegree_Name());
            } else {
                System.out.println("Degree not found: " + degreeId);
            }
        } else {
            System.out.println("Degree ID is empty");
        }
    }

    @FXML
    public void initialize() {
        allDegrees = degreeService.getAllDegrees();

        degreeIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                handleDegreeIdChanged();
            }
        });

        enrollmentHelperText.setVisible(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}