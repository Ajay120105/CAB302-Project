package org.example.grade_predictor.model;

public class Degree {
    private String degree_Name;
    private int degree_ID;

    public Degree(String degree_Name, int degree_ID) {
        this.degree_Name = degree_Name;
        this.degree_ID = degree_ID;
    }

    public String getDegree_Name() {
        return degree_Name;
    }

    public void setDegree_Name(String degree_Name) {
        this.degree_Name = degree_Name;
    }

    public int getDegree_ID() {
        return degree_ID;
    }

    public void setDegree_ID(int degree_ID) {
        this.degree_ID = degree_ID;
    }
}
