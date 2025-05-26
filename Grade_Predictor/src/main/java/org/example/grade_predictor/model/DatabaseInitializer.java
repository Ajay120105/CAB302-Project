package org.example.grade_predictor.model;

import org.example.grade_predictor.model.interfaces.IDatabaseMigration;
import org.example.grade_predictor.model.migrations.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    
    private final Connection connection;
    private final List<IDatabaseMigration> migrations;
    
    public DatabaseInitializer(Connection connection) {
        this.connection = connection;
        this.migrations = new ArrayList<>();
        initializeMigrations();
    }
    
    /**
     * Initialize all migration strategies
     */
    private void initializeMigrations() {
        migrations.add(new UserTableMigration());
        migrations.add(new DegreeTableMigration());
        migrations.add(new UnitTableMigration());
        migrations.add(new EnrollmentTableMigration());
        migrations.add(new EnrolledUnitTableMigration());
    }
    
    /**
     * Initialize the entire database
     */
    public void initializeDatabase() {
        try {
            for (IDatabaseMigration migration : migrations) {
                String tableName = migration.getTableName();
                
                if (!tableExists(tableName)) {
                    System.out.println("Creating table: " + tableName);
                    migration.createTable(connection);
                    // Initial data might be empty
                    migration.populateInitialData(connection);
                } else {
                    System.out.println("Table already exists: " + tableName);
                }
            }
            System.out.println("Database initialization completed successfully.");
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Check if a table exists in the database
     * @param tableName name of the table to check
     * @return true if table exists, false otherwise
     */
    private boolean tableExists(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);
            boolean exists = tables.next();
            tables.close();
            
            return exists;
        } catch (Exception e) {
            System.err.println("Error checking table existence: " + e.getMessage());
            return false;
        }
    }
} 