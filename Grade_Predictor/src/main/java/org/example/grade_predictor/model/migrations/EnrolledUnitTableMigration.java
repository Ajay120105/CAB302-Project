package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;

import java.sql.Connection;
import java.sql.Statement;

public class EnrolledUnitTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "enrolled_units";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS enrolled_units (" +
                    "enrollment_ID INTEGER NOT NULL, " +
                    "unit_code TEXT NOT NULL, " +
                    "year_enrolled INTEGER, " +
                    "semester_enrolled INTEGER, " +
                    "weekly_hours INTEGER, " +
                    "PRIMARY KEY (enrollment_ID, unit_code)," +
                    "FOREIGN KEY(enrollment_ID) REFERENCES enrollments(enrollment_ID)," +
                    "FOREIGN KEY(unit_code) REFERENCES units(unit_code)" +
                    ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
    }
} 