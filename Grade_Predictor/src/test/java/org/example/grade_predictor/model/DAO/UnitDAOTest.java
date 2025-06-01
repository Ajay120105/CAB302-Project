package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Unit;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnitDAOTest {

    private UnitDAO unitDAO;

    @BeforeEach
    void setUp() {
        unitDAO = new UnitDAO(); // Uses the database now
    }

    @Test
    void testGetAllUnits() {
        assertTrue(unitDAO.getAllUnits().size() >= 3, "Database should have at least 3 predefined units.");
    }

    @Test
    void testRetrieveUnit() {
        Unit retrievedUnit = unitDAO.getUnit("CAB302");
        assertNotNull(retrievedUnit, "Unit should exist in the database.");
        assertEquals("CAB302", retrievedUnit.getUnit_code());
        assertEquals("Software Engineering", retrievedUnit.getUnit_name());
    }

    @Test
    void testGetNonExistentUnit() {
        assertNull(unitDAO.getUnit("XYZ999"), "Retrieving a non-existent unit should return null.");
    }
}
