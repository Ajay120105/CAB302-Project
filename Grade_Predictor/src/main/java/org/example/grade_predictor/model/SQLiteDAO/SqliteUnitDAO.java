package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteUnitDAO implements I_Unit {

    private Connection connection;

    public SqliteUnitDAO() {
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO units (unit_code, unit_name) VALUES (?, ?)");
            statement.setString(1, unit.getUnit_code());
            statement.setString(2, unit.getUnit_name());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE units SET unit_name = ? WHERE unit_code = ?");
            statement.setString(1, unit.getUnit_name());
            statement.setString(2, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM units WHERE unit_code = ?");
            statement.setString(1, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Unit getUnit(String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM units WHERE unit_code = ?");
            statement.setString(1, unit_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String unit_name = resultSet.getString("unit_name");
                return new Unit(unit_code, unit_name); // Pass null for difficulty
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM units");
            while (resultSet.next()) {
                String unit_code = resultSet.getString("unit_code");
                String unit_name = resultSet.getString("unit_name");
                units.add(new Unit(unit_code, unit_name)); // Pass null for difficulty
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return units;
    }

}

