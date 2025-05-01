package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    
    private Student student;
    
    @BeforeEach
    void setUp() {
        // Initialize test student object
        student = new Student("A", "B", "c@example.com", "0401234567", "passworda", 1, 2, 3, 4);
    }
    
    @Test
    void testGetters() {
        // Check if current data is correct
        assertEquals("A", student.getFirst_name());
        assertEquals("B", student.getLast_name());
        assertEquals("c@example.com", student.getEmail());
        assertEquals("0401234567", student.getPhone());
        assertEquals("passworda", student.getPassword());
        assertEquals(1, student.getStudent_ID());
        assertEquals(2, student.getDegree_ID());
        assertEquals(3, student.getCurrent_gpa());
        assertEquals(4, student.getPredicted_gpa());
    }

    @Test
    void testSetters() {
        // Update check
        student.setFirst_name("AB");
        assertEquals("AB", student.getFirst_name());
        
        student.setLast_name("BC");
        assertEquals("BC", student.getLast_name());
        
        student.setEmail("d@example.com");
        assertEquals("d@example.com", student.getEmail());
        
        student.setPhone("0707654321");
        assertEquals("0707654321", student.getPhone());
        
        student.setPassword("passwordb");
        assertEquals("passwordb", student.getPassword());
        
        student.setStudent_ID(2);
        assertEquals(2, student.getStudent_ID());
        
        student.setDegree_ID(3);
        assertEquals(3, student.getDegree_ID());
        
        student.setCurrent_gpa(4);
        assertEquals(4, student.getCurrent_gpa());
        
        student.setPredicted_gpa(5);
        assertEquals(5, student.getPredicted_gpa());
    }
} 