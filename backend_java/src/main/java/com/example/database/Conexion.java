package com.example.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion
{
    
    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DATABASE_URL");
    private static final String USER = dotenv.get("DATABASE_USER");
    private static final String PASSWORD = dotenv.get("DATABASE_PASSWORD");
    

    public static Connection getConnection() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }
}