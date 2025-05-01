package org.example.grade_predictor.model.interfaces;

import org.example.grade_predictor.model.EnrolledUnit;

public interface I_EnrolledUnit {
    /**
     * Adds a new EnrolledUnit to the database.
     * @param EnrolledUnit The EnrolledUnit to add.
     */
    public void addEnrolledUnit(EnrolledUnit EnrolledUnit);
    /**
     * Updates an existing EnrolledUnit in the database.
     * @param EnrolledUnit The EnrolledUnit to update.
     */
    public void updateEnrolledUnit(EnrolledUnit EnrolledUnit);
    /**
     * Deletes a EnrolledUnit from the database.
     * @param student_ID, unit_code The EnrolledUnit to delete.
     */
    public void deleteEnrolledUnit(int student_ID, String unit_code);
    /**
     * Retrieves a EnrolledUnit from the database.
     * @param student_ID, unit_code The id of the degree to retrieve.
     * @return The EnrolledUnit with the given id, or null if not found.
     */
    public EnrolledUnit getEnrolledUnit(int student_ID, String unit_code);


    //Still need to add a return all Enrolled units but I am not sure if that is neccesary
}
