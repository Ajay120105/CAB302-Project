package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_EnrolledUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteEnrolledUnitDAO implements I_EnrolledUnit {

    private final Connection connection;

    public SqliteEnrolledUnitDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void deleteTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS enrolled_units";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS enrolled_units (" +
                    "student_ID INTEGER NOT NULL, " +
                    "unit_code TEXT NOT NULL, " +
                    "year_enrolled INTEGER, " +
                    "semester_enrolled INTEGER, " +
                    "weekly_hours INTEGER, " +
                    "PRIMARY KEY (student_ID, unit_code))";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEnrolledUnit(EnrolledUnit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO enrolled_units (student_ID, unit_code, year_enrolled, semester_enrolled, weekly_hours) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, unit.getStudent_ID());
            statement.setString(2, unit.getUnit_code());
            statement.setInt(3, unit.getYear_enrolled());
            statement.setInt(4, unit.getSemester_enrolled());
            statement.setInt(5, unit.getWeekly_hours());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEnrolledUnit(EnrolledUnit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE enrolled_units SET year_enrolled = ?, semester_enrolled = ?, weekly_hours = ? WHERE student_ID = ? AND unit_code = ?");
            statement.setInt(1, unit.getYear_enrolled());
            statement.setInt(2, unit.getSemester_enrolled());
            statement.setInt(3, unit.getWeekly_hours());
            statement.setInt(4, unit.getStudent_ID());
            statement.setString(5, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEnrolledUnit(int student_ID, String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM enrolled_units WHERE student_ID = ? AND unit_code = ?");
            statement.setInt(1, student_ID);
            statement.setString(2, unit_code);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EnrolledUnit getEnrolledUnit(int student_ID, String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM enrolled_units WHERE student_ID = ? AND unit_code = ?");
            statement.setInt(1, student_ID);
            statement.setString(2, unit_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new EnrolledUnit(
                        student_ID,
                        unit_code,
                        resultSet.getInt("year_enrolled"),
                        resultSet.getInt("semester_enrolled"),
                        resultSet.getInt("weekly_hours")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

