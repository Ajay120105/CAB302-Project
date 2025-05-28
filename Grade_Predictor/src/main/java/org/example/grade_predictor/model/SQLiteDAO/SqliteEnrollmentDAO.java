package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.interfaces.I_Enrollment;
import org.example.grade_predictor.model.Enrollment;
import org.example.grade_predictor.model.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteEnrollmentDAO implements I_Enrollment {

    private final Connection connection;

    public SqliteEnrollmentDAO() {
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addEnrollment(Enrollment enrollment) {
        try {
            String query = "INSERT INTO enrollments (user_ID, degree_ID, current_gpa, predicted_gpa, first_year, first_semester, current_year, current_semester) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, enrollment.getUser_ID());
            preparedStatement.setString(2, enrollment.getDegree_ID());
            preparedStatement.setInt(3, enrollment.getCurrent_gpa());
            preparedStatement.setInt(4, enrollment.getPredicted_gpa());
            preparedStatement.setInt(5, enrollment.getFirstYear());
            preparedStatement.setInt(6, enrollment.getFirstSemester());
            preparedStatement.setInt(7, enrollment.getCurrentYear());
            preparedStatement.setInt(8, enrollment.getCurrentSemester());
            preparedStatement.executeUpdate();
            
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                enrollment.setEnrollment_ID(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) {
        try {
            String query = "UPDATE enrollments SET user_ID = ?, degree_ID = ?, current_gpa = ?, predicted_gpa = ?, first_year = ?, first_semester = ?, current_year = ?, current_semester = ? WHERE enrollment_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, enrollment.getUser_ID());
            preparedStatement.setString(2, enrollment.getDegree_ID());
            preparedStatement.setInt(3, enrollment.getCurrent_gpa());
            preparedStatement.setInt(4, enrollment.getPredicted_gpa());
            preparedStatement.setInt(5, enrollment.getFirstYear());
            preparedStatement.setInt(6, enrollment.getFirstSemester());
            preparedStatement.setInt(7, enrollment.getCurrentYear());
            preparedStatement.setInt(8, enrollment.getCurrentSemester());
            preparedStatement.setInt(5, enrollment.getEnrollment_ID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEnrollment(Enrollment enrollment) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM enrollments WHERE enrollment_ID = ?");
            stmt.setInt(1, enrollment.getEnrollment_ID());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Enrollment getEnrollment(int enrollment_ID) {
        try {
            String query = "SELECT * FROM enrollments WHERE enrollment_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, enrollment_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Enrollment(
                        resultSet.getInt("enrollment_ID"),
                        resultSet.getInt("user_ID"),
                        resultSet.getString("degree_ID"),
                        resultSet.getInt("current_gpa"),
                        resultSet.getInt("predicted_gpa"),
                        resultSet.getInt("first_year"),
                        resultSet.getInt("first_semester"),
                        resultSet.getInt("current_year"),
                        resultSet.getInt("current_semester")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM enrollments";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Enrollment enrollment = new Enrollment(
                        resultSet.getInt("enrollment_ID"),
                        resultSet.getInt("user_ID"),
                        resultSet.getString("degree_ID"),
                        resultSet.getInt("current_gpa"),
                        resultSet.getInt("predicted_gpa"),
                        resultSet.getInt("first_year"),
                        resultSet.getInt("first_semester"),
                        resultSet.getInt("current_year"),
                        resultSet.getInt("current_semester")
                );
                enrollments.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> getEnrollmentsForUser(int user_ID) {
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            String query = "SELECT * FROM enrollments WHERE user_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Enrollment enrollment = new Enrollment(
                        resultSet.getInt("enrollment_ID"),
                        resultSet.getInt("user_ID"),
                        resultSet.getString("degree_ID"),
                        resultSet.getInt("current_gpa"),
                        resultSet.getInt("predicted_gpa"),
                        resultSet.getInt("first_year"),
                        resultSet.getInt("first_semester"),
                        resultSet.getInt("current_year"),
                        resultSet.getInt("current_semester")
                );
                enrollments.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollments;
    }
} 