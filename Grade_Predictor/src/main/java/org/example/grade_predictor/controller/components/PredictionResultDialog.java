package org.example.grade_predictor.controller.components;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.grade_predictor.dto.GradeResponseDTO;

public class PredictionResultDialog {
    
    public static void showUnitPredictionResult(String unitCode, GradeResponseDTO response) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Grade Prediction Result");
        alert.setHeaderText("Unit: " + unitCode);
        
        VBox content = createResultContent(
            String.format("%.2f", response.getPredictedGrade()) + " / 7.0",
            response.getReasoning(),
            response.getAdvice()
        );
        
        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefWidth(600);
        alert.getDialogPane().setPrefHeight(500);
        alert.showAndWait();
    }
    
    public static void showSemesterPredictionResult(int year, int semester, GradeResponseDTO response) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Semester GPA Prediction");
        alert.setHeaderText(String.format("Year %d, Semester %d", year, semester));
        
        VBox content = createResultContent(
            String.format("%.2f", response.getPredictedGrade()) + " GPA",
            response.getReasoning(),
            response.getAdvice()
        );
        
        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefWidth(650);
        alert.getDialogPane().setPrefHeight(550);
        alert.showAndWait();
    }
    
    private static VBox createResultContent(String gradeText, String reasoning, String advice) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        Label gradeLabel = new Label("Predicted Grade:");
        gradeLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        Label gradeValueLabel = new Label(gradeText);
        gradeValueLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        gradeValueLabel.setStyle("-fx-text-fill: #2E7D32;");
        
        Label reasoningLabel = new Label("Reasoning:");
        reasoningLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        TextArea reasoningText = new TextArea(reasoning);
        reasoningText.setWrapText(true);
        reasoningText.setEditable(false);
        reasoningText.setPrefRowCount(4);
        reasoningText.setStyle("-fx-background-color: #F5F5F5;");
        
        Label adviceLabel = new Label("Advice:");
        adviceLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        TextArea adviceText = new TextArea(advice);
        adviceText.setWrapText(true);
        adviceText.setEditable(false);
        adviceText.setPrefRowCount(3);
        adviceText.setStyle("-fx-background-color: #E3F2FD;");
        
        content.getChildren().addAll(
            gradeLabel,
            gradeValueLabel,
            new Separator(),
            reasoningLabel,
            reasoningText,
            adviceLabel,
            adviceText
        );
        
        return content;
    }
} 