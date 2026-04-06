package com.studentresult.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public class DatabaseConnection {

    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root"; // Change it if needed
    private static final String PASSWORD = "Raghava@4022904"; // Change it if needed

    // Prevent instantiation
    private DatabaseConnection() {
    }

    /**
     * Establishes and returns a database connection.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the driver (optional on modern JDBC, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Please add the connector jar to your classpath.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
