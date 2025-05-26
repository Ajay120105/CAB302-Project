package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UnitTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "units";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS units (" +
                    "unit_code TEXT PRIMARY KEY," +
                    "unit_name TEXT NOT NULL" +
                    ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
        String[][] dummyUnits = {
                {"IFB102", "Introduction to Computer Systems"},
                {"IFB103", "IT Systems Design"},
                {"IFB104", "Building IT Systems"},
                {"IFB105", "Database Management"},
                {"IFB201", "Introduction to Enterprise Systems"},
                {"IFB240", "Cyber Security"},
                {"IFB398", "Capstone Project (Phase 1)"},
                {"IFB399", "Capstone Project (Phase 2)"},
                {"IFB452", "Blockchain Technology"},
                {"CAB201", "Programming Principles"},
                {"CAB202", "Microprocessors and Digital Systems"},
                {"CAB203", "Discrete Structures"},
                {"CAB222", "Networks"},
                {"CAB301", "Algorithms and Complexity"},
                {"CAB302", "Software Development"},
                {"CAB401", "High Performance and Parallel Computing"},
                {"CAB402", "Programming Paradigms"},
                {"CAB403", "Systems Programming"},
                {"CAB420", "Machine Learning"},
                {"CAB432", "Cloud Computing"}
        };

        String insertQuery = "INSERT OR IGNORE INTO units (unit_code, unit_name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            for (String[] unit : dummyUnits) {
                pstmt.setString(1, unit[0]); // unit_code
                pstmt.setString(2, unit[1]); // unit_name
                pstmt.executeUpdate();
            }
        }
    }
} 