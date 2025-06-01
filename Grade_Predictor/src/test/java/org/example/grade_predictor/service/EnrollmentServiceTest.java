package org.example.grade_predictor.service;

import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrollmentDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import org.example.grade_predictor.service.AuthenticateService;
import org.example.grade_predictor.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private SqliteEnrollmentDAO enrollmentDAO;

    @Mock
    private SqliteEnrolledUnitDAO enrolledUnitDAO;

    @Mock
    private AuthenticateService authenticateService;

    private EnrollmentService enrollmentService;
    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        enrollmentService = new EnrollmentService(authenticateService) {
            {
                try {
                    java.lang.reflect.Field enrollmentDAOField = EnrollmentService.class.getDeclaredField("enrollmentDAO");
                    enrollmentDAOField.setAccessible(true);
                    enrollmentDAOField.set(this, enrollmentDAO);

                    java.lang.reflect.Field enrolledUnitDAOField = EnrollmentService.class.getDeclaredField("enrolledUnitDAO");
                    enrolledUnitDAOField.setAccessible(true);
                    enrolledUnitDAOField.set(this, enrolledUnitDAO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        testUser = new User("Test", "User", "test@example.com", "1234567890", "password");
        testUser.setUser_ID(1);
    }

    @Test
    public void testCreateEnrollment_WithStringDegreeId() {
        String degreeId = "IN01";

        doAnswer(invocation -> {
            Enrollment enrollment = invocation.getArgument(0);
            enrollment.setEnrollment_ID(1);

            System.out.println("Saved enrollment with degree_ID: " + enrollment.getDegree_ID());
            
            return null;
        }).when(enrollmentDAO).addEnrollment(any(Enrollment.class));

        Enrollment result = enrollmentService.createEnrollment(testUser, degreeId, 2024, 1, 2025, 2);

        assertNotNull(result, "Enrollment should not be null");
        verify(enrollmentDAO).addEnrollment(any(Enrollment.class));

        assertNotEquals(0, result.getDegree_ID(), "Degree ID should not be 0");
        System.out.println("Resulting enrollment degree ID: " + result.getDegree_ID());
    }
    
    @Test
    public void testCreateEnrollment_WithNullDegreeId() {
        String degreeId = null;

        Exception exception = assertThrows(Exception.class, () -> {
            enrollmentService.createEnrollment(testUser, degreeId, 2024, 1, 2025, 2);
        });
        
        System.out.println("Exception when null degree ID: " + exception.getMessage());
        verify(enrollmentDAO, never()).addEnrollment(any(Enrollment.class));
    }
} 