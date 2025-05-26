package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;

import java.sql.Connection;
import java.sql.Statement;

public class UserTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "users";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
    }
} 