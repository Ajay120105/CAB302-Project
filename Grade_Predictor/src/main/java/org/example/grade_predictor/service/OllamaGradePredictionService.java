package org.example.grade_predictor.service;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import org.example.grade_predictor.client.OllamaClient;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.GradeResponseDTO;
import org.example.grade_predictor.dto.OllamaRequestDTO;
import org.example.grade_predictor.dto.OllamaResponseDTO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.Unit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OllamaGradePredictionService {
    private final OllamaClient ollamaClient;
    private final ExecutorService executorService;
    private final AuthenticateService authenticateService;

    public OllamaGradePredictionService() {
        OllamaConfig config = new OllamaConfig();
        this.ollamaClient = new OllamaClient(config);
        this.executorService = Executors.newFixedThreadPool(2);
        this.authenticateService = AuthenticateService.getInstance();
    }

    /**
     * Predicts the grade for a user based on their enrollment, study hours, and efficiency
     *
     * @param enrollment The user's enrollment
     * @param degree The degree program
     * @param enrolledUnits List of units the user is enrolled in
     * @param units List of unit details
     * @param studyHours Weekly study hours
     * @param studyEfficiency Study efficiency (1-10)
     * @param callback The callback to handle the response
     */
    public void predictGrade(Enrollment enrollment, Degree degree, List<EnrolledUnit> enrolledUnits, 
                             List<Unit> units, int studyHours, int studyEfficiency, 
                             GradePredictionCallback callback) {
        
        Task<GradeResponseDTO> task = new Task<>() {
            @Override
            protected GradeResponseDTO call() throws Exception {
                String prompt = buildPrompt(enrollment, degree, enrolledUnits, units, studyHours, studyEfficiency);
                OllamaRequestDTO request = new OllamaRequestDTO(prompt);
                request.setStream(false);
                
                try {
                    OllamaResponseDTO response = ollamaClient.sendRequest(request);
                    return parseJsonResponse(response.getResponse());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("Failed to get grade prediction: " + e.getMessage());
                }
            }
        };
        
        task.setOnSucceeded(event -> {
            GradeResponseDTO result = task.getValue();
            callback.onPredictionComplete(result);
        });
        
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            callback.onPredictionFailed(exception.getMessage());
        });
        
        executorService.submit(task);
    }
    
    /**
     * Builds the prompt for the Ollama API based on enrollment data
     */
    private String buildPrompt(Enrollment enrollment, Degree degree, List<EnrolledUnit> enrolledUnits,
                             List<Unit> units, int studyHours, int studyEfficiency) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a university grade prediction professional. predict their grade based on the following data. ");
        
        // It might be better to serve request prompt also as a json object? Needs testing.
        sb.append("Student information:\n");
        sb.append("- Current GPA: ").append(enrollment.getCurrent_gpa()).append("\n");
        sb.append("- Degree program: ").append(degree.getDegree_Name()).append("\n");
        
        sb.append("Enrolled units:\n");
        for (EnrolledUnit enrolledUnit : enrolledUnits) {
            Unit unitDetails = units.stream()
                    .filter(u -> u.getUnit_code().equals(enrolledUnit.getUnit_code()))
                    .findFirst()
                    .orElse(null);
            
            if (unitDetails != null) {
                sb.append("- ").append(unitDetails.getUnit_code())
                  .append(" (").append(unitDetails.getUnit_name()).append("): ")
                  .append("Difficulty: ").append(unitDetails.getDifficulty())
                  .append(", Weekly hours: ").append(enrolledUnit.getWeekly_hours())
                  .append("\n");
            }
        }
        
        sb.append("Student's preferences on study:\n");
        sb.append("- Average weekly study hours: ").append(studyHours).append("\n");
        sb.append("- Study efficiency (1-10): ").append(studyEfficiency).append("\n");
        
        sb.append("Response in following JSON format:\n");
        sb.append("{\n");
        sb.append("  \"predictedGrade\": 7.0,\n");
        sb.append("  \"reasoning\": \"Reason for given that grade...\",\n");
        sb.append("  \"advice\": \"Advice for the student to improve their grade...\"\n");
        sb.append("}");
        
        return sb.toString();
    }
    
    /**
     * Parses the JSON response from Ollama
     */
    private GradeResponseDTO parseJsonResponse(String jsonResponse) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, GradeResponseDTO.class);
        } catch (Exception e) {
            System.err.println("Failed to parse JSON response: " + e.getMessage());
            return new GradeResponseDTO(0.0, 
                    "Failed to parse response from Ollama", 
                    "Please try again later");
        }
    }
    
    /**
     * Shutdown the executor service
     */
    public void shutdown() {
        executorService.shutdown();
    }
    
    /**
     * Interface for handling the grade prediction callback
     */
    public interface GradePredictionCallback {
        void onPredictionComplete(GradeResponseDTO response);
        void onPredictionFailed(String errorMessage);
    }
} 