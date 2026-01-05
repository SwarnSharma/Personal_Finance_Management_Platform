package com.finance.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Database configuration
    private static final String URL =
            "jdbc:mysql://localhost:3306/finance_db?createDatabaseIfNotExist=true";
    private static final String USERNAME = "root"; //change if needed
    private static final String PASSWORD = "root"; //change if needed

    // Get database connection
    public static Connection getConnection() {

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✔ Database Connected Successfully");
        } catch (Exception e) {
            System.out.println("❌ Error connecting to DB: " + e.getMessage());
        }

        return conn;
    }
}
