package org.example.grade_predictor.model.interfaces;

import java.util.List;
import org.example.grade_predictor.model.Enrollment;

public interface I_Enrollment {
    /**
     * Adds a new Enrollment to the database.
     * @param enrollment The Enrollment to add.
     */
    public void addEnrollment(Enrollment enrollment);
    
    /**
     * Updates an existing Enrollment in the database.
     * @param enrollment The Enrollment to update.
     */
    public void updateEnrollment(Enrollment enrollment);
    
    /**
     * Deletes an Enrollment from the database.
     * @param enrollment The Enrollment to delete.
     */
    public void deleteEnrollment(Enrollment enrollment);
    
    /**
     * Retrieves an Enrollment from the database.
     * @param enrollment_ID The id of the Enrollment to retrieve.
     * @return The Enrollment with the given id, or null if not found.
     */
    public Enrollment getEnrollment(int enrollment_ID);
    
    /**
     * Retrieves all Enrollments from the database.
     * @return A list of all Enrollments in the database.
     */
    public List<Enrollment> getAllEnrollments();
    
    /**
     * Retrieves all Enrollments for a specific user.
     * @param user_ID The id of the user to retrieve enrollments for.
     * @return A list of all Enrollments for the user in the database.
     */
    public List<Enrollment> getEnrollmentsForUser(int user_ID);
} 