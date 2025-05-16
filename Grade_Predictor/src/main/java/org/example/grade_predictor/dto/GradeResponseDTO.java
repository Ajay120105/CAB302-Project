package org.example.grade_predictor.dto;

import com.google.gson.Gson;

public class GradeResponseDTO {
    private double predictedGrade;
    private String reasoning;
    private String advice;

    public GradeResponseDTO() {
    }

    public GradeResponseDTO(double predictedGrade, String reasoning, String advice) {
        this.predictedGrade = predictedGrade;
        this.reasoning = reasoning;
        this.advice = advice;
    }

    public double getPredictedGrade() {
        return predictedGrade;
    }

    public void setPredictedGrade(double predictedGrade) {
        this.predictedGrade = predictedGrade;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public static GradeResponseDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GradeResponseDTO.class);
    }

    @Override
    public String toString() {
        return "Grade Prediction:" +
                "- Predicted Grade: " + predictedGrade +
                "- Reasoning: " + reasoning +
                "- Advice: " + advice;
    }
} 