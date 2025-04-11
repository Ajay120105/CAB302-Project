package org.example.grade_predictor.model.interfaces;

import java.util.List;
import org.example.grade_predictor.model.Student;


public interface I_Student {
    /**
     * Adds a new Student to the database.
     * @param student The Student to add.
     */
    public void addStudent(Student student);
    /**
     * Updates an existing Student in the database.
     * @param student The Student to update.
     */
    public void updateStudent(Student student);
    /**
     * Deletes a Student from the database.
     * @param student The Student to delete.
     */
    public void deleteStudent(Student student);
    /**
     * Retrieves a Student from the database.
     * @param student_ID The id of the Student to retrieve.
     * @return The Student with the given id, or null if not found.
     */
    public Student getStudent(int student_ID);
    /**
     * Retrieves all Students from the database.
     *
     * @return A list of all Students in the database.
     */
    public List<Student> getAllStudents();
}
