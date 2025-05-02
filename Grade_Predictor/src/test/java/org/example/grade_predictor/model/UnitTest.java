package org.example.grade_predictor.model;

import org.example.grade_predictor.model.DAO.UnitDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    private Unit unit;
    private UnitDAO unitDAO;

    @BeforeEach
    void setUp() {
        // Initialize test object & DAO
        unit = new Unit("CAB302", "Software Development", "Medium");
        unitDAO = new UnitDAO(); // Creates DAO with sample units
    }

    @Test
    void testGetters() {
        assertEquals("CAB302", unit.getUnit_code());
        assertEquals("Software Development", unit.getUnit_name());
        assertEquals("Medium", unit.getDifficulty());
        assertEquals(2, unit.getCREDIT_POINTS());
    }

    @Test
    void testSetters() {
        unit.setUnit_code("CAB201");
        assertEquals("CAB201", unit.getUnit_code());

        unit.setUnit_name("Programming Principles");
        assertEquals("Programming Principles", unit.getUnit_name());

        unit.setDifficulty("Easy");
        assertEquals("Easy", unit.getDifficulty());
    }

    @Test
    void testInvalidUnitCodeFormat() {
        // Ensure invalid unit codes trigger exceptions
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                unitDAO.addUnit(new Unit("cab302", "Invalid Unit", "High")) // Lowercase is invalid
        );
        assertEquals("Invalid unit code format. Must be 3 uppercase letters followed by 3 digits (e.g. CAB203).", exception.getMessage());
    }

    @Test
    void testAddAndRetrieveUnit() {
        Unit newUnit = new Unit("CAB305", "Artificial Intelligence", "High");
        unitDAO.addUnit(newUnit);

        Unit retrievedUnit = unitDAO.getUnit("CAB305");
        assertNotNull(retrievedUnit);
        assertEquals("CAB305", retrievedUnit.getUnit_code());
        assertEquals("Artificial Intelligence", retrievedUnit.getUnit_name());
        assertEquals("High", retrievedUnit.getDifficulty());
    }

    @Test
    void testUpdateUnit() {
        Unit updatedUnit = new Unit("CAB302", "Advanced Software Development", "Hard");
        unitDAO.updateUnit(updatedUnit);

        Unit retrievedUnit = unitDAO.getUnit("CAB302");
        assertNotNull(retrievedUnit);
        assertEquals("Advanced Software Development", retrievedUnit.getUnit_name());
        assertEquals("Hard", retrievedUnit.getDifficulty());
    }

    @Test
    void testDeleteUnit() {
        unitDAO.deleteUnit(unit);

        Unit retrievedUnit = unitDAO.getUnit("CAB302");
        assertNull(retrievedUnit, "Unit should have been deleted but still exists.");
    }

    @Test
    void testGetAllUnits() {
        assertEquals(3, unitDAO.getAllUnits().size()); // DAO should already contain sample units
        unitDAO.addUnit(new Unit("CAB306", "Machine Learning", "High"));
        assertEquals(4, unitDAO.getAllUnits().size()); // List should now contain 4 units
    }

    @Test
    void testGetNonExistentUnit() {
        assertNull(unitDAO.getUnit("XYZ999"), "Retrieving a non-existent unit should return null.");
    }
}
