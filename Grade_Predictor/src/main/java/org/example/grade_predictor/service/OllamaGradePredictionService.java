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
import org.example.grade_predictor.util.OllamaConnectionChecker;

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
    private final EnrollmentService enrollmentService;

    public OllamaGradePredictionService() {
        OllamaConfig config = new OllamaConfig();
        this.ollamaClient = new OllamaClient(config);
        this.executorService = Executors.newFixedThreadPool(2);
        this.authenticateService = AuthenticateService.getInstance();
        this.unitService = new UnitService();
        this.enrollmentService = new EnrollmentService();
        
        if (!OllamaConnectionChecker.isOllamaReadyForGeneration()) {
            System.err.println(OllamaConnectionChecker.getStatusErrorMessage());
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
        private String degreeProgram;
        
        public StudentInfo(String degreeProgram) {
            this.degreeProgram = degreeProgram;
        }
        
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
     * Complete data structure for single unit grade prediction request
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
     * Data structure for semester GPA prediction request
     */
    public static class SemesterGPAPredictionData {
        private StudentInfo studentInfo;
        private List<EnrolledUnitWithDetails> semesterUnits;
        private List<EnrolledUnitWithDetails> historicalUnits;
        private StudyPreferences studyPreferences;
        private int targetYear;
        private int targetSemester;
        
        public SemesterGPAPredictionData(StudentInfo studentInfo, List<EnrolledUnitWithDetails> semesterUnits,
                                        List<EnrolledUnitWithDetails> historicalUnits, StudyPreferences studyPreferences,
                                        int targetYear, int targetSemester) {
            this.studentInfo = studentInfo;
            this.semesterUnits = semesterUnits;
            this.historicalUnits = historicalUnits;
            this.studyPreferences = studyPreferences;
            this.targetYear = targetYear;
            this.targetSemester = targetSemester;
        }
        
        // Getters and setters
        public StudentInfo getStudentInfo() { return studentInfo; }
        public void setStudentInfo(StudentInfo studentInfo) { this.studentInfo = studentInfo; }
        public List<EnrolledUnitWithDetails> getSemesterUnits() { return semesterUnits; }
        public void setSemesterUnits(List<EnrolledUnitWithDetails> semesterUnits) { this.semesterUnits = semesterUnits; }
        public List<EnrolledUnitWithDetails> getHistoricalUnits() { return historicalUnits; }
        public void setHistoricalUnits(List<EnrolledUnitWithDetails> historicalUnits) { this.historicalUnits = historicalUnits; }
        public StudyPreferences getStudyPreferences() { return studyPreferences; }
        public void setStudyPreferences(StudyPreferences studyPreferences) { this.studyPreferences = studyPreferences; }
        public int getTargetYear() { return targetYear; }
        public void setTargetYear(int targetYear) { this.targetYear = targetYear; }
        public int getTargetSemester() { return targetSemester; }
        public void setTargetSemester(int targetSemester) { this.targetSemester = targetSemester; }
    }

    /**
     * Predicts the grade for a specific unit
     */
    public void predictUnitGrade(Enrollment enrollment, Degree degree, EnrolledUnit enrolledUnit, 
                                List<EnrolledUnit> enrolledUnits, int studyHours, int studyEfficiency, 
                                GradePredictionCallback callback) {
        
        if (!OllamaConnectionChecker.isOllamaReadyForGeneration()) {
            callback.onPredictionFailed(OllamaConnectionChecker.getStatusErrorMessage());
            return;
        }
        
        Task<GradeResponseDTO> task = new Task<>() {
            @Override
            protected GradeResponseDTO call() throws Exception {
                String prompt = buildUnitGradePrompt(enrollment, degree, enrolledUnit, enrolledUnits, studyHours, studyEfficiency);
                return executeRequest(prompt);
            }
        };
        
        setupTaskCallbacks(task, callback);
        executorService.submit(task);
    }

    /**
     * Predicts the GPA for an entire semester
     */
    public void predictSemesterGPA(Enrollment enrollment, Degree degree, int targetYear, int targetSemester,
                                  List<EnrolledUnit> semesterUnits, int averageStudyHours, int studyEfficiency,
                                  GradePredictionCallback callback) {
        
        if (!OllamaConnectionChecker.isOllamaReadyForGeneration()) {
            callback.onPredictionFailed(OllamaConnectionChecker.getStatusErrorMessage());
            return;
        }
        
        Task<GradeResponseDTO> task = new Task<>() {
            @Override
            protected GradeResponseDTO call() throws Exception {
                String prompt = buildSemesterGPAPrompt(enrollment, degree, targetYear, targetSemester, 
                                                      semesterUnits, averageStudyHours, studyEfficiency);
                System.out.println(prompt);
                return executeRequest(prompt);
            }
        };
        
        setupTaskCallbacks(task, callback);
        executorService.submit(task);
    }

    /**
     * @deprecated Use predictUnitGrade instead
     */
    @Deprecated
    public void predictGrade(Enrollment enrollment, Degree degree, EnrolledUnit enrolledUnit, 
                             List<EnrolledUnit> enrolledUnits, int studyHours, int studyEfficiency, 
                             GradePredictionCallback callback) {
        predictUnitGrade(enrollment, degree, enrolledUnit, enrolledUnits, studyHours, studyEfficiency, callback);
    }
    
    /**
     * Execute the request to Ollama
     */
    private GradeResponseDTO executeRequest(String prompt) throws Exception {
        OllamaRequestDTO request = new OllamaRequestDTO(prompt);
        request.setStream(false);
        request.setThinking(false);
        
        try {
            if (!ollamaClient.isOllamaRunning()) {
                throw new IOException(OllamaConnectionChecker.getConnectionErrorMessage());
            }
            
            OllamaResponseDTO response = ollamaClient.sendRequest(request);
            return parseJsonResponse(response.getResponse());
        } catch (IOException e) {
            if (!OllamaConnectionChecker.isOllamaConnected()) {
                throw new Exception(OllamaConnectionChecker.getConnectionErrorMessage());
            }
            e.printStackTrace();
            throw new Exception("Failed to get grade prediction: " + e.getMessage());
        }
    }

    /**
     * Setup task callbacks
     */
    private void setupTaskCallbacks(Task<GradeResponseDTO> task, GradePredictionCallback callback) {
        task.setOnSucceeded(event -> {
            GradeResponseDTO result = task.getValue();
            callback.onPredictionComplete(result);
        });
        
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            callback.onPredictionFailed(exception.getMessage());
        });
    }
    
    /**
     * Builds the prompt for single unit grade prediction
     */
    private String buildUnitGradePrompt(Enrollment enrollment, Degree degree, EnrolledUnit enrolledUnit,
                                       List<EnrolledUnit> enrolledUnits, int studyHours, int studyEfficiency) {
        StudentInfo studentInfo = new StudentInfo(degree.getDegree_Name());
        
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
        sb.append("\n\nEnsure the predictedGrade is a number between 0.0 and 7.0 on the standard 7-point GPA scale.");
        
        return sb.toString();
    }

    /**
     * Builds the prompt for semester GPA prediction
     */
    private String buildSemesterGPAPrompt(Enrollment enrollment, Degree degree, int targetYear, int targetSemester,
                                         List<EnrolledUnit> semesterUnits, int averageStudyHours, int studyEfficiency) {
        StudentInfo studentInfo = new StudentInfo(degree.getDegree_Name());
        
        // semester units
        List<EnrolledUnitWithDetails> semesterUnitsWithDetails = semesterUnits.stream()
                .map(eu -> {
                    Unit unitDetails = unitService.getUnitByCode(eu.getUnit_code());
                    return new EnrolledUnitWithDetails(eu, unitDetails);
                })
                .collect(Collectors.toList());
        
        // historical units
        List<EnrolledUnit> allEnrolledUnits = enrollmentService.getEnrolledUnits(enrollment);
        List<EnrolledUnitWithDetails> historicalUnits = allEnrolledUnits.stream()
                .filter(eu -> eu.getYear_enrolled() != targetYear || eu.getSemester_enrolled() != targetSemester)
                .map(eu -> {
                    Unit unitDetails = unitService.getUnitByCode(eu.getUnit_code());
                    return new EnrolledUnitWithDetails(eu, unitDetails);
                })
                .collect(Collectors.toList());
        
        StudyPreferences studyPreferences = new StudyPreferences(averageStudyHours, studyEfficiency);
        
        SemesterGPAPredictionData semesterData = new SemesterGPAPredictionData(
                studentInfo, semesterUnitsWithDetails, historicalUnits, studyPreferences, targetYear, targetSemester);
        
        Gson gson = new Gson();
        String jsonData = gson.toJson(semesterData);
        
        StringBuilder sb = new StringBuilder();
        sb.append("You are a university academic performance analyst. Predict the overall GPA for the specified semester based on the following JSON data:\n\n");
        sb.append("Data:\n");
        sb.append(jsonData);
        sb.append("\n\n");
        sb.append("Please analyze the student's current GPA, historical performance, the units they're taking this semester, ");
        sb.append("their study preferences, and provide a semester GPA prediction. Consider the difficulty and credit points of each unit. ");
        sb.append("Respond in the following JSON format:\n");
        sb.append("{\n");
        sb.append("  \"predictedGrade\": *.*,\n");
        sb.append("  \"reasoning\": \"Detailed analysis of how you arrived at this semester GPA prediction, considering each unit and the student's historical performance...\",\n");
        sb.append("  \"advice\": \"Strategic advice for the student to achieve or improve their predicted semester GPA, including specific study recommendations...\"\n");
        sb.append("}");
        sb.append("\n\nEnsure the predictedGrade represents the overall semester GPA between 0.0 and 7.0 on the standard 7-point GPA scale.");
        
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