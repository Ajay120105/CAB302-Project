package org.example.grade_predictor.model;

public class Student extends User{
    private int student_ID;
    private int degree_ID;
    private int current_gpa;
    private int predicted_gpa;


    public Student(String first_name, String last_name, String email, String phone, String password, int student_ID, int degree_ID, int current_gpa, int predicted_gpa) {
        super(first_name, last_name, email, phone, password);
        this.student_ID = student_ID;
        this.degree_ID = degree_ID;
        this.current_gpa = current_gpa;
        this.predicted_gpa = predicted_gpa;
    }
}
