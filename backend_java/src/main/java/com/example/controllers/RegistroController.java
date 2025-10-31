package com.example.controllers;

import com.example.database.Conexion;
import com.example.models.Registro;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroController 
{
    private Connection conn;
    private Gson gson = new Gson();

    public RegistroController() {
        try {
            conn = Conexion.getConnection();
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    public String index() {
        List<Registro> lista = new ArrayList<>();
        String sql = "SELECT * FROM registros";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Registro r = new Registro();
                r.setIdRegistro(rs.getInt("id_registro"));
                r.setMontoRegistro(rs.getBigDecimal("monto_registro"));
                r.setMonedaRegistro(rs.getString("moneda_registro"));
                r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                r.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return gson.toJson("Error al listar registros: " + e.getMessage());
        }

        return gson.toJson(lista);
    }
}