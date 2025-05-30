package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_EnrolledUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqliteEnrolledUnitDAO implements I_EnrolledUnit {

    private final Connection connection;

    public SqliteEnrolledUnitDAO() {
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addEnrolledUnit(EnrolledUnit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO enrolled_units (enrollment_ID, unit_code, year_enrolled, semester_enrolled, weekly_hours, finalised_gpa) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, unit.getEnrollment_ID());
            statement.setString(2, unit.getUnit_code());
            statement.setInt(3, unit.getYear_enrolled());
            statement.setInt(4, unit.getSemester_enrolled());
            statement.setInt(5, unit.getWeekly_hours());
            if (unit.getFinalised_gpa() != null) {
                statement.setDouble(6, unit.getFinalised_gpa());
            } else {
                statement.setNull(6, java.sql.Types.REAL);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEnrolledUnit(EnrolledUnit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE enrolled_units SET year_enrolled = ?, semester_enrolled = ?, weekly_hours = ?, finalised_gpa = ? WHERE enrollment_ID = ? AND unit_code = ?");
            statement.setInt(1, unit.getYear_enrolled());
            statement.setInt(2, unit.getSemester_enrolled());
            statement.setInt(3, unit.getWeekly_hours());
            if (unit.getFinalised_gpa() != null) {
                statement.setDouble(4, unit.getFinalised_gpa());
            } else {
                statement.setNull(4, java.sql.Types.REAL);
            }
            statement.setInt(5, unit.getEnrollment_ID());
            statement.setString(6, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEnrolledUnit(int enrollment_ID, String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM enrolled_units WHERE enrollment_ID = ? AND unit_code = ?");
            statement.setInt(1, enrollment_ID);
            statement.setString(2, unit_code);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EnrolledUnit getEnrolledUnit(int enrollment_ID, String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM enrolled_units WHERE enrollment_ID = ? AND unit_code = ?");
            statement.setInt(1, enrollment_ID);
            statement.setString(2, unit_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Double finalisedGpa = resultSet.getObject("finalised_gpa") != null ? 
                    resultSet.getDouble("finalised_gpa") : null;
                return new EnrolledUnit(
                        enrollment_ID,
                        unit_code,
                        resultSet.getInt("year_enrolled"),
                        resultSet.getInt("semester_enrolled"),
                        resultSet.getInt("weekly_hours"),
                        finalisedGpa
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EnrolledUnit> getEnrolledUnitsForEnrollment(int enrollment_ID) {
        List<EnrolledUnit> enrolledUnitsList = new ArrayList<>();
        String query = "SELECT * FROM enrolled_units WHERE enrollment_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, enrollment_ID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Double finalisedGpa = rs.getObject("finalised_gpa") != null ? 
                        rs.getDouble("finalised_gpa") : null;
                    EnrolledUnit unit = new EnrolledUnit(
                            rs.getInt("enrollment_ID"),
                            rs.getString("unit_code"),
                            rs.getInt("year_enrolled"),
                            rs.getInt("semester_enrolled"),
                            rs.getInt("weekly_hours"),
                            finalisedGpa
                    );
                    enrolledUnitsList.add(unit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrolledUnitsList;
    }

    @Override
    public List<EnrolledUnit> getAllEnrolledUnits() {
        List<EnrolledUnit> allUnits = new ArrayList<>();
        String query = "SELECT * FROM enrolled_units";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Double finalisedGpa = rs.getObject("finalised_gpa") != null ? 
                    rs.getDouble("finalised_gpa") : null;
                EnrolledUnit unit = new EnrolledUnit(
                        rs.getInt("enrollment_ID"),
                        rs.getString("unit_code"),
                        rs.getInt("year_enrolled"),
                        rs.getInt("semester_enrolled"),
                        rs.getInt("weekly_hours"),
                        finalisedGpa
                );
                allUnits.add(unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUnits;
    }
}
