package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {
    
    private Unit unit;
    
    @BeforeEach
    void setUp() {
        // Initialize test object
        unit = new Unit("CAB302", "Software Development", "Medium");
    }
    
    @Test
    void testGetters() {
        // Check if current data is correct
        assertEquals("CAB302", unit.getUnit_code());
        assertEquals("Software Development", unit.getUnit_name());
        assertEquals("Medium", unit.getDifficulty());
        assertEquals(2, unit.getCREDIT_POINTS());
    }

    @Test
    void testSetters() {
        // Update check
        unit.setUnit_code("CAB201");
        assertEquals("CAB201", unit.getUnit_code());
        
        unit.setUnit_name("Programming Principles");
        assertEquals("Programming Principles", unit.getUnit_name());
        
        unit.setDifficulty("Easy");
        assertEquals("Easy", unit.getDifficulty());
        
        // CREDIT_POINTS is final?
    }
} 