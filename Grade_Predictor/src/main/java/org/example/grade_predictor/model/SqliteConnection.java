package org.example.grade_predictor.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static Connection instance = null;
    private static boolean isInitialized = false;

    /**
     * Constructor for the SqliteConnection class
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:GradePredictor.db";
        try {
            instance = DriverManager.getConnection(url);
            initializeDatabase();
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Get the instance of the SqliteConnection class
     * @return The instance of the SqliteConnection class
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
    
    /**
     * Initialize the database schema and populate initial data
     * This method is called only once when the connection is first created
     */
    private void initializeDatabase() {
        if (!isInitialized && instance != null) {
            try {
                DatabaseInitializer initializer = new DatabaseInitializer(instance);
                initializer.initializeDatabase();
                isInitialized = true;
            } catch (Exception e) {
                System.err.println("Failed to initialize database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
