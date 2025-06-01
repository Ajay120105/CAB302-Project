package org.example.grade_predictor.dto;

import com.google.gson.Gson;

public class GradeResponseDTO {
    private double predictedGrade;
    private String reasoning;
    private String advice;

    public GradeResponseDTO() {
    }

    /**
     * Constructor for the GradeResponseDTO object
     * @param predictedGrade The predicted grade
     * @param reasoning The reasoning
     * @param advice The advice
     */
    public GradeResponseDTO(double predictedGrade, String reasoning, String advice) {
        this.predictedGrade = predictedGrade;
        this.reasoning = reasoning;
        this.advice = advice;
    }

    /**
     * Get the predicted grade for the GradeResponseDTO object
     * @return The predicted grade
     */
    public double getPredictedGrade() {
        return predictedGrade;
    }
    
    /**
     * Set the predicted grade for the GradeResponseDTO object
     * @param predictedGrade The predicted grade to set
     */
    public void setPredictedGrade(double predictedGrade) {
        this.predictedGrade = predictedGrade;
    }

    /**
     * Get the reasoning for the GradeResponseDTO object
     * @return The reasoning
     */
    public String getReasoning() {
        return reasoning;
    }
    
    /**
     * Set the reasoning for the GradeResponseDTO object
     * @param reasoning The reasoning to set
     */
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    /**
     * Get the advice for the GradeResponseDTO object
     * @return The advice
     */
    public String getAdvice() {
        return advice;
    }

    /**
     * Set the advice for the GradeResponseDTO object
     * @param advice The advice to set
     */
    public void setAdvice(String advice) {
        this.advice = advice;
    }

    /**
     * Convert a JSON string to a GradeResponseDTO object
     * @param json The JSON string to convert
     * @return The GradeResponseDTO object
     */
    public static GradeResponseDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GradeResponseDTO.class);
    }

    /**
     * Convert the GradeResponseDTO object to a string representation for debugging purposes
     * @return A string representation of the GradeResponseDTO object
     */
    @Override
    public String toString() {
        return "Grade Prediction:" +
                "- Predicted Grade: " + predictedGrade +
                "- Reasoning: " + reasoning +
                "- Advice: " + advice;
    }
} 