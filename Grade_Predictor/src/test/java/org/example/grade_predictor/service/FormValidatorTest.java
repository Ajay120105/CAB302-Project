package org.example.grade_predictor.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.grade_predictor.util.FormValidator;

public class FormValidatorTest {

    @Test
    public void testValidateRegistration_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "0412345678", "password123"
        );
        
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateRegistration_InvalidFirstName() {
        // Empty first name or invalid characters
        FormValidator.ValidationResult result1 = FormValidator.validateRegistration(
            "", "Doe", "john.doe@example.com", "0412345678", "password123"
        );
        assertFalse(result1.isValid());
        
        // Name with numbers
        FormValidator.ValidationResult result2 = FormValidator.validateRegistration(
            "John123", "Doe", "john.doe@example.com", "0412345678", "password123"
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateRegistration_InvalidLastName() {
        // Empty last name or invalid characters
        FormValidator.ValidationResult result1 = FormValidator.validateRegistration(
            "John", "", "john.doe@example.com", "0412345678", "password123"
        );
        assertFalse(result1.isValid());
        
        // Name with special characters
        FormValidator.ValidationResult result2 = FormValidator.validateRegistration(
            "John", "Doe@$%", "john.doe@example.com", "0412345678", "password123"
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateRegistration_InvalidEmail() {
        // Empty email
        FormValidator.ValidationResult result1 = FormValidator.validateRegistration(
            "John", "Doe", "", "0412345678", "password123"
        );
        assertFalse(result1.isValid());
        
        // Invalid email formats
        FormValidator.ValidationResult result2 = FormValidator.validateRegistration(
            "John", "Doe", "not-an-email", "0412345678", "password123"
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateRegistration_InvalidPhone() {
        // Empty phone
        FormValidator.ValidationResult result1 = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "", "password123"
        );
        assertFalse(result1.isValid());
        
        // Non-Australian format
        FormValidator.ValidationResult result2 = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "1234567890", "password123"
        );
        assertFalse(result2.isValid());
        
        // Incorrect format with hyphens
        FormValidator.ValidationResult result3 = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "04-1234-5678", "password123"
        );
        assertFalse(result3.isValid());
    }
    
    @Test
    public void testValidateRegistration_InvalidPassword() {
        // Empty password
        FormValidator.ValidationResult result1 = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "0412345678", ""
        );
        assertFalse(result1.isValid());
        
        // Password too short
        FormValidator.ValidationResult result2 = FormValidator.validateRegistration(
            "John", "Doe", "john.doe@example.com", "0412345678", "12345"
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateLogin_Valid() {
        // Valid login data
        FormValidator.ValidationResult result = FormValidator.validateLogin(
            "john.doe@example.com", "password123"
        );
        
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateLogin_EmptyFields() {
        // Empty email
        FormValidator.ValidationResult result1 = FormValidator.validateLogin(
            "", "password123"
        );
        assertFalse(result1.isValid());
        
        // Empty password
        FormValidator.ValidationResult result2 = FormValidator.validateLogin(
            "john.doe@example.com", ""
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateDegreeInfo_Valid() {
        // Valid with both ID and name
        FormValidator.ValidationResult result1 = FormValidator.validateDegreeInfo(
            "IN01", "Bachelor of Information Technology"
        );
        assertTrue(result1.isValid());
        
        // Valid with just ID
        FormValidator.ValidationResult result2 = FormValidator.validateDegreeInfo(
            "IN01", ""
        );
        assertFalse(result2.isValid());
        
        // Valid with just name
        FormValidator.ValidationResult result3 = FormValidator.validateDegreeInfo(
            "", "Bachelor of Information Technology"
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateDegreeInfo_Missing() {
        // Missing both ID and name
        FormValidator.ValidationResult result = FormValidator.validateDegreeInfo("", "");
        assertFalse(result.isValid());
    }
    
    @Test
    public void testValidateDegreeInfo_InvalidName() {
        FormValidator.ValidationResult result = FormValidator.validateDegreeInfo(
            "IN01", ""
        );
        assertFalse(result.isValid());
        
        // Empty ID but invalid (empty) name
        FormValidator.ValidationResult result2 = FormValidator.validateDegreeInfo(
            "", " "
        );
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateDegreeId_Valid() {
        // Valid degree ID
        FormValidator.ValidationResult result = FormValidator.validateDegreeId("IN01");
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateDegreeId_Invalid() {
        // Empty degree ID (now required)
        FormValidator.ValidationResult result1 = FormValidator.validateDegreeId("");
        assertFalse(result1.isValid());
        
        // Invalid format
        FormValidator.ValidationResult result2 = FormValidator.validateDegreeId("I123");
        assertFalse(result2.isValid());
        
        // Lowercase letters
        FormValidator.ValidationResult result3 = FormValidator.validateDegreeId("in01");
        assertFalse(result3.isValid());
    }
    
    @Test
    public void testValidateDegreeName_Valid() {
        // Valid degree name
        FormValidator.ValidationResult result1 = FormValidator.validateDegreeName("Bachelor of Information Technology");
        assertTrue(result1.isValid());
        
        // Minimum length (1 character)
        FormValidator.ValidationResult result2 = FormValidator.validateDegreeName("B");
        assertTrue(result2.isValid());
    }
    
    @Test
    public void testValidateDegreeName_Invalid() {
        // Empty degree name (now required)
        FormValidator.ValidationResult result1 = FormValidator.validateDegreeName("");
        assertFalse(result1.isValid());
        
        // Too long
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            longName.append("a");
        }
        FormValidator.ValidationResult result2 = FormValidator.validateDegreeName(longName.toString());
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateEnrollmentYears_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateEnrollmentYears(
            "2020", "1", "2024", "2"
        );
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateEnrollmentYears_InvalidFirstYear() {
        // Empty first year
        FormValidator.ValidationResult result1 = FormValidator.validateEnrollmentYears(
            "", "1", "2024", "2"
        );
        assertFalse(result1.isValid());
        
        // Invalid year format
        FormValidator.ValidationResult result2 = FormValidator.validateEnrollmentYears(
            "20", "1", "2024", "2"
        );
        assertFalse(result2.isValid());
        
        // Non-numeric year
        FormValidator.ValidationResult result3 = FormValidator.validateEnrollmentYears(
            "abcd", "1", "2024", "2"
        );
        assertFalse(result3.isValid());
    }
    
    @Test
    public void testValidateEnrollmentYears_InvalidSemester() {
        // Invalid first semester
        FormValidator.ValidationResult result1 = FormValidator.validateEnrollmentYears(
            "2020", "3", "2024", "2"
        );
        assertFalse(result1.isValid());
        
        // Invalid current semester
        FormValidator.ValidationResult result2 = FormValidator.validateEnrollmentYears(
            "2020", "1", "2024", "0"
        );
        assertFalse(result2.isValid());
        
        // Empty semester
        FormValidator.ValidationResult result3 = FormValidator.validateEnrollmentYears(
            "2020", "", "2024", "2"
        );
        assertFalse(result3.isValid());
    }
    
    @Test
    public void testValidateIntake_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateIntake("2024", "1");
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateIntake_Invalid() {
        // Invalid year
        FormValidator.ValidationResult result1 = FormValidator.validateIntake("abc", "1");
        assertFalse(result1.isValid());
        
        // Invalid semester
        FormValidator.ValidationResult result2 = FormValidator.validateIntake("2024", "3");
        assertFalse(result2.isValid());
        
        // Empty fields
        FormValidator.ValidationResult result3 = FormValidator.validateIntake("", "");
        assertFalse(result3.isValid());
    }
    
    @Test
    public void testValidateCurrentAcademicPeriod_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateCurrentAcademicPeriod("2024", "1");
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidateCurrentAcademicPeriod_InvalidYear() {
        // Year too low
        FormValidator.ValidationResult result1 = FormValidator.validateCurrentAcademicPeriod("1999", "1");
        assertFalse(result1.isValid());
        
        // Year too high
        FormValidator.ValidationResult result2 = FormValidator.validateCurrentAcademicPeriod("2101", "1");
        assertFalse(result2.isValid());
        
        // Non-numeric year
        FormValidator.ValidationResult result3 = FormValidator.validateCurrentAcademicPeriod("abcd", "1");
        assertFalse(result3.isValid());
        
        // Empty year
        FormValidator.ValidationResult result4 = FormValidator.validateCurrentAcademicPeriod("", "1");
        assertFalse(result4.isValid());
    }
    
    @Test
    public void testValidateCurrentAcademicPeriod_InvalidSemester() {
        // Semester too low
        FormValidator.ValidationResult result1 = FormValidator.validateCurrentAcademicPeriod("2024", "0");
        assertFalse(result1.isValid());
        
        // Semester too high
        FormValidator.ValidationResult result2 = FormValidator.validateCurrentAcademicPeriod("2024", "3");
        assertFalse(result2.isValid());
        
        // Non-numeric semester
        FormValidator.ValidationResult result3 = FormValidator.validateCurrentAcademicPeriod("2024", "a");
        assertFalse(result3.isValid());
        
        // Empty semester
        FormValidator.ValidationResult result4 = FormValidator.validateCurrentAcademicPeriod("2024", "");
        assertFalse(result4.isValid());
    }
    
    @Test
    public void testValidateStudyInputs_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateStudyInputs("20", "8");
        assertTrue(result.isValid());
        
        // Boundary values
        FormValidator.ValidationResult result1 = FormValidator.validateStudyInputs("1", "1");
        assertTrue(result1.isValid());
        
        FormValidator.ValidationResult result2 = FormValidator.validateStudyInputs("168", "10");
        assertTrue(result2.isValid());
    }
    
    @Test
    public void testValidateStudyInputs_InvalidHours() {
        // Hours too low
        FormValidator.ValidationResult result1 = FormValidator.validateStudyInputs("0", "5");
        assertFalse(result1.isValid());
        
        // Hours too high
        FormValidator.ValidationResult result2 = FormValidator.validateStudyInputs("169", "5");
        assertFalse(result2.isValid());
        
        // Non-numeric hours
        FormValidator.ValidationResult result3 = FormValidator.validateStudyInputs("abc", "5");
        assertFalse(result3.isValid());
        
        // Empty hours
        FormValidator.ValidationResult result4 = FormValidator.validateStudyInputs("", "5");
        assertFalse(result4.isValid());
    }
    
    @Test
    public void testValidateStudyInputs_InvalidEfficiency() {
        // Efficiency too low
        FormValidator.ValidationResult result1 = FormValidator.validateStudyInputs("20", "0");
        assertFalse(result1.isValid());
        
        // Efficiency too high
        FormValidator.ValidationResult result2 = FormValidator.validateStudyInputs("20", "11");
        assertFalse(result2.isValid());
        
        // Non-numeric efficiency
        FormValidator.ValidationResult result3 = FormValidator.validateStudyInputs("20", "abc");
        assertFalse(result3.isValid());
        
        // Empty efficiency
        FormValidator.ValidationResult result4 = FormValidator.validateStudyInputs("20", "");
        assertFalse(result4.isValid());
    }
    
    @Test
    public void testValidateSettings_Valid() {
        FormValidator.ValidationResult result = FormValidator.validateSettings("http://localhost:11434", "llama2");
        assertTrue(result.isValid());
        assertEquals("http://localhost:11434", result.getTransformedValue());
        
        // Test with trailing slash removal
        FormValidator.ValidationResult result2 = FormValidator.validateSettings("http://localhost:11434/", "llama2");
        assertTrue(result2.isValid());
        assertEquals("http://localhost:11434", result2.getTransformedValue());
    }
    
    @Test
    public void testValidateSettings_InvalidHost() {
        // Empty host
        FormValidator.ValidationResult result1 = FormValidator.validateSettings("", "llama2");
        assertFalse(result1.isValid());
        assertTrue(result1.getErrorMessage().contains("host cannot be empty"));
        
        // Null host
        FormValidator.ValidationResult result2 = FormValidator.validateSettings(null, "llama2");
        assertFalse(result2.isValid());
    }
    
    @Test
    public void testValidateSettings_InvalidModel() {
        // Empty model
        FormValidator.ValidationResult result1 = FormValidator.validateSettings("http://localhost:11434", "");
        assertFalse(result1.isValid());
        assertTrue(result1.getErrorMessage().contains("select a model"));
        
        // Null model
        FormValidator.ValidationResult result2 = FormValidator.validateSettings("http://localhost:11434", null);
        assertFalse(result2.isValid());
    }
} 