package org.example.grade_predictor.model;

public class EnrolledUnit {
    private int enrollment_ID;
    private String unit_code;
    private int year_enrolled;
    private int semester_enrolled;
    private int weekly_hours;
    private Double finalised_gpa;

    /**
     * Constructor for the EnrolledUnit class
     * @param enrollment_ID The ID of the enrollment
     * @param unit_code The code of the unit
     * @param year_enrolled The year the unit was enrolled in
     * @param semester_enrolled The semester the unit was enrolled in
     * @param weekly_hours The number of hours the unit is worth
     * @param finalised_gpa The GPA of the unit
     */
    public EnrolledUnit(int enrollment_ID, String unit_code, int year_enrolled, int semester_enrolled, int weekly_hours, Double finalised_gpa) {
        this.enrollment_ID = enrollment_ID;
        this.unit_code = unit_code;
        this.year_enrolled = year_enrolled;
        this.semester_enrolled = semester_enrolled;
        this.weekly_hours = weekly_hours;
        this.finalised_gpa = finalised_gpa;
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
     * Get the code of the unit
     * @return The code of the unit
     */
    public String getUnit_code() {
        return unit_code;
    }

    /**
     * Set the code of the unit
     * @param unit_code The code of the unit
     */
    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    /**
     * Get the year the unit was enrolled in
     * @return The year the unit was enrolled in
     */
    public int getYear_enrolled() {
        return year_enrolled;
    }

    /**
     * Set the year the unit was enrolled in
     * @param year_enrolled The year the unit was enrolled in
     */
    public void setYear_enrolled(int year_enrolled) {
        this.year_enrolled = year_enrolled;
    }

    /**
     * Get the semester the unit was enrolled in
     * @return The semester the unit was enrolled in
     */
    public int getSemester_enrolled() {
        return semester_enrolled;
    }

    /**
     * Set the semester the unit was enrolled in
     * @param semester_enrolled The semester the unit was enrolled in
     */
    public void setSemester_enrolled(int semester_enrolled) {
        this.semester_enrolled = semester_enrolled;
    }

    /**
     * Get the number of hours the unit is worth
     * @return The number of hours the unit is worth
     */
    public int getWeekly_hours() {
        return weekly_hours;
    }

    /**
     * Set the number of hours the unit is worth
     * @param weekly_hours The number of hours the unit is worth
     */
    public void setWeekly_hours(int weekly_hours) {
        this.weekly_hours = weekly_hours;
    }

    /**
     * Get the GPA of the unit
     * @return The GPA of the unit
     */
    public Double getFinalised_gpa() {
        return finalised_gpa;
    }

    /**
     * Set the GPA of the unit
     * @param finalised_gpa The GPA of the unit
     */
    public void setFinalised_gpa(Double finalised_gpa) {
        this.finalised_gpa = finalised_gpa;
    }
}

