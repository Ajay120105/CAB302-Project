package org.example.grade_predictor.model.interfaces;

import java.sql.Connection;

public interface IDatabaseMigration {
    
    /**
     * Get the table name
     * @return table name
     */
    String getTableName();
    
    /**
     * Create the table structure
     * @param connection database connection
     * @throws Exception if table creation fails
     */
    void createTable(Connection connection) throws Exception;
    
    /**
     * Populate the table with initial data
     * @param connection database connection
     * @throws Exception if data population fails
     */
    void populateInitialData(Connection connection) throws Exception;
} 