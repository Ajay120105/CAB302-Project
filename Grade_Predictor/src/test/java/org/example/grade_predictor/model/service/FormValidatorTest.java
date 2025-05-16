package org.example.grade_predictor.model.service;

import org.example.grade_predictor.service.FormValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
} 