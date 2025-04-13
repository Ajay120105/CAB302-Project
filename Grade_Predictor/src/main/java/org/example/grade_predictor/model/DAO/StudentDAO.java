package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Student;
import org.example.grade_predictor.model.interfaces.I_Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements I_Student {
    /**
     * A static list of students to be used as a mock database.
     */
    public static final ArrayList<Student> students = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addStudent(Student student) {
        student.setStudent_ID(autoIncrementedId);
        autoIncrementedId++;
        students.add(student);
    }

    @Override
    public void updateStudent(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudent_ID() == student.getStudent_ID()) {
                students.set(i, student);
                break;
            }
        }
    }

    @Override
    public void deleteStudent(Student student) {
        students.remove(student);
    }

    @Override
    public Student getStudent(int student_ID) {
        for (Student student : students) {
            if (student.getStudent_ID() == student_ID) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}
