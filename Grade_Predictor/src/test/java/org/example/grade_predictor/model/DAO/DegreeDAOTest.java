package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Degree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DegreeDAOTest {
    
    private DegreeDAO degreeDAO;
    private Degree testDegree1;
    private Degree testDegree2;
    
    @BeforeEach
    void setUp() {
        // Initialize the DAO
        degreeDAO = new DegreeDAO();
        DegreeDAO.degrees.clear();
        
        // Degrees for test
        testDegree1 = new Degree("Bachelor of Information Technology", "IN01");
        testDegree2 = new Degree("Diploma in Information Technology", "IT10");
    }
    
    @Test
    void testAddDegree() {
        // Add degree and verify it
        degreeDAO.addDegree(testDegree1);
        assertEquals(1, DegreeDAO.degrees.size());
        assertTrue(DegreeDAO.degrees.contains(testDegree1));
        
        // Add another degree
        degreeDAO.addDegree(testDegree2);
        assertEquals(2, DegreeDAO.degrees.size());
        assertTrue(DegreeDAO.degrees.contains(testDegree1));
        assertTrue(DegreeDAO.degrees.contains(testDegree2));
    }
    
    @Test
    void testAddDegreeWithInvalidCode() {
        // Create degree with invalid code format
        Degree invalidDegree = new Degree("Invalid Degree", "123");
        
        // Is the wanted exception thrown?
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            degreeDAO.addDegree(invalidDegree);
        });
        
        // Check exception message
        String expectedMessage = "Invalid unit code format";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    void testUpdateDegree() {
        // Add degree first
        degreeDAO.addDegree(testDegree1);
        
        // Create an updated version
        Degree updatedDegree = new Degree("Updated IT Degree", testDegree1.getDegree_ID());
        
        // Update the degree
        degreeDAO.updateDegree(updatedDegree);
        
        // Get the degree and check if it was updated
        Degree retrievedDegree = degreeDAO.getDegree(testDegree1.getDegree_ID());
        assertEquals("Updated IT Degree", retrievedDegree.getDegree_Name());
    }
    
    @Test
    void testDeleteDegree() {
        // Add two degrees
        degreeDAO.addDegree(testDegree1);
        degreeDAO.addDegree(testDegree2);
        assertEquals(2, DegreeDAO.degrees.size());
        
        // Delete one degree
        degreeDAO.deleteDegree(testDegree1);
        
        // Check if only one degree remains and its the correct one
        assertEquals(1, DegreeDAO.degrees.size());
        assertEquals(testDegree2, DegreeDAO.degrees.get(0));
    }
    
    @Test
    void testGetDegree() {
        // Add degree
        degreeDAO.addDegree(testDegree1);
        
        // Get the degree by ID
        Degree retrievedDegree = degreeDAO.getDegree(testDegree1.getDegree_ID());
        
        // Check if the retrieved degree matches the original
        assertEquals(testDegree1.getDegree_ID(), retrievedDegree.getDegree_ID());
        assertEquals(testDegree1.getDegree_Name(), retrievedDegree.getDegree_Name());
    }
    
    @Test
    void testGetDegreeNotFound() {
        // Try to get a non existent degree
        Degree retrievedDegree = degreeDAO.getDegree("XX99");
        
        // Check if null is returned
        assertNull(retrievedDegree);
    }
} 