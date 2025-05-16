package org.example.grade_predictor.service;

import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.SQLiteDAO.SqliteDegreeDAO;

import java.util.List;

public class DegreeService {
    private final SqliteDegreeDAO degreeDAO;
    
    /**
     * Default constructor
     */
    public DegreeService() {
        this.degreeDAO = new SqliteDegreeDAO();
    }
    
    /**
     * Gets a degree by its ID
     * 
     * @param degreeId The ID of the degree to get
     * @return The degree with the given ID, or null if not found
     */
    public Degree getDegreeById(String degreeId) {
        return degreeDAO.getDegree(degreeId);
    }
    
    /**
     * Gets all degrees
     * 
     * @return List of all degrees
     */
    public List<Degree> getAllDegrees() {
        return degreeDAO.getAllDegrees();
    }
    
    /**
     * Adds a new degree
     * 
     * @param degreeName The name of the degree
     * @param degreeId The ID of the degree
     * @return The newly created degree
     */
    public Degree addDegree(String degreeName, String degreeId) {
        Degree degree = new Degree(degreeName, degreeId);
        degreeDAO.addDegree(degree);
        return degree;
    }
    
    /**
     * Updates an existing degree
     * 
     * @param degree The degree to update
     * @return true if update was successful, false otherwise
     */
    public boolean updateDegree(Degree degree) {
        try {
            degreeDAO.updateDegree(degree);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 