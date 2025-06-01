package org.example.grade_predictor.service;

import org.example.grade_predictor.model.SQLiteDAO.SqliteUnitDAO;
import org.example.grade_predictor.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

    @Mock
    private SqliteUnitDAO unitDAO;

    private UnitService unitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        unitService = new UnitService() {
            {
                try {
                    java.lang.reflect.Field unitDAOField = UnitService.class.getDeclaredField("unitDAO");
                    unitDAOField.setAccessible(true);
                    unitDAOField.set(this, unitDAO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testGetAllUnits() {
        List<Unit> mockUnits = new ArrayList<>();
        mockUnits.add(new Unit("CAB302", "Software Development"));
        mockUnits.add(new Unit("CAB222", "Networks"));
        
        when(unitDAO.getAllUnits()).thenReturn(mockUnits);
        
        List<Unit> results = unitService.getAllUnits();
        
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("CAB302", results.get(0).getUnit_code());
        assertEquals("CAB222", results.get(1).getUnit_code());
        verify(unitDAO).getAllUnits();
    }
    
    @Test
    public void testGetUnitByCode_Success() {
        String unitCode = "CAB302";
        Unit mockUnit = new Unit(unitCode, "Software Development");
        
        when(unitDAO.getUnit(unitCode)).thenReturn(mockUnit);
        
        Unit result = unitService.getUnitByCode(unitCode);
        
        assertNotNull(result);
        assertEquals(unitCode, result.getUnit_code());
        assertEquals("Software Development", result.getUnit_name());
        verify(unitDAO).getUnit(unitCode);
    }
    
    @Test
    public void testGetUnitByCode_NotFound() {
        String unitCode = "XXX999";
        
        when(unitDAO.getUnit(unitCode)).thenReturn(null);
        
        Unit result = unitService.getUnitByCode(unitCode);
        
        assertNull(result);
        verify(unitDAO).getUnit(unitCode);
    }
    
    @Test
    public void testAddUnit() {
        String unitCode = "CAB402";
        String unitName = "Programming Paradigms";
        
        doNothing().when(unitDAO).addUnit(any(Unit.class));
        
        Unit result = unitService.addUnit(unitCode, unitName);
        
        assertNotNull(result);
        assertEquals(unitCode, result.getUnit_code());
        assertEquals(unitName, result.getUnit_name());
        verify(unitDAO).addUnit(any(Unit.class));
    }
    
    @Test
    public void testUpdateUnit_Success() {
        Unit unit = new Unit("CAB302", "Updated Software Development");
        
        doNothing().when(unitDAO).updateUnit(any(Unit.class));
        
        boolean result = unitService.updateUnit(unit);
        
        assertTrue(result);
        verify(unitDAO).updateUnit(unit);
    }
    
    @Test
    public void testUpdateUnit_Null() {
        boolean result = unitService.updateUnit(null);
        
        assertFalse(result);
        verify(unitDAO, never()).updateUnit(any(Unit.class));
    }
    
    @Test
    public void testDeleteUnit_Success() {
        Unit unit = new Unit("CAB302", "Software Development");
        
        doNothing().when(unitDAO).deleteUnit(any(Unit.class));
        
        boolean result = unitService.deleteUnit(unit);
        
        assertTrue(result);
        verify(unitDAO).deleteUnit(unit);
    }
    
    @Test
    public void testDeleteUnit_Null() {
        boolean result = unitService.deleteUnit(null);
        
        assertFalse(result);
        verify(unitDAO, never()).deleteUnit(any(Unit.class));
    }
} 