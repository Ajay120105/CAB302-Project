package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentTest {

    private Enrollment enrollment;

    @BeforeEach
    public void setUp() {
        enrollment = new Enrollment(1, 123, "IN01", 6, 7, 2020, 1, 2024, 2);
    }

    @Test
    public void testEnrollmentConstructor() {
        assertEquals(1, enrollment.getEnrollment_ID());
        assertEquals(123, enrollment.getUser_ID());
        assertEquals("IN01", enrollment.getDegree_ID());
        assertEquals(6, enrollment.getCurrent_gpa());
        assertEquals(7, enrollment.getPredicted_gpa());
        assertEquals(2020, enrollment.getFirstYear());
        assertEquals(1, enrollment.getFirstSemester());
        assertEquals(2024, enrollment.getCurrentYear());
        assertEquals(2, enrollment.getCurrentSemester());
        assertNotNull(enrollment.getEnrolledUnits());
        assertTrue(enrollment.getEnrolledUnits().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        // Test Enrollment ID
        enrollment.setEnrollment_ID(999);
        assertEquals(999, enrollment.getEnrollment_ID());

        // Test User ID
        enrollment.setUser_ID(456);
        assertEquals(456, enrollment.getUser_ID());

        // Test Degree ID
        enrollment.setDegree_ID("CS02");
        assertEquals("CS02", enrollment.getDegree_ID());

        // Test Current GPA
        enrollment.setCurrent_gpa(8);
        assertEquals(8, enrollment.getCurrent_gpa());

        // Test Predicted GPA
        enrollment.setPredicted_gpa(9);
        assertEquals(9, enrollment.getPredicted_gpa());

        // Test First Year
        enrollment.setFirstYear(2021);
        assertEquals(2021, enrollment.getFirstYear());

        // Test First Semester
        enrollment.setFirstSemester(2);
        assertEquals(2, enrollment.getFirstSemester());

        // Test Current Year
        enrollment.setCurrentYear(2025);
        assertEquals(2025, enrollment.getCurrentYear());

        // Test Current Semester
        enrollment.setCurrentSemester(1);
        assertEquals(1, enrollment.getCurrentSemester());
    }

    @Test
    public void testEnrolledUnitsManagement() {
        // Test initial empty list
        assertNotNull(enrollment.getEnrolledUnits());
        assertTrue(enrollment.getEnrolledUnits().isEmpty());

        // Test adding enrolled unit
        EnrolledUnit unit = new EnrolledUnit(1, "CAB302", 2024, 2, 12, 7.0);
        enrollment.addEnrolledUnit(unit);
        
        assertEquals(1, enrollment.getEnrolledUnits().size());
        assertEquals(unit, enrollment.getEnrolledUnits().get(0));

        // Test setting enrolled units list
        List<EnrolledUnit> unitsList = new ArrayList<>();
        unitsList.add(new EnrolledUnit(1, "CAB303", 2024, 2, 10, 6.5));
        unitsList.add(new EnrolledUnit(1, "CAB401", 2024, 2, 8, 8.0));
        
        enrollment.setEnrolledUnits(unitsList);
        assertEquals(2, enrollment.getEnrolledUnits().size());
        assertEquals("CAB303", enrollment.getEnrolledUnits().get(0).getUnit_code());
        assertEquals("CAB401", enrollment.getEnrolledUnits().get(1).getUnit_code());
    }

    @Test
    public void testNullDegreeId() {
        enrollment.setDegree_ID(null);
        assertNull(enrollment.getDegree_ID());
    }

    @Test
    public void testLargeValues() {
        // Test maximum integer values
        enrollment.setEnrollment_ID(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getEnrollment_ID());

        enrollment.setUser_ID(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getUser_ID());

        enrollment.setCurrent_gpa(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getCurrent_gpa());

        enrollment.setPredicted_gpa(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getPredicted_gpa());

        enrollment.setFirstYear(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getFirstYear());

        enrollment.setCurrentYear(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getCurrentYear());

        enrollment.setFirstSemester(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getFirstSemester());

        enrollment.setCurrentSemester(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, enrollment.getCurrentSemester());
    }

    @Test
    public void testValidAcademicYears() {
        // Test typical academic years
        enrollment.setFirstYear(2020);
        enrollment.setCurrentYear(2024);
        assertEquals(2020, enrollment.getFirstYear());
        assertEquals(2024, enrollment.getCurrentYear());

        // Test same year for start and current
        enrollment.setFirstYear(2024);
        enrollment.setCurrentYear(2024);
        assertEquals(2024, enrollment.getFirstYear());
        assertEquals(2024, enrollment.getCurrentYear());
    }

    @Test
    public void testValidSemesters() {
        // Test valid semester values (1 and 2)
        enrollment.setFirstSemester(1);
        enrollment.setCurrentSemester(1);
        assertEquals(1, enrollment.getFirstSemester());
        assertEquals(1, enrollment.getCurrentSemester());

        enrollment.setFirstSemester(2);
        enrollment.setCurrentSemester(2);
        assertEquals(2, enrollment.getFirstSemester());
        assertEquals(2, enrollment.getCurrentSemester());
    }

    @Test
    public void testNullEnrolledUnitsList() {
        enrollment.setEnrolledUnits(null);
        assertNull(enrollment.getEnrolledUnits());
    }
} 