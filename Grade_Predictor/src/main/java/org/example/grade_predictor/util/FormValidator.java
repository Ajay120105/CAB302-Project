package org.example.grade_predictor.util;

import java.util.regex.Pattern;

public class FormValidator {
    
    // RFC822 email pattern
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
    
    // Australia format - begin with 04 and 10 digits
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^04\\d{8}$");
    
    // Name pattern - only letters, spaces
    private static final Pattern NAME_PATTERN = 
            Pattern.compile("^[\\p{L} .'-]+$");
    
    // QUT style degree ID
    private static final Pattern DEGREE_ID_PATTERN = 
            Pattern.compile("^[A-Z]{2}\\d{2}$");
    
    // Minimum length for password
    private static final int MIN_PASSWORD_LENGTH = 6;
    
    /**
     * Validates registration form inputs
     * 
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param phone User's phone number
     * @param password User's password
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateRegistration(String firstName, String lastName, String email, String phone, String password) {
        if (firstName == null || firstName.trim().isEmpty() || !NAME_PATTERN.matcher(firstName).matches()) {
            return new ValidationResult(false, "First name does not satisfy the required format.");
        }
        
        if (lastName == null || lastName.trim().isEmpty() || !NAME_PATTERN.matcher(lastName).matches()) {
            return new ValidationResult(false, "Last name does not satisfy the required format.");
        }
        
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            return new ValidationResult(false, "Email does not satisfy the required format.");
        }
        
        if (phone == null || phone.trim().isEmpty() || !PHONE_PATTERN.matcher(phone).matches()) {
            return new ValidationResult(false, "Phone number does not satisfy the required format.");
        }
        
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult(false, "Password does not satisfy the required format.");
        }
        
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return new ValidationResult(false, "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates login form inputs
     * 
     * @param email User's email
     * @param password User's password
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateLogin(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return new ValidationResult(false, "Email is required.");
        }
        
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult(false, "Password is required.");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates degree ID format or checks that at least one of degreeId or degreeName is provided
     * 
     * @param degreeId The degree ID to validate
     * @param degreeName The degree name to validate alongside degreeId
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateDegreeInfo(String degreeId, String degreeName) {
        boolean hasId = degreeId != null && !degreeId.trim().isEmpty();
        boolean hasName = degreeName != null && !degreeName.trim().isEmpty();
        
        // Either degreeId or degreeName must be provided
        if (!hasId || !hasName) {
            return new ValidationResult(false, "Please provide a Degree ID and a Degree Name.");
        }

        if (!validateDegreeId(degreeId).isValid() || !validateDegreeName(degreeName).isValid()) {
            return new ValidationResult(false, "Invalid degree ID or name.");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates degree ID format
     * 
     * @param degreeId The degree ID to validate
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateDegreeId(String degreeId) {
        if (degreeId == null || degreeId.trim().isEmpty()) {
            return new ValidationResult(false, "Degree ID is required.");
        }
        
        if (!DEGREE_ID_PATTERN.matcher(degreeId).matches()) {
            return new ValidationResult(false, "Degree ID must be 2 uppercase letters followed by 2 digits (e.g., IN01).");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates degree name
     * 
     * @param degreeName The degree name to validate
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateDegreeName(String degreeName) {
        if (degreeName == null || degreeName.trim().isEmpty()) {
            return new ValidationResult(false, "Degree name is required.");
        }
        
        if (degreeName.length() < 1 || degreeName.length() > 100) {
            return new ValidationResult(false, "Degree name must be between 1 and 100 characters.");
        }
        
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates the enrollment years
     * @param firstYear The first year of the enrollment
     * @param firstSemester The first semester of the enrollment
     * @param currentYear The current year of the enrollment
     * @param currentSemester The current semester of the enrollment
     * @return The validation result
     */
    public static ValidationResult validateEnrollmentYears(String firstYear, String firstSemester, String currentYear, String currentSemester) {
        if (firstYear == null || firstYear.trim().isEmpty() || !firstYear.matches("\\d{4}")) {
            return new ValidationResult(false, "First year must be a 4-digit number.");
        }
        if (firstSemester == null || firstSemester.trim().isEmpty() || !firstSemester.matches("[1-2]")) {
            return new ValidationResult(false, "First semester must be 1 or 2.");
        }
        if (currentYear == null || currentYear.trim().isEmpty() || !currentYear.matches("\\d{4}")) {
            return new ValidationResult(false, "Current year must be a 4-digit number.");
        }
        if (currentSemester == null || currentSemester.trim().isEmpty() || !currentSemester.matches("[1-2]")) {
            return new ValidationResult(false, "Current semester must be 1 or 2.");
        }
        return new ValidationResult(true, "");
    }

    /**
     * Validates the intake years
     * @param intakeYear The intake year of the enrollment
     * @param intakeSemester The intake semester of the enrollment
     * @return The validation result
     */
    public static ValidationResult validateIntake(String intakeYear, String intakeSemester) {
        if (intakeYear == null || intakeYear.trim().isEmpty() || !intakeYear.matches("\\d{4}")) {
            return new ValidationResult(false, "Intake year must be a 4-digit number.");
        }
        if (intakeSemester == null || intakeSemester.trim().isEmpty() || !intakeSemester.matches("[1-2]")) {
            return new ValidationResult(false, "Intake semester must be 1 or 2.");
        }
        return new ValidationResult(true, "");
    }
    
    /**
     * Validates current academic period inputs (year and semester)
     * 
     * @param yearStr Current year as string
     * @param semesterStr Current semester as string
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateCurrentAcademicPeriod(String yearStr, String semesterStr) {
        if (yearStr == null || yearStr.trim().isEmpty()) {
            return new ValidationResult(false, "Current year is required.");
        }
        if (semesterStr == null || semesterStr.trim().isEmpty()) {
            return new ValidationResult(false, "Current semester is required.");
        }
        
        try {
            int year = Integer.parseInt(yearStr);
            int semester = Integer.parseInt(semesterStr);
            
            if (year < 2000 || year > 2100) {
                return new ValidationResult(false, "Year must be between 2000 and 2100.");
            }
            
            if (semester < 1 || semester > 2) {
                return new ValidationResult(false, "Semester must be 1 or 2.");
            }
            
            return new ValidationResult(true, "");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Please enter valid numeric values for year and semester.");
        }
    }
    
    /**
     * Validates study hours and efficiency inputs for grade prediction
     * 
     * @param studyHoursText Study hours per week as string
     * @param studyEfficiencyText Study efficiency (1-10) as string
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateStudyInputs(String studyHoursText, String studyEfficiencyText) {
        if (studyHoursText == null || studyHoursText.trim().isEmpty() || 
            studyEfficiencyText == null || studyEfficiencyText.trim().isEmpty()) {
            return new ValidationResult(false, "Please enter both study hours and study efficiency values.");
        }
        
        try {
            int studyHours = Integer.parseInt(studyHoursText);
            int studyEfficiency = Integer.parseInt(studyEfficiencyText);
            
            if (studyHours < 1 || studyHours > 168) {
                return new ValidationResult(false, "Study hours must be between 1 and 168 hours per week.");
            }
            
            if (studyEfficiency < 1 || studyEfficiency > 10) {
                return new ValidationResult(false, "Study efficiency must be between 1 and 10.");
            }
            
            return new ValidationResult(true, "");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Please enter valid numbers for study hours and study efficiency.");
        }
    }
    
    /**
     * Validates settings for Ollama host and model
     * 
     * @param host Ollama host URL
     * @param model Ollama model name
     * @return ValidationResult containing validation status and error message if any
     */
    public static ValidationResult validateSettings(String host, String model) {
        if (host == null || host.trim().isEmpty()) {
            return new ValidationResult(false, "Ollama host cannot be empty.");
        }
        
        if (model == null || model.trim().isEmpty()) {
            return new ValidationResult(false, "Please select a model from the list or enter a custom model name.");
        }
        
        // Host address fixup
        String fixedHost = host;
        if (fixedHost.endsWith("/")) {
            fixedHost = fixedHost.substring(0, fixedHost.length() - 1);
        }
        
        return new ValidationResult(true, "", fixedHost);
    }
    
    /**
     * Validation result class
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        private String transformedValue;
        
        /**
         * Constructor for the ValidationResult class
         * @param valid Whether the validation is valid
         * @param errorMessage The error message
         */
        public ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
            this.transformedValue = null;
        }
        
        /**
         * Constructor for the ValidationResult class
         * @param valid Whether the validation is valid
         * @param errorMessage The error message
         * @param transformedValue The transformed value
         */
        public ValidationResult(boolean valid, String errorMessage, String transformedValue) {
            this.valid = valid;
            this.errorMessage = errorMessage;
            this.transformedValue = transformedValue;
        }
        
        /**
         * Checks if the validation is valid
         * @return Whether the validation is valid
         */
        public boolean isValid() {
            return valid;
        }

        /**
         * Gets the error message
         * @return The error message
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * Gets the transformed value
         * @return The transformed value
         */
        public String getTransformedValue() {
            return transformedValue;
        }
    }
} 