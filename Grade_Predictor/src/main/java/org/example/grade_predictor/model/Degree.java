package org.example.grade_predictor.model;

public class Degree {
    private String degree_Name;
    private String degree_ID;

    /**
     * Constructor for the Degree class
     * @param degree_Name The name of the degree
     * @param degree_ID The ID of the degree
     */
    public Degree(String degree_Name, String degree_ID) {
        this.degree_Name = degree_Name;
        this.degree_ID = degree_ID;
    }

    /**
     * Get the name of the degree
     * @return The name of the degree
     */
    public String getDegree_Name() {
        return degree_Name;
    }

    /**
     * Set the name of the degree
     * @param degree_Name The name of the degree
     */
    public void setDegree_Name(String degree_Name) {
        this.degree_Name = degree_Name;
    }

    /**
     * Get the ID of the degree
     * @return The ID of the degree
     */
    public String getDegree_ID() {
        return degree_ID;
    }

    /**
     * Set the ID of the degree
     * @param degree_ID The ID of the degree
     */
    public void setDegree_ID(String degree_ID) {
        this.degree_ID = degree_ID;
    }
}
