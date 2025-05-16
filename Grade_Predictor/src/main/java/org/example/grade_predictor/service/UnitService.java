package org.example.grade_predictor.service;

import org.example.grade_predictor.model.SQLiteDAO.SqliteUnitDAO;
import org.example.grade_predictor.model.Unit;

import java.util.List;

public class UnitService {
    private final SqliteUnitDAO unitDAO;

    public UnitService() {
        this.unitDAO = new SqliteUnitDAO();
    }

    /**
     * Gets all available units in the system.
     * 
     * @return List of all units
     */
    public List<Unit> getAllUnits() {
        return unitDAO.getAllUnits();
    }
    
    /**
     * Gets a specific unit by its code.
     * 
     * @param unitCode The unit code to look up
     * @return The unit, or null if not found
     */
    public Unit getUnitByCode(String unitCode) {
        return unitDAO.getUnit(unitCode);
    }
    
    /**
     * Adds a new unit to the system.
     * 
     * @param unitCode The unit code
     * @param unitName The unit name
     * @param difficulty The unit difficulty
     * @return The newly created unit, or null if operation failed
     */
    public Unit addUnit(String unitCode, String unitName, String difficulty) {
        Unit unit = new Unit(unitCode, unitName, difficulty);
        unitDAO.addUnit(unit);
        return unit;
    }
    
    /**
     * Updates an existing unit's information.
     * 
     * @param unit The unit to update
     * @return true if update successful, false otherwise
     */
    public boolean updateUnit(Unit unit) {
        if (unit == null) {
            return false;
        }
        unitDAO.updateUnit(unit);
        return true;
    }
    
    /**
     * Deletes a unit from the system.
     * 
     * @param unit The unit to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteUnit(Unit unit) {
        if (unit == null) {
            return false;
        }
        unitDAO.deleteUnit(unit);
        return true;
    }
} 