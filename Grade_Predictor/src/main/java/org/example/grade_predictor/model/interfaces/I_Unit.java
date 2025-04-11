package org.example.grade_predictor.model.interfaces;

import java.util.List;
import org.example.grade_predictor.model.Unit;

public interface I_Unit {
    /**
     * Adds a new unit to the database.
     * @param unit The unit to add.
     */
    public void addUnit(Unit unit);
    /**
     * Updates an existing unit in the database.
     * @param unit The unit to update.
     */
    public void updateUnit(Unit unit);
    /**
     * Deletes a unit from the database.
     * @param unit The unit to delete.
     */
    public void deleteUnit(Unit unit);
    /**
     * Retrieves a unit from the database.
     * @param unit_code The id of the unit to retrieve.
     * @return The unit with the given id, or null if not found.
     */
    public Unit getUnit(String unit_code);
    /**
     * Retrieves all units from the database.
     *
     * @return A list of all units in the database.
     */
    public List<Unit> getAllUnits();
}
