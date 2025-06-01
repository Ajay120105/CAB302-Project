package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitModelTest {

    private Unit unit;

    @BeforeEach
    public void setUp() {
        unit = new Unit("CAB302", "Software Development");
    }

    @Test
    public void testUnitConstructor() {
        assertEquals("CAB302", unit.getUnit_code());
        assertEquals("Software Development", unit.getUnit_name());
    }

    @Test
    public void testSettersAndGetters() {
        // Test Unit Code
        unit.setUnit_code("CAB401");
        assertEquals("CAB401", unit.getUnit_code());

        // Test Unit Name
        unit.setUnit_name("High Performance and Parallel Computing");
        assertEquals("High Performance and Parallel Computing", unit.getUnit_name());
    }

    @Test
    public void testNullUnitCode() {
        unit.setUnit_code(null);
        assertNull(unit.getUnit_code());
    }

    @Test
    public void testNullUnitName() {
        unit.setUnit_name(null);
        assertNull(unit.getUnit_name());
    }

    @Test
    public void testEmptyUnitCode() {
        unit.setUnit_code("");
        assertEquals("", unit.getUnit_code());
    }

    @Test
    public void testEmptyUnitName() {
        unit.setUnit_name("");
        assertEquals("", unit.getUnit_name());
    }

    @Test
    public void testWhitespaceUnitCode() {
        unit.setUnit_code("   ");
        assertEquals("   ", unit.getUnit_code());

        unit.setUnit_code("\t\n");
        assertEquals("\t\n", unit.getUnit_code());
    }

    @Test
    public void testWhitespaceUnitName() {
        unit.setUnit_name("   ");
        assertEquals("   ", unit.getUnit_name());

        unit.setUnit_name("\t\n");
        assertEquals("\t\n", unit.getUnit_name());
    }

    @Test
    public void testLongUnitCode() {
        String longCode = "CAB" + "1".repeat(100);
        unit.setUnit_code(longCode);
        assertEquals(longCode, unit.getUnit_code());
    }

    @Test
    public void testLongUnitName() {
        String longName = "Very Long Unit Name ".repeat(100);
        unit.setUnit_name(longName);
        assertEquals(longName, unit.getUnit_name());
    }

    @Test
    public void testSpecialCharactersInUnitName() {
        unit.setUnit_name("C++ Programming");
        assertEquals("C++ Programming", unit.getUnit_name());

        unit.setUnit_name("Web Development & Design");
        assertEquals("Web Development & Design", unit.getUnit_name());

        unit.setUnit_name("AI/ML Fundamentals");
        assertEquals("AI/ML Fundamentals", unit.getUnit_name());
    }

    @Test
    public void testTypicalUnitCodes() {
        String[] typicalCodes = {"CAB302", "IFB104", "MAB220", "EGH400", "PHB130"};
        
        for (String code : typicalCodes) {
            unit.setUnit_code(code);
            assertEquals(code, unit.getUnit_code());
        }
    }

    @Test
    public void testTypicalUnitNames() {
        String[] typicalNames = {
            "Software Development",
            "Building IT Systems", 
            "Mathematical Foundations",
            "Advanced Engineering Project",
            "Physics for Science"
        };
        
        for (String name : typicalNames) {
            unit.setUnit_name(name);
            assertEquals(name, unit.getUnit_name());
        }
    }

    @Test
    public void testNumericUnitCode() {
        unit.setUnit_code("12345");
        assertEquals("12345", unit.getUnit_code());
    }

    @Test
    public void testNumericUnitName() {
        unit.setUnit_name("101 Programming Basics");
        assertEquals("101 Programming Basics", unit.getUnit_name());
    }

    @Test
    public void testUnitNameWithNumbers() {
        unit.setUnit_name("CAB302: Software Development");
        assertEquals("CAB302: Software Development", unit.getUnit_name());

        unit.setUnit_name("Unit 1 - Introduction");
        assertEquals("Unit 1 - Introduction", unit.getUnit_name());
    }

    @Test
    public void testNullConstructorValues() {
        Unit nullUnit = new Unit(null, null);
        assertNull(nullUnit.getUnit_code());
        assertNull(nullUnit.getUnit_name());
    }

    @Test
    public void testEmptyConstructorValues() {
        Unit emptyUnit = new Unit("", "");
        assertEquals("", emptyUnit.getUnit_code());
        assertEquals("", emptyUnit.getUnit_name());
    }

    @Test
    public void testMixedCaseUnitCode() {
        unit.setUnit_code("cAb302");
        assertEquals("cAb302", unit.getUnit_code());

        unit.setUnit_code("CAb302");
        assertEquals("CAb302", unit.getUnit_code());
    }
} 