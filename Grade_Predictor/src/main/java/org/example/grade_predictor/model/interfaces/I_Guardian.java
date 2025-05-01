package org.example.grade_predictor.model.interfaces;

import java.util.List;
import org.example.grade_predictor.model.Guardian;

public interface I_Guardian {
    /**
     * Adds a new guardian to the database.
     * @param guardian The guardian to add.
     */
    public void addGuardian(Guardian guardian);
    /**
     * Updates an existing guardian in the database.
     * @param guardian The guardian to update.
     */
    public void updateGuardian(Guardian guardian);
    /**
     * Deletes a guardian from the database.
     * @param guardian The guardian to delete.
     */
    public void deleteGuardian(Guardian guardian);
    /**
     * Retrieves a guardian from the database.
     * @param guardian_ID The id of the guardian to retrieve.
     * @return The guardian with the given id, or null if not found.
     */
    public Guardian getGuardian(int guardian_ID);
    /**
     * Retrieves all guardians from the database.
     *
     * @return A list of all guardians in the database.
     */
    public List<Guardian> getAllGuardians();
}
