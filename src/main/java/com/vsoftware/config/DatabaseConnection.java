package com.vsoftware.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    public Connection connection;
 
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(
                    DbConfig.getDbUrl(),
                    DbConfig.getDbUser(),
                    DbConfig.getDbPassword()
            );
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage(), e);
        }
    }
 
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    } 

    public boolean test() {
        try {
            return connection != null && connection.isValid(0);
        } catch (SQLException e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}