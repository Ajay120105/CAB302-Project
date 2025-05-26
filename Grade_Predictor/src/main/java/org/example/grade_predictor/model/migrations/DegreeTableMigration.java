package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DegreeTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "degrees";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS degrees (" +
                    "id VARCHAR PRIMARY KEY," +
                    "degreeName VARCHAR NOT NULL" +
                    ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
        String insertQuery = "INSERT OR IGNORE INTO degrees (id, degreeName) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            String[][] degrees = {
                    {"IN01", "Bachelor of Information Technology"},
                    {"IN05", "Bachelor of Games and Interactive Environments"},
                    {"IN10", "Bachelor of Information Technology (Honours)"},
                    {"IN14", "Graduate Certificate in Business Analysis"},
                    {"IN15", "Graduate Certificate in Computer Science"},
                    {"IN16", "Graduate Certificate in Cyber Security and Networks"},
                    {"IN17", "Graduate Certificate in Communication for Information Technology"},
                    {"IN18", "Graduate Certificate in Information Technology"},
                    {"IN19", "Graduate Diploma in Information Technology"}
            };

            for (String[] degree : degrees) {
                pstmt.setString(1, degree[0]); // id
                pstmt.setString(2, degree[1]); // degreeName
                pstmt.executeUpdate();
            }
        }
    }
} 