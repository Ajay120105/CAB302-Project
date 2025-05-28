package org.example.grade_predictor.service;

import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrolledUnitDAO;
import org.example.grade_predictor.model.SQLiteDAO.SqliteEnrollmentDAO;
import org.example.grade_predictor.model.User;

import java.util.List;

public class EnrollmentService {
    private final SqliteEnrollmentDAO enrollmentDAO;
    private final SqliteEnrolledUnitDAO enrolledUnitDAO;
    private final AuthenticateService authenticateService;

    /**
     * Constructor that accepts an AuthenticateService instance
     * 
     * @param authenticateService The authentication service to use
     */
    public EnrollmentService(AuthenticateService authenticateService) {
        this.enrollmentDAO = new SqliteEnrollmentDAO();
        this.enrolledUnitDAO = new SqliteEnrolledUnitDAO();
        this.authenticateService = authenticateService;
    }
    
    /**
     * Default constructor that uses the singleton instance of AuthenticateService
     */
    public EnrollmentService() {
        this(AuthenticateService.getInstance());
    }

    /**
     * Gets the current user's first enrollment.
     * 
     * @return The first enrollment for the current user
     */
    public Enrollment getCurrentUserFirstEnrollment() {
        List<Enrollment> enrollments = getCurrentUserEnrollments();
        
        // If there are multiple enrollments, we only process the first one for now
        // TODO: Add support for multiple enrollments in the future
        return enrollments != null && !enrollments.isEmpty() ? enrollments.get(0) : null;
    }
    
    /**
     * Gets all enrollments for the current user.
     * 
     * @return List of enrollments for the current user
     */
    public List<Enrollment> getCurrentUserEnrollments() {
        User user = authenticateService.getCurrentUser();
        if (user == null) {
            return null;
        }
        
        return user.getEnrollments();
    }
    
    /**
     * Gets all enrolled units for a specific enrollment.
     * 
     * @param enrollment The enrollment to get units for
     * @return List of enrolled units
     */
    public List<EnrolledUnit> getEnrolledUnits(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        return enrollment.getEnrolledUnits();
    }
    
    /**
     * Adds a new enrolled unit to the system.
     * 
     * @param unitCode The unit code
     * @param year The year enrolled
     * @param semester The semester enrolled
     * @param weeklyHours Weekly hours
     * @return The newly created enrolled unit, or null if operation failed
     */
    public EnrolledUnit addEnrolledUnit(String unitCode, int year, int semester, int weeklyHours) {
        Enrollment enrollment = getCurrentUserFirstEnrollment();
        if (enrollment == null) {
            return null;
        }
        if (enrolledUnitDAO.getEnrolledUnit(enrollment.getEnrollment_ID(), unitCode) != null) {
            return null;
        }
        
        EnrolledUnit newUnit = new EnrolledUnit(
            enrollment.getEnrollment_ID(),
            unitCode,
            year,
            semester,
            weeklyHours
        );
        
        enrolledUnitDAO.addEnrolledUnit(newUnit);
        
        // Update in-memory model
        refreshEnrolledUnitsForCurrentUser();
        
        return newUnit;
    }
    
    /**
     * Updates an existing enrolled unit.
     * 
     * @param originalUnit The original enrolled unit
     * @param newUnitCode The new unit code
     * @param newYear The new year
     * @param newSemester The new semester
     * @param newWeeklyHours The new weekly hours
     * @return true if update successful, false otherwise
     */
    public boolean updateEnrolledUnit(EnrolledUnit originalUnit, String newUnitCode, int newYear, int newSemester, int newWeeklyHours) {
        if (originalUnit == null) {
            return false;
        }
        
        boolean unitCodeChanged = !originalUnit.getUnit_code().equals(newUnitCode);
        
        if (unitCodeChanged) {
            enrolledUnitDAO.deleteEnrolledUnit(originalUnit.getEnrollment_ID(), originalUnit.getUnit_code());
            
            EnrolledUnit newUnit = new EnrolledUnit(
                originalUnit.getEnrollment_ID(),
                newUnitCode,
                newYear,
                newSemester,
                newWeeklyHours
            );
            enrolledUnitDAO.addEnrolledUnit(newUnit);
        } else {
            EnrolledUnit updatedUnit = new EnrolledUnit(
                originalUnit.getEnrollment_ID(),
                newUnitCode,
                newYear,
                newSemester,
                newWeeklyHours
            );
            enrolledUnitDAO.updateEnrolledUnit(updatedUnit);
        }
        
        // Update in-memory model
        refreshEnrolledUnitsForCurrentUser();
        
        return true;
    }
    
    /**
     * Deletes an enrolled unit.
     * 
     * @param enrolledUnit The enrolled unit to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteEnrolledUnit(EnrolledUnit enrolledUnit) {
        if (enrolledUnit == null) {
            return false;
        }
        
        enrolledUnitDAO.deleteEnrolledUnit(enrolledUnit.getEnrollment_ID(), enrolledUnit.getUnit_code());
        
        refreshEnrolledUnitsForCurrentUser();
        
        return true;
    }
    
    /**
     * Creates a new enrollment for a user.
     * 
     * @param user The user to create enrollment for
     * @param degreeId The degree ID as a string (e.g., "IN01")
     * @param firstYear The first year of enrollment
     * @param firstSemester The first semester of enrollment
     * @param currentYear The current year of enrollment
     * @param currentSemester The current semester of enrollment
     * @return The new enrollment, or null if operation failed
     */
    public Enrollment createEnrollment(User user, String degreeId, int firstYear, int firstSemester, int currentYear, int currentSemester) {
        if (user == null || degreeId == null) {
            throw new IllegalArgumentException("User and degree ID cannot be null");
        }
        Enrollment enrollment = new Enrollment(0, user.getUser_ID(), degreeId, 0, 0, firstYear, firstSemester, currentYear, currentSemester);
        enrollmentDAO.addEnrollment(enrollment);
        
        refreshCurrentUserData();
        
        return enrollment;
    }
    
    /**
     * Updates an existing enrollment.
     * 
     * @param enrollment The enrollment to update
     * @return true if update successful, false otherwise
     */
    public boolean updateEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            return false;
        }
        
        enrollmentDAO.updateEnrollment(enrollment);
        
        refreshCurrentUserData();
        
        return true;
    }
    
    /**
     * Refreshes the enrolled units for all enrollments of the current user
     * after adding, updating, or deleting units through the DAO
     */
    public void refreshEnrolledUnitsForCurrentUser() {
        User currentUser = authenticateService.getCurrentUser();
        if (currentUser == null) {
            return;
        }
        
        List<Enrollment> enrollments = currentUser.getEnrollments();
        if (enrollments == null || enrollments.isEmpty()) {
            return;
        }
        
        for (Enrollment enrollment : enrollments) {
            List<EnrolledUnit> enrolledUnits = enrolledUnitDAO.getEnrolledUnitsForEnrollment(enrollment.getEnrollment_ID());
            enrollment.setEnrolledUnits(enrolledUnits);
        }
    }
    
    /**
     * Refreshes the current user's data from the database.
     * Ensure the in-memory model stays in sync with the database.
     */
    public void refreshCurrentUserData() {
        authenticateService.refreshCurrentUser();
    }
} 