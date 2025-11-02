package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.example.database.Conexion;
import com.google.gson.Gson;

public class PorcentController {
    private static final Gson gson = new Gson();

    public String porcentajeRegistros() {
        String sql = "CALL porcent_monedas()";
        List<Object> porcentajes = new ArrayList<>();
        try (
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Map<String, Object> porcentaje = new HashMap<>();

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                        
                    porcentaje.put(columnName, value);
                }
                porcentajes.add(porcentaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular porcentajes: " + e.getMessage());
        }
        return gson.toJson(porcentajes);
    }
}
