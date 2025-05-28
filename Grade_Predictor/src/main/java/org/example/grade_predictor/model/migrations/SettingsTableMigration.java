package org.example.grade_predictor.model.migrations;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;
import org.example.grade_predictor.util.OllamaConnectionChecker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SettingsTableMigration implements IDatabaseMigration {
    
    @Override
    public String getTableName() {
        return "settings";
    }
    
    @Override
    public void createTable(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS settings ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "key VARCHAR NOT NULL UNIQUE,"
                    + "value VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        }
    }
    
    @Override
    public void populateInitialData(Connection connection) throws Exception {
        // Insert default Ollama configuration
        String insertQuery = "INSERT OR IGNORE INTO settings (key, value) VALUES (?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            // Insert ollama_host
            statement.setString(1, "ollama_host");
            statement.setString(2, "http://localhost:11434");
            statement.executeUpdate();
            
            // Get first available model from Ollama server using centralized checker
            String firstAvailableModel = OllamaConnectionChecker.getFirstAvailableModel();
            
            // Insert ollama_model
            statement.setString(1, "ollama_model");
            statement.setString(2, firstAvailableModel);
            statement.executeUpdate();
            
            if (firstAvailableModel.isEmpty()) {
                System.out.println("Warning: No models found on Ollama server during initialization. Model setting set to empty.");
            } else {
                System.out.println("Initialized with first available model: " + firstAvailableModel);
            }
        }
    }
} 