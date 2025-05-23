package org.example.grade_predictor.service;

import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrollmentDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;

import java.util.List;

public class AuthenticateService {
    private final SqliteUserDAO userDAO;
    private final SqliteEnrollmentDAO enrollmentDAO;
    private final SqliteEnrolledUnitDAO enrolledUnitDAO;
    private User currentUser;
    
    private static AuthenticateService instance;
    
    /**
     * Private constructor prevent direct instantiation
     */
    private AuthenticateService() {
        this.userDAO = new SqliteUserDAO();
        this.enrollmentDAO = new SqliteEnrollmentDAO();
        this.enrolledUnitDAO = new SqliteEnrolledUnitDAO();
    }
    
    protected AuthenticateService(boolean forTesting) {
        if (!forTesting) {
            throw new IllegalArgumentException("This constructor is for testing only");
        }
        this.userDAO = new SqliteUserDAO();
        this.enrollmentDAO = new SqliteEnrollmentDAO();
        this.enrolledUnitDAO = new SqliteEnrolledUnitDAO();
    }
    
    /**
     * Gets the singleton instance of the authentication service
     * @return The singleton instance
     */
    public static synchronized AuthenticateService getInstance() {
        if (instance == null) {
            instance = new AuthenticateService();
        }
        return instance;
    }
    
    /**
     * Resets the singleton instance (for testing purposes only)
     */
    public static synchronized void resetInstance() {
        instance = null;
    }

    /**
     * Registers a new user with the provided information
     * 
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param phone User's phone number
     * @param password User's password
     * @param degreeId Optional degree ID for initial enrollment???
     * @return The newly created user
     */
    public User registerUser(String firstName, String lastName, String email, String phone, String password, String degreeId) {
        User newUser = new User(firstName, lastName, email, phone, password);
        userDAO.addUser(newUser);
        
        if (degreeId != null) {
            Enrollment enrollment = new Enrollment(0, newUser.getUser_ID(), degreeId, 0, 0);
            enrollmentDAO.addEnrollment(enrollment);
            
            newUser.addEnrollment(enrollment);
        }
        
        this.currentUser = newUser;
        return newUser;
    }
    
    /**
     * Authenticates a user with the provided credentials
     * 
     * @param email User's email
     * @param password User's password
     * @return true if authentication succeeded, false otherwise
     */
    public boolean loginUser(String email, String password) {
        User user = userDAO.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            this.currentUser = user; // Fix: Ensure currentUser is stored

            List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsForUser(user.getUser_ID());
            user.setEnrollments(enrollments);

            for (Enrollment enrollment : enrollments) {
                List<EnrolledUnit> enrolledUnits = enrolledUnitDAO.getEnrolledUnitsForEnrollment(enrollment.getEnrollment_ID());
                enrollment.setEnrolledUnits(enrolledUnits);
            }

            return true;
        }

        return false;
    }

    
    /**
     * Gets the current authenticated user
     * 
     * @return The current user or null if no user is authenticated
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    /**
     * Gets all users in the system
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    /**
     * Logs out the current user
     */
    public void logoutUser() {
        this.currentUser = null;
    }

    /**
     * Refreshes the current user's data completely from the database
     * This includes enrollments and enrolled units
     */
    public void refreshCurrentUser() {
        if (currentUser == null) {
            return;
        }
        
        List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsForUser(currentUser.getUser_ID());
        currentUser.setEnrollments(enrollments);
        
        for (Enrollment enrollment : enrollments) {
            List<EnrolledUnit> enrolledUnits = enrolledUnitDAO.getEnrolledUnitsForEnrollment(enrollment.getEnrollment_ID());
            enrollment.setEnrolledUnits(enrolledUnits);
        }
    }
} 