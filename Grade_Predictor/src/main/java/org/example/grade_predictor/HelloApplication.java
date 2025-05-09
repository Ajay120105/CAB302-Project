package org.example.grade_predictor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 438, 362);
        stage.setTitle("Grade Predictor");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("Grade Predictor");
        primaryStage.setScene(scene); // fixed capitalization
        primaryStage.show();
    }

    public static void switchToEditUnitPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit_unit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
            primaryStage.setTitle("Edit Unit");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public static void switchToPredictGradePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predict_grade.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
            primaryStage.setTitle("Predict Grade");
            primaryStage.setScene(scene);
            primaryStage.show();
    }


    public static void switchToAllUnitsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AllUnits.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.setTitle("All Units");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
