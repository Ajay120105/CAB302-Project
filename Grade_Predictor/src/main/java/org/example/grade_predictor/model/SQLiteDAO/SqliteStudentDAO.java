package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.interfaces.I_Student;
import org.example.grade_predictor.model.Student;
import org.example.grade_predictor.model.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteStudentDAO implements I_Student {

    private final Connection connection;

    public SqliteStudentDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students (" +
                    "student_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_ID INTEGER NOT NULL," +
                    "degree_ID INTEGER NOT NULL," +
                    "current_gpa INTEGER," +
                    "predicted_gpa INTEGER," +
                    "FOREIGN KEY(user_ID) REFERENCES users(user_ID)" +
                    ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudent(Student student) {
        try {
            PreparedStatement userStmt = connection.prepareStatement(
                    "INSERT INTO users (firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            userStmt.setString(1, student.getFirst_name());
            userStmt.setString(2, student.getLast_name());
            userStmt.setString(3, student.getEmail());
            userStmt.setString(4, student.getPhone());
            userStmt.setString(5, student.getPassword());
            userStmt.executeUpdate();

            ResultSet userKeys = userStmt.getGeneratedKeys();
            if (userKeys.next()) {
                student.setUser_ID(userKeys.getInt(1));
            }

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO students (user_ID, degree_ID, current_gpa, predicted_gpa) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, student.getUser_ID());
            stmt.setInt(2, student.getDegree_ID());
            stmt.setInt(3, student.getCurrent_gpa());
            stmt.setInt(4, student.getPredicted_gpa());
            stmt.executeUpdate();

            ResultSet studentKeys = stmt.getGeneratedKeys();
            if (studentKeys.next()) {
                student.setStudent_ID(studentKeys.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            PreparedStatement userStmt = connection.prepareStatement(
                    "UPDATE users SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE user_ID = ?"
            );
            userStmt.setString(1, student.getFirst_name());
            userStmt.setString(2, student.getLast_name());
            userStmt.setString(3, student.getEmail());
            userStmt.setString(4, student.getPhone());
            userStmt.setString(5, student.getPassword());
            userStmt.setInt(6, student.getUser_ID());
            userStmt.executeUpdate();

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE students SET degree_ID = ?, current_gpa = ?, predicted_gpa = ? WHERE student_ID = ?"
            );
            stmt.setInt(1, student.getDegree_ID());
            stmt.setInt(2, student.getCurrent_gpa());
            stmt.setInt(3, student.getPredicted_gpa());
            stmt.setInt(4, student.getStudent_ID());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(Student student) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM students WHERE student_ID = ?");
            stmt.setInt(1, student.getStudent_ID());
            stmt.executeUpdate();

            PreparedStatement userStmt = connection.prepareStatement("DELETE FROM users WHERE user_ID = ?");
            userStmt.setInt(1, student.getUser_ID());
            userStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudent(int student_ID) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT s.*, u.* FROM students s JOIN users u ON s.user_ID = u.user_ID WHERE s.student_ID = ?"
            );
            stmt.setInt(1, student_ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Student student = new Student(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getInt("student_ID"),
                        rs.getInt("degree_ID"),
                        rs.getInt("current_gpa"),
                        rs.getInt("predicted_gpa")
                );
                student.setUser_ID(rs.getInt("user_ID"));
                return student;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT s.*, u.* FROM students s JOIN users u ON s.user_ID = u.user_ID"
            );
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getInt("student_ID"),
                        rs.getInt("degree_ID"),
                        rs.getInt("current_gpa"),
                        rs.getInt("predicted_gpa")
                );
                student.setUser_ID(rs.getInt("user_ID"));
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


}
