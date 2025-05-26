package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;

import java.sql.Connection;
import java.sql.Statement;

public class EnrollmentTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "enrollments";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS enrollments (" +
                    "enrollment_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_ID INTEGER NOT NULL," +
                    "degree_ID CHAR(4) NOT NULL," +
                    "current_gpa INTEGER," +
                    "predicted_gpa INTEGER," +
                    "FOREIGN KEY(user_ID) REFERENCES users(id)" +
                    ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
    }
} 