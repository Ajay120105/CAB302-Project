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
import java.util.stream.Collectors;

public class OllamaGradePredictionService {
    private final OllamaClient ollamaClient;
    private final ExecutorService executorService;
    private final AuthenticateService authenticateService;
    private final UnitService unitService;

    public OllamaGradePredictionService() {
        OllamaConfig config = new OllamaConfig();
        this.ollamaClient = new OllamaClient(config);
        this.executorService = Executors.newFixedThreadPool(2);
        this.authenticateService = AuthenticateService.getInstance();
        this.unitService = new UnitService();
        
        if (!ollamaClient.isOllamaRunning()) {
            System.err.println("WARNING: Ollama service is not running.");
        }
    }

    /**
     * Data structure for enrolled unit with unit details
     */
    public static class EnrolledUnitWithDetails {
        private EnrolledUnit enrolledUnit;
        private Unit unit;
        
        public EnrolledUnitWithDetails(EnrolledUnit enrolledUnit, Unit unit) {
            this.enrolledUnit = enrolledUnit;
            this.unit = unit;
        }
        
        public EnrolledUnit getEnrolledUnit() { return enrolledUnit; }
        public void setEnrolledUnit(EnrolledUnit enrolledUnit) { this.enrolledUnit = enrolledUnit; }
        public Unit getUnit() { return unit; }
        public void setUnit(Unit unit) { this.unit = unit; }
    }
    
    /**
     * Data structure for student information
     */
    public static class StudentInfo {
        private double currentGPA;
        private String degreeProgram;
        
        public StudentInfo(double currentGPA, String degreeProgram) {
            this.currentGPA = currentGPA;
            this.degreeProgram = degreeProgram;
        }
        
        public double getCurrentGPA() { return currentGPA; }
        public void setCurrentGPA(double currentGPA) { this.currentGPA = currentGPA; }
        public String getDegreeProgram() { return degreeProgram; }
        public void setDegreeProgram(String degreeProgram) { this.degreeProgram = degreeProgram; }
    }
    
    /**
     * Data structure for study preferences
     */
    public static class StudyPreferences {
        private int averageWeeklyStudyHours;
        private int studyEfficiency;
        
        public StudyPreferences(int averageWeeklyStudyHours, int studyEfficiency) {
            this.averageWeeklyStudyHours = averageWeeklyStudyHours;
            this.studyEfficiency = studyEfficiency;
        }
        
        public int getAverageWeeklyStudyHours() { return averageWeeklyStudyHours; }
        public void setAverageWeeklyStudyHours(int averageWeeklyStudyHours) { this.averageWeeklyStudyHours = averageWeeklyStudyHours; }
        public int getStudyEfficiency() { return studyEfficiency; }
        public void setStudyEfficiency(int studyEfficiency) { this.studyEfficiency = studyEfficiency; }
    }
    
    /**
     * Complete data structure for grade prediction request
     */
    public static class GradePredictionData {
        private StudentInfo studentInfo;
        private EnrolledUnitWithDetails targetUnit;
        private List<EnrolledUnitWithDetails> enrolledUnits;
        private StudyPreferences studyPreferences;
        
        public GradePredictionData(StudentInfo studentInfo, EnrolledUnitWithDetails targetUnit, 
                                   List<EnrolledUnitWithDetails> enrolledUnits, StudyPreferences studyPreferences) {
            this.studentInfo = studentInfo;
            this.targetUnit = targetUnit;
            this.enrolledUnits = enrolledUnits;
            this.studyPreferences = studyPreferences;
        }
        
        public StudentInfo getStudentInfo() { return studentInfo; }
        public void setStudentInfo(StudentInfo studentInfo) { this.studentInfo = studentInfo; }
        public EnrolledUnitWithDetails getTargetUnit() { return targetUnit; }
        public void setTargetUnit(EnrolledUnitWithDetails targetUnit) { this.targetUnit = targetUnit; }
        public List<EnrolledUnitWithDetails> getEnrolledUnits() { return enrolledUnits; }
        public void setEnrolledUnits(List<EnrolledUnitWithDetails> enrolledUnits) { this.enrolledUnits = enrolledUnits; }
        public StudyPreferences getStudyPreferences() { return studyPreferences; }
        public void setStudyPreferences(StudyPreferences studyPreferences) { this.studyPreferences = studyPreferences; }
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
    public void predictGrade(Enrollment enrollment, Degree degree, EnrolledUnit enrolledUnit, 
                             List<EnrolledUnit> enrolledUnits, int studyHours, int studyEfficiency, 
                             GradePredictionCallback callback) {
        
        Task<GradeResponseDTO> task = new Task<>() {
            @Override
            protected GradeResponseDTO call() throws Exception {
                String prompt = buildPrompt(enrollment, degree, enrolledUnit, enrolledUnits, studyHours, studyEfficiency);
                OllamaRequestDTO request = new OllamaRequestDTO(prompt);
                request.setStream(false);
                request.setThinking(false);
                
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
    private String buildPrompt(Enrollment enrollment, Degree degree, EnrolledUnit enrolledUnit,
                             List<EnrolledUnit> enrolledUnits, int studyHours, int studyEfficiency) {
        StudentInfo studentInfo = new StudentInfo(enrollment.getCurrent_gpa(), degree.getDegree_Name());
        
        Unit targetUnitDetails = unitService.getUnitByCode(enrolledUnit.getUnit_code());
        EnrolledUnitWithDetails targetUnit = new EnrolledUnitWithDetails(enrolledUnit, targetUnitDetails);
        
        List<EnrolledUnitWithDetails> enrolledUnitsWithDetails = enrolledUnits.stream()
                .map(eu -> {
                    Unit unitDetails = unitService.getUnitByCode(eu.getUnit_code());
                    return new EnrolledUnitWithDetails(eu, unitDetails);
                })
                .collect(Collectors.toList());
        
        StudyPreferences studyPreferences = new StudyPreferences(studyHours, studyEfficiency);
        
        GradePredictionData gradePredictionData = new GradePredictionData(
                studentInfo, targetUnit, enrolledUnitsWithDetails, studyPreferences);
        
        Gson gson = new Gson();
        String jsonData = gson.toJson(gradePredictionData);
        
        StringBuilder sb = new StringBuilder();
        sb.append("You are a university grade prediction professional. Predict the grade for the target unit based on the following JSON data:\n\n");
        sb.append("Data:\n");
        sb.append(jsonData);
        sb.append("\n\n");
        sb.append("Please analyze the student's current GPA, enrolled units with their weekly hours, study preferences, and provide a grade prediction for the target unit. ");
        sb.append("Respond in the following JSON format:\n");
        sb.append("{\n");
        sb.append("  \"predictedGrade\": *.*,\n");
        sb.append("  \"reasoning\": \"Detailed reasoning for the predicted grade based on the provided data...\",\n");
        sb.append("  \"advice\": \"Specific advice for the student to improve their grade in this unit...\"\n");
        sb.append("}");
        
        return sb.toString();
    }
    
    /**
     * Parses the JSON response from Ollama
     */
    private GradeResponseDTO parseJsonResponse(String jsonResponse) {
        try {
            String cleanedResponse = extractJsonFromResponse(jsonResponse);
            
            Gson gson = new Gson();
            return gson.fromJson(cleanedResponse, GradeResponseDTO.class);
        } catch (Exception e) {
            System.err.println("Failed to parse JSON response: " + e.getMessage());
            return new GradeResponseDTO(0.0, 
                    "Failed to parse response from Ollama", 
                    "Please try again later");
        }
    }
    
    /**
     * Find JSON from response
     */
    private String extractJsonFromResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            return "{}";
        }
        
        String cleaned = response.replaceAll("<think>.*?</think>", "").trim();
        
        int jsonStart = cleaned.indexOf('{');
        int jsonEnd = cleaned.lastIndexOf('}');
        
        if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
            return cleaned.substring(jsonStart, jsonEnd + 1);
        }
        
        return cleaned;
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