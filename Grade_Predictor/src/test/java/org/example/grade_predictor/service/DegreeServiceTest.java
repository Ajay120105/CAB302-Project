package org.example.grade_predictor.service;

import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.SQLiteDAO.SqliteDegreeDAO;
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
public class DegreeServiceTest {

    @Mock
    private SqliteDegreeDAO degreeDAO;

    private DegreeService degreeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        degreeService = new DegreeService() {
            {
                try {
                    java.lang.reflect.Field degreeDAOField = DegreeService.class.getDeclaredField("degreeDAO");
                    degreeDAOField.setAccessible(true);
                    degreeDAOField.set(this, degreeDAO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testGetDegreeById_Success() {
        String degreeId = "IN01";
        Degree mockDegree = new Degree("Bachelor of Information Technology", degreeId);
        
        when(degreeDAO.getDegree(degreeId)).thenReturn(mockDegree);
        
        Degree result = degreeService.getDegreeById(degreeId);
        
        assertNotNull(result);
        assertEquals(degreeId, result.getDegree_ID());
        assertEquals("Bachelor of Information Technology", result.getDegree_Name());
        verify(degreeDAO).getDegree(degreeId);
    }
    
    @Test
    public void testGetDegreeById_NotFound() {
        String degreeId = "XX99";
        
        when(degreeDAO.getDegree(degreeId)).thenReturn(null);
        
        Degree result = degreeService.getDegreeById(degreeId);
        
        assertNull(result);
        verify(degreeDAO).getDegree(degreeId);
    }
    
    @Test
    public void testGetAllDegrees() {
        List<Degree> mockDegrees = new ArrayList<>();
        mockDegrees.add(new Degree("Bachelor of Information Technology", "IN01"));
        mockDegrees.add(new Degree("Bachelor of Games and Interactive Environments", "IN05"));
        
        when(degreeDAO.getAllDegrees()).thenReturn(mockDegrees);
        
        List<Degree> results = degreeService.getAllDegrees();
        
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("IN01", results.get(0).getDegree_ID());
        assertEquals("IN05", results.get(1).getDegree_ID());
        verify(degreeDAO).getAllDegrees();
    }
    
    @Test
    public void testAddDegree() {
        String degreeName = "Bachelor of Information Technology (Honours)";
        String degreeId = "IN10";
        
        doNothing().when(degreeDAO).addDegree(any(Degree.class));
        
        Degree result = degreeService.addDegree(degreeName, degreeId);
        
        assertNotNull(result);
        assertEquals(degreeId, result.getDegree_ID());
        assertEquals(degreeName, result.getDegree_Name());
        verify(degreeDAO).addDegree(any(Degree.class));
    }
    
    @Test
    public void testUpdateDegree_Success() {
        Degree degree = new Degree("Updated IT Degree", "IN01");
        
        doNothing().when(degreeDAO).updateDegree(any(Degree.class));
        
        boolean result = degreeService.updateDegree(degree);
        
        assertTrue(result);
        verify(degreeDAO).updateDegree(degree);
    }
} 