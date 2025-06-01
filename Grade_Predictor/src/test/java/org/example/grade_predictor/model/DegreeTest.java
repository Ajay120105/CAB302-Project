package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DegreeTest {

    private Degree degree;

    @BeforeEach
    public void setUp() {
        degree = new Degree("Bachelor of Information Technology", "IN01");
    }

    @Test
    public void testDegreeConstructor() {
        assertEquals("Bachelor of Information Technology", degree.getDegree_Name());
        assertEquals("IN01", degree.getDegree_ID());
    }

    @Test
    public void testSettersAndGetters() {
        // Test Degree Name
        degree.setDegree_Name("Bachelor of Computer Science");
        assertEquals("Bachelor of Computer Science", degree.getDegree_Name());

        // Test Degree ID
        degree.setDegree_ID("CS01");
        assertEquals("CS01", degree.getDegree_ID());
    }

    @Test
    public void testNullDegreeName() {
        degree.setDegree_Name(null);
        assertNull(degree.getDegree_Name());
    }

    @Test
    public void testNullDegreeId() {
        degree.setDegree_ID(null);
        assertNull(degree.getDegree_ID());
    }

    @Test
    public void testEmptyDegreeName() {
        degree.setDegree_Name("");
        assertEquals("", degree.getDegree_Name());
    }

    @Test
    public void testEmptyDegreeId() {
        degree.setDegree_ID("");
        assertEquals("", degree.getDegree_ID());
    }

    @Test
    public void testTypicalDegreeNames() {
        String[] typicalNames = {
            "Bachelor of Information Technology",
            "Bachelor of Computer Science",
            "Bachelor of Software Engineering",
            "Bachelor of Games and Interactive Environments"
        };
        
        for (String name : typicalNames) {
            degree.setDegree_Name(name);
            assertEquals(name, degree.getDegree_Name());
        }
    }

    @Test
    public void testTypicalDegreeIds() {
        String[] typicalIds = {"IN01", "CS02", "SE03", "IN05"};
        
        for (String id : typicalIds) {
            degree.setDegree_ID(id);
            assertEquals(id, degree.getDegree_ID());
        }
    }

    @Test
    public void testNullConstructorValues() {
        Degree nullDegree = new Degree(null, null);
        assertNull(nullDegree.getDegree_Name());
        assertNull(nullDegree.getDegree_ID());
    }

    @Test
    public void testEmptyConstructorValues() {
        Degree emptyDegree = new Degree("", "");
        assertEquals("", emptyDegree.getDegree_Name());
        assertEquals("", emptyDegree.getDegree_ID());
    }
} 