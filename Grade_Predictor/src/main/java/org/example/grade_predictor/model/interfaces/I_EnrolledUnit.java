package org.example.grade_predictor.model.interfaces;

import org.example.grade_predictor.model.EnrolledUnit;
import java.util.List;

public interface I_EnrolledUnit {

    /**
     * Adds a new EnrolledUnit to the database.
     * @param enrolledUnit The EnrolledUnit to add.
     */
    void addEnrolledUnit(EnrolledUnit enrolledUnit);

    /**
     * Updates an existing EnrolledUnit in the database.
     * @param enrolledUnit The EnrolledUnit to update.
     */
    void updateEnrolledUnit(EnrolledUnit enrolledUnit);

    /**
     * Deletes an EnrolledUnit from the database.
     * @param enrollment_ID The enrollment ID.
     * @param unit_code The unit code of the enrollment to delete.
     */
    void deleteEnrolledUnit(int enrollment_ID, String unit_code);

    /**
     * Retrieves a specific EnrolledUnit from the database.
     * @param enrollment_ID The enrollment ID.
     * @param unit_code The unit code of the enrollment.
     * @return The EnrolledUnit with the given identifiers, or null if not found.
     */
    EnrolledUnit getEnrolledUnit(int enrollment_ID, String unit_code);

    /**
     * Retrieves all EnrolledUnit records for the specified enrollment.
     * @param enrollment_ID The enrollment ID.
     * @return A list of EnrolledUnit records for the given enrollment.
     */
    List<EnrolledUnit> getEnrolledUnitsForEnrollment(int enrollment_ID);

    /**
     * Retrieves all EnrolledUnit records from the database.
     * @return A list of all EnrolledUnit records.
     */
    List<EnrolledUnit> getAllEnrolledUnits();
}
