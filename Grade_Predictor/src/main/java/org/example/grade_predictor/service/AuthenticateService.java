package org.example.grade_predictor.service;

import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUserDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrollmentDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import at.favre.lib.crypto.bcrypt.BCrypt;

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

    /**
     * Protected constructor for testing purposes
     * @param forTesting Whether the constructor is for testing
     */
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
     * @param degreeId Degree ID for initial enrollment
     * @param firstYear First year of enrollment
     * @param firstSemester First semester of enrollment
     * @param currentYear Current year of enrollment
     * @param currentSemester Current semester of enrollment
     * @return The newly created user
     */
    public User registerUser(String firstName, String lastName, String email, String phone, String password) {
        if (userDAO.getAllUsers().stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new IllegalArgumentException("User already exists");
        }

        String bcryptHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User newUser = new User(firstName, lastName, email, phone, bcryptHash);
        userDAO.addUser(newUser);
        
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
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            
            if (result.verified) {
                this.currentUser = user;

                List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsForUser(user.getUser_ID());
                user.setEnrollments(enrollments);

                for (Enrollment enrollment : enrollments) {
                    List<EnrolledUnit> enrolledUnits = enrolledUnitDAO.getEnrolledUnitsForEnrollment(enrollment.getEnrollment_ID());
                    enrollment.setEnrolledUnits(enrolledUnits);
                }

                return true;
            }
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
     * Deletes a user from the database
     * 
     * @param user The user to delete
     */
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
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