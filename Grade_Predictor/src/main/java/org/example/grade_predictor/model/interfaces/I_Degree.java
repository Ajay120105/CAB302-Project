package org.example.grade_predictor.model.interfaces;

import org.example.grade_predictor.model.Degree;

import java.util.List;
public interface I_Degree {
    /**
     * Adds a new degree to the database.
     * @param degree The degree to add.
     */
    public void addDegree(Degree degree);
    /**
     * Updates an existing degree in the database.
     * @param degree The degree to update.
     */
    public void updateDegree(Degree degree);
    /**
     * Deletes a degree from the database.
     * @param degree The degree to delete.
     */
    public void deleteDegree(Degree degree);
    /**
     * Retrieves a degree from the database.
     * @param degree_ID The id of the degree to retrieve.
     * @return The degree with the given id, or null if not found.
     */
    public Degree getDegree(int degree_ID);
    /**
     * Retrieves all degrees from the database.
     *
     * @return A list of all degrees in the database.
     */
    public List<Degree> getAllDegrees();

}
