package org.example.grade_predictor.model;

public class EnrolledUnit {
    private int student_ID;
    private String unit_code;
    private int year_enrolled;
    private int semester_enrolled;
    private int weekly_hours;

    public EnrolledUnit(int student_ID, String unit_code, int year_enrolled, int semester_enrolled, int weekly_hours) {
        this.student_ID = student_ID;
        this.unit_code = unit_code;
        this.year_enrolled = year_enrolled;
        this.semester_enrolled = semester_enrolled;
        this.weekly_hours = weekly_hours;
    }

    public int getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(int student_ID) {
        this.student_ID = student_ID;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public int getYear_enrolled() {
        return year_enrolled;
    }

    public void setYear_enrolled(int year_enrolled) {
        this.year_enrolled = year_enrolled;
    }

    public int getSemester_enrolled() {
        return semester_enrolled;
    }

    public void setSemester_enrolled(int semester_enrolled) {
        this.semester_enrolled = semester_enrolled;
    }

    public int getWeekly_hours() {
        return weekly_hours;
    }

    public void setWeekly_hours(int weekly_hours) {
        this.weekly_hours = weekly_hours;
    }
}

