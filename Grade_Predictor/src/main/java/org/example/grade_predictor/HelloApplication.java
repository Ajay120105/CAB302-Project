package org.example.grade_predictor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUnitDAO;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Stage primaryStage;

    /**
     * Switches to the signup/login page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToSignup_LoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Signup/Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Starts the application
     * @param stage the primary stage for the application
     * @throws IOException if the FXML file is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        stage.setTitle("Grade Predictor");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the home page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Grade Predictor");
        primaryStage.setScene(scene); // fixed capitalization
        primaryStage.show();
    }

    /**
     * Switches to the edit unit page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToEditUnitPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit_unit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
            primaryStage.setTitle("Edit Unit");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    /**
     * Switches to the predict grade page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToPredictGradePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predict_grade.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
            primaryStage.setTitle("Predict Grade");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    /**
     * Switches to the all units page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToAllUnitsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("all_units.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("All Units");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    /**
     * Switches to the login page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Login/Signup");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switches to the profile page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToProfilePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Profile Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Switches to the settings page
     * @throws IOException if the FXML file is not found
     */
    public static void switchToSettingsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
