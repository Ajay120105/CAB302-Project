package org.example.grade_predictor.model;

import java.util.ArrayList;
import java.util.List;

public class Enrollment {
    private int enrollment_ID;
    private int user_ID;
    private String degree_ID;
    private int current_gpa;
    private int predicted_gpa;
    private List<EnrolledUnit> enrolledUnits;
    private int firstYear;
    private int firstSemester;
    private int currentYear;
    private int currentSemester;

    public Enrollment(int enrollment_ID, int user_ID, String degree_ID, int current_gpa, int predicted_gpa, int firstYear, int firstSemester, int currentYear, int currentSemester) {
        this.enrollment_ID = enrollment_ID;
        this.user_ID = user_ID;
        this.degree_ID = degree_ID;
        this.current_gpa = current_gpa;
        this.predicted_gpa = predicted_gpa;
        this.enrolledUnits = new ArrayList<>();
        this.firstYear = firstYear;
        this.firstSemester = firstSemester;
        this.currentYear = currentYear;
        this.currentSemester = currentSemester;
    }

    public int getEnrollment_ID() {
        return enrollment_ID;
    }

    public void setEnrollment_ID(int enrollment_ID) {
        this.enrollment_ID = enrollment_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getDegree_ID() {
        return degree_ID;
    }

    public void setDegree_ID(String degree_ID) {
        this.degree_ID = degree_ID;
    }

    public int getCurrent_gpa() {
        return current_gpa;
    }

    public void setCurrent_gpa(int current_gpa) {
        this.current_gpa = current_gpa;
    }

    public int getPredicted_gpa() {
        return predicted_gpa;
    }

    public void setPredicted_gpa(int predicted_gpa) {
        this.predicted_gpa = predicted_gpa;
    }
    
    public List<EnrolledUnit> getEnrolledUnits() {
        return enrolledUnits;
    }
    
    public void setEnrolledUnits(List<EnrolledUnit> enrolledUnits) {
        this.enrolledUnits = enrolledUnits;
    }
    
    public void addEnrolledUnit(EnrolledUnit enrolledUnit) {
        this.enrolledUnits.add(enrolledUnit);
    }

    public int getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    public int getFirstSemester() {
        return firstSemester;
    }

    public void setFirstSemester(int firstSemester) {
        this.firstSemester = firstSemester;
    }
    
    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
    
    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
} 