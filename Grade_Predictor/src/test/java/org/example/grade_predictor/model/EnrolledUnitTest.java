package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrolledUnitTest {

    private EnrolledUnit enrolledUnit;

    @BeforeEach
    public void setUp() {
        enrolledUnit = new EnrolledUnit(1, "CAB302", 2024, 2, 12, 7.5);
    }

    @Test
    public void testEnrolledUnitConstructor() {
        assertEquals(1, enrolledUnit.getEnrollment_ID());
        assertEquals("CAB302", enrolledUnit.getUnit_code());
        assertEquals(2024, enrolledUnit.getYear_enrolled());
        assertEquals(2, enrolledUnit.getSemester_enrolled());
        assertEquals(12, enrolledUnit.getWeekly_hours());
        assertEquals(7.5, enrolledUnit.getFinalised_gpa());
    }

    @Test
    public void testSettersAndGetters() {
        // Test Enrollment ID
        enrolledUnit.setEnrollment_ID(999);
        assertEquals(999, enrolledUnit.getEnrollment_ID());

        // Test Unit Code
        enrolledUnit.setUnit_code("CAB401");
        assertEquals("CAB401", enrolledUnit.getUnit_code());

        // Test Year Enrolled
        enrolledUnit.setYear_enrolled(2025);
        assertEquals(2025, enrolledUnit.getYear_enrolled());

        // Test Semester Enrolled
        enrolledUnit.setSemester_enrolled(1);
        assertEquals(1, enrolledUnit.getSemester_enrolled());

        // Test Weekly Hours
        enrolledUnit.setWeekly_hours(15);
        assertEquals(15, enrolledUnit.getWeekly_hours());

        // Test Finalised GPA
        enrolledUnit.setFinalised_gpa(8.0);
        assertEquals(8.0, enrolledUnit.getFinalised_gpa());
    }

    @Test
    public void testLargeValues() {
        // Test maximum integer values
        enrolledUnit.setEnrollment_ID(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrolledUnit.getEnrollment_ID());

        enrolledUnit.setYear_enrolled(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrolledUnit.getYear_enrolled());

        enrolledUnit.setSemester_enrolled(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrolledUnit.getSemester_enrolled());

        enrolledUnit.setWeekly_hours(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrolledUnit.getWeekly_hours());

        // Test maximum double value
        enrolledUnit.setFinalised_gpa(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, enrolledUnit.getFinalised_gpa());
    }

    @Test
    public void testValidSemesters() {
        // Test valid semester values (1 and 2)
        enrolledUnit.setSemester_enrolled(1);
        assertEquals(1, enrolledUnit.getSemester_enrolled());

        enrolledUnit.setSemester_enrolled(2);
        assertEquals(2, enrolledUnit.getSemester_enrolled());
    }

    @Test
    public void testTypicalWeeklyHours() {
        // Test typical weekly hour values
        enrolledUnit.setWeekly_hours(10);
        assertEquals(10, enrolledUnit.getWeekly_hours());

        enrolledUnit.setWeekly_hours(15);
        assertEquals(15, enrolledUnit.getWeekly_hours());

        enrolledUnit.setWeekly_hours(20);
        assertEquals(20, enrolledUnit.getWeekly_hours());
    }

    @Test
    public void testTypicalGPAValues() {
        // Test valid GPA range (0.0 to 10.0)
        enrolledUnit.setFinalised_gpa(0.0);
        assertEquals(0.0, enrolledUnit.getFinalised_gpa());

        enrolledUnit.setFinalised_gpa(5.0);
        assertEquals(5.0, enrolledUnit.getFinalised_gpa());

        enrolledUnit.setFinalised_gpa(7.5);
        assertEquals(7.5, enrolledUnit.getFinalised_gpa());

        enrolledUnit.setFinalised_gpa(10.0);
        assertEquals(10.0, enrolledUnit.getFinalised_gpa());
    }

    @Test
    public void testZeroValues() {
        // Test zero values
        enrolledUnit.setEnrollment_ID(0);
        assertEquals(0, enrolledUnit.getEnrollment_ID());

        enrolledUnit.setYear_enrolled(0);
        assertEquals(0, enrolledUnit.getYear_enrolled());

        enrolledUnit.setSemester_enrolled(0);
        assertEquals(0, enrolledUnit.getSemester_enrolled());

        enrolledUnit.setWeekly_hours(0);
        assertEquals(0, enrolledUnit.getWeekly_hours());

        enrolledUnit.setFinalised_gpa(0.0);
        assertEquals(0.0, enrolledUnit.getFinalised_gpa());
    }

    @Test
    public void testGPAWithDecimalPrecision() {
        // Test GPA with various decimal precisions
        enrolledUnit.setFinalised_gpa(7.123456789);
        assertEquals(7.123456789, enrolledUnit.getFinalised_gpa());

        enrolledUnit.setFinalised_gpa(4.5);
        assertEquals(4.5, enrolledUnit.getFinalised_gpa());

        enrolledUnit.setFinalised_gpa(9.99);
        assertEquals(9.99, enrolledUnit.getFinalised_gpa());
    }
} 