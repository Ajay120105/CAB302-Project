package org.example.grade_predictor.model.service;

import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrollmentDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import org.example.grade_predictor.service.AuthenticateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticateServiceTest {

    @Mock
    private SqliteUserDAO userDAO;

    @Mock
    private SqliteEnrollmentDAO enrollmentDAO;

    @Mock
    private SqliteEnrolledUnitDAO enrolledUnitDAO;

    private AuthenticateService authenticateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        AuthenticateService.resetInstance();

        authenticateService = new AuthenticateService(true) {
            {
                try {
                    java.lang.reflect.Field userDAOField = AuthenticateService.class.getDeclaredField("userDAO");
                    userDAOField.setAccessible(true);
                    userDAOField.set(this, userDAO);

                    java.lang.reflect.Field enrollmentDAOField = AuthenticateService.class.getDeclaredField("enrollmentDAO");
                    enrollmentDAOField.setAccessible(true);
                    enrollmentDAOField.set(this, enrollmentDAO);

                    java.lang.reflect.Field enrolledUnitDAOField = AuthenticateService.class.getDeclaredField("enrolledUnitDAO");
                    enrolledUnitDAOField.setAccessible(true);
                    enrolledUnitDAOField.set(this, enrolledUnitDAO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testRegisterUser_WithoutDegree() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        String password = "password123";

        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUser_ID(1);
            return null;
        }).when(userDAO).addUser(any(User.class));

        User result = authenticateService.registerUser(firstName, lastName, email, phone, password);

        verify(userDAO).addUser(any(User.class));
        verify(enrollmentDAO, never()).addEnrollment(any(Enrollment.class));
        
        assertEquals(1, result.getUser_ID());
        assertEquals(firstName, result.getFirst_name());
        assertEquals(lastName, result.getLast_name());
        assertEquals(email, result.getEmail());
        assertEquals(phone, result.getPhone());
        
        assertTrue(BCrypt.verifyer().verify(password.toCharArray(), result.getPassword()).verified);
        assertTrue(result.getEnrollments().isEmpty());

        assertEquals(result, authenticateService.getCurrentUser());
    }
    
    @Test
    public void testLoginUser_Success() {
        String email = "test@example.com";
        String password = "correctPassword";
        
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        User mockUser = new User("Test", "User", email, "12345", hashedPassword);
        mockUser.setUser_ID(5);
        
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(mockUser);
        
        List<Enrollment> mockEnrollments = new ArrayList<>();
        Enrollment mockEnrollment = new Enrollment(10, 5, "IN01", 0, 0, 2024, 1, 2025, 2);
        mockEnrollments.add(mockEnrollment);
        
        List<EnrolledUnit> mockEnrolledUnits = new ArrayList<>();
        EnrolledUnit mockEnrolledUnit = new EnrolledUnit(10, "CAB302", 2023, 1, 4);
        mockEnrolledUnits.add(mockEnrolledUnit);

        when(userDAO.getAllUsers()).thenReturn(mockUsers);
        when(enrollmentDAO.getEnrollmentsForUser(5)).thenReturn(mockEnrollments);
        when(enrolledUnitDAO.getEnrolledUnitsForEnrollment(10)).thenReturn(mockEnrolledUnits);

        boolean result = authenticateService.loginUser(email, password);

        assertTrue(result);
        verify(userDAO).getAllUsers();
        verify(enrollmentDAO).getEnrollmentsForUser(5);
        verify(enrolledUnitDAO).getEnrolledUnitsForEnrollment(10);

        User currentUser = authenticateService.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals(5, currentUser.getUser_ID());
        assertEquals(email, currentUser.getEmail());

        assertEquals(1, currentUser.getEnrollments().size());
        assertEquals(10, currentUser.getEnrollments().get(0).getEnrollment_ID());

        assertEquals(1, currentUser.getEnrollments().get(0).getEnrolledUnits().size());
        assertEquals(10, currentUser.getEnrollments().get(0).getEnrolledUnits().get(0).getEnrollment_ID());
        assertEquals("CAB302", currentUser.getEnrollments().get(0).getEnrolledUnits().get(0).getUnit_code());
    }
    
    @Test
    public void testLoginUser_Failure_WrongEmail() {
        String email = "wrong@example.com";
        String password = "correctPassword";
        
        String hashedPassword = BCrypt.withDefaults().hashToString(12, "correctPassword".toCharArray());
        
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("Test", "User", "test@example.com", "12345", hashedPassword));

        when(userDAO.getAllUsers()).thenReturn(mockUsers);

        boolean result = authenticateService.loginUser(email, password);

        assertFalse(result);
        verify(userDAO).getAllUsers();
        verify(enrollmentDAO, never()).getEnrollmentsForUser(anyInt());
        verify(enrolledUnitDAO, never()).getEnrolledUnitsForEnrollment(anyInt());

        assertNull(authenticateService.getCurrentUser());
    }
    
    @Test
    public void testLoginUser_Failure_WrongPassword() {
        String email = "test@example.com";
        String password = "wrongPassword";
        
        String hashedPassword = BCrypt.withDefaults().hashToString(12, "correctPassword".toCharArray());
        
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("Test", "User", email, "12345", hashedPassword));

        when(userDAO.getAllUsers()).thenReturn(mockUsers);

        boolean result = authenticateService.loginUser(email, password);

        assertFalse(result);
        verify(userDAO).getAllUsers();
        verify(enrollmentDAO, never()).getEnrollmentsForUser(anyInt());
        verify(enrolledUnitDAO, never()).getEnrolledUnitsForEnrollment(anyInt());

        assertNull(authenticateService.getCurrentUser());
    }
    
    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("User1", "Last1", "user1@example.com", "123", "pass1"));
        mockUsers.add(new User("User2", "Last2", "user2@example.com", "456", "pass2"));

        when(userDAO.getAllUsers()).thenReturn(mockUsers);

        List<User> result = authenticateService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDAO).getAllUsers();
    }
    
    @Test
    public void testLogoutUser() {
        String email = "test@example.com";
        String password = "password";
        
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User mockUser = new User("Test", "User", email, "12345", hashedPassword);
        mockUser.setUser_ID(1);
        
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(mockUser);
        
        when(userDAO.getAllUsers()).thenReturn(mockUsers);
        when(enrollmentDAO.getEnrollmentsForUser(anyInt())).thenReturn(new ArrayList<>());
        
        authenticateService.loginUser(email, password);
        assertNotNull(authenticateService.getCurrentUser());

        authenticateService.logoutUser();

        assertNull(authenticateService.getCurrentUser());
    }
    
    @Test
    public void testRefreshCurrentUser_NoCurrentUser() {
        try {
            java.lang.reflect.Field currentUserField = AuthenticateService.class.getDeclaredField("currentUser");
            currentUserField.setAccessible(true);
            currentUserField.set(authenticateService, null);
        } catch (Exception e) {
            fail("Failed to set current user via reflection: " + e.getMessage());
        }

        authenticateService.refreshCurrentUser();

        verify(enrollmentDAO, never()).getEnrollmentsForUser(anyInt());
        verify(enrolledUnitDAO, never()).getEnrolledUnitsForEnrollment(anyInt());
    }
} 