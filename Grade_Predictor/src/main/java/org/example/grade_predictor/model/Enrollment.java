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

    /**
     * Constructor for the Enrollment class
     * @param enrollment_ID The ID of the enrollment
     * @param user_ID The ID of the user
     * @param degree_ID The ID of the degree
     * @param current_gpa The current GPA of the user
     * @param predicted_gpa The predicted GPA of the user
     * @param firstYear The first year of the user
     * @param firstSemester The first semester of the user
     * @param currentYear The current year of the user
     * @param currentSemester The current semester of the user
     */
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

    /**
     * Get the ID of the enrollment
     * @return The ID of the enrollment
     */
    public int getEnrollment_ID() {
        return enrollment_ID;
    }

    /**
     * Set the ID of the enrollment
     * @param enrollment_ID The ID of the enrollment
     */
    public void setEnrollment_ID(int enrollment_ID) {
        this.enrollment_ID = enrollment_ID;
    }

    /**
     * Get the ID of the user
     * @return The ID of the user
     */
    public int getUser_ID() {
        return user_ID;
    }

    /**
     * Set the ID of the user
     * @param user_ID The ID of the user
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
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

    /**
     * Get the current GPA of the user
     * @return The current GPA of the user
     */
    public int getCurrent_gpa() {
        return current_gpa;
    }

    /**
     * Set the current GPA of the user
     * @param current_gpa The current GPA of the user
     */
    public void setCurrent_gpa(int current_gpa) {
        this.current_gpa = current_gpa;
    }

    /**
     * Get the predicted GPA of the user
     * @return The predicted GPA of the user
     */
    public int getPredicted_gpa() {
        return predicted_gpa;
    }

    /**
     * Set the predicted GPA of the user
     * @param predicted_gpa The predicted GPA of the user
     */
    public void setPredicted_gpa(int predicted_gpa) {
        this.predicted_gpa = predicted_gpa;
    }

    /**
     * Get the enrolled units of the user
     * @return The enrolled units of the user
     */
    public List<EnrolledUnit> getEnrolledUnits() {
        return enrolledUnits;
    }

    /**
     * Set the enrolled units of the user
     * @param enrolledUnits The enrolled units of the user
     */
    public void setEnrolledUnits(List<EnrolledUnit> enrolledUnits) {
        this.enrolledUnits = enrolledUnits;
    }

    /**
     * Add an enrolled unit to the user
     * @param enrolledUnit The enrolled unit to add
     */
    public void addEnrolledUnit(EnrolledUnit enrolledUnit) {
        this.enrolledUnits.add(enrolledUnit);
    }

    /**
     * Get the first year of the user
     * @return The first year of the user
     */
    public int getFirstYear() {
        return firstYear;
    }

    /**
     * Set the first year of the user
     * @param firstYear The first year of the user
     */
    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    /**
     * Get the first semester of the user
     * @return The first semester of the user
     */
    public int getFirstSemester() {
        return firstSemester;
    }

    /**
     * Set the first semester of the user
     * @param firstSemester The first semester of the user
     */
    public void setFirstSemester(int firstSemester) {
        this.firstSemester = firstSemester;
    }

    /**
     * Get the current year of the user
     * @return The current year of the user
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /**
     * Set the current year of the user
     * @param currentYear The current year of the user
     */
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    /**
     * Get the current semester of the user
     * @return The current semester of the user
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Set the current semester of the user
     * @param currentSemester The current semester of the user
     */
    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
} 