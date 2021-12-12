package com.java.restaurant_hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionHandler {

    private static Connection connection;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private ConnectionHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (ConnectionHandler.connection==null || ConnectionHandler.connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ConnectionHandler.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return ConnectionHandler.connection;
    }

    public static void migrateDB() throws ClassNotFoundException, SQLException {
        if (!ConnectionHandler.existsTable("MenuItems")) ConnectionHandler.createMenuItemTable();
        if (!ConnectionHandler.existsTable("ListItems")) ConnectionHandler.createListItemTable();
        if (!ConnectionHandler.existsTable("Carts")) ConnectionHandler.createCartTable();
    }

    private static void createMenuItemTable() throws ClassNotFoundException, SQLException {
        String sqlStatement = "CREATE TABLE MenuItems " +
                   "(id BIGINT NOT NULL , " +
                   " name VARCHAR(255) NOT NULL, " + 
                   " price FLOAT NOT NULL, " + 
                   " active BOOLEAN NOT NULL, " + 
                   " dateOfLaunch DATE NOT NULL, " + 
                   " category VARCHAR(255) NOT NULL, " + 
                   " freeDelivery BOOLEAN NOT NULL, " + 
                   " PRIMARY KEY ( id ))";
        
        ConnectionHandler.executeUpdateStatement(sqlStatement);
    }

    private static void createListItemTable() throws ClassNotFoundException, SQLException {
        String sqlStatement = "CREATE TABLE ListItems " +
                   "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                   " userID BIGINT NOT NULL, " + 
                   " menuID BIGINT NOT NULL, " + 
                   " PRIMARY KEY ( id ))";
        
        ConnectionHandler.executeUpdateStatement(sqlStatement);
    }

    private static void createCartTable() throws ClassNotFoundException, SQLException {
        String sqlStatement = "CREATE TABLE Carts " +
                   "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                   " userID BIGINT NOT NULL, " + 
                   " total FLOAT NOT NULL, " + 
                   " PRIMARY KEY ( id ))";
        
        ConnectionHandler.executeUpdateStatement(sqlStatement);
    }

    public static ResultSet executeQueryStatement(String sqlStatement) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionHandler.getConnection();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sqlStatement);
    }

    public static void executeUpdateStatement(String sqlStatement) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionHandler.getConnection();
        Statement stmt = con.createStatement();
        try {
          stmt.executeUpdate(sqlStatement);  
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            stmt.close();
        }
    }

    private static boolean existsTable(String tableName) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionHandler.getConnection();
        Statement stmt = con.createStatement();
        ResultSet result = null;
        boolean exists = false;
        try {
            result = stmt.executeQuery("SHOW TABLES");
            while(result.next()) {
                if(result.getString(1).equals(tableName)) {
                    exists = true;
                    break;
                }
            }
        } finally {
            if(result != null) result.close();
            stmt.close();
        }
        return exists;
    }

    public static void closeConnection() throws SQLException {
        if (ConnectionHandler.connection!=null && !ConnectionHandler.connection.isClosed()) {
            ConnectionHandler.connection.close();
        }
    }
}
