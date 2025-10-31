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

    public String show(int id) {
        Registro registro = null;
        String sql = "SELECT * FROM registros WHERE id_registro = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    registro = new Registro();
                    registro.setIdRegistro(rs.getInt("id_registro"));
                    registro.setMontoRegistro(rs.getBigDecimal("monto_registro"));
                    registro.setMonedaRegistro(rs.getString("moneda_registro"));
                    registro.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    registro.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return gson.toJson("Error al obtener el registro: " + e.getMessage());
        }

        if (registro == null) {
            return gson.toJson("Registro no encontrado");
        }

        return gson.toJson(registro);
    }

    public String store(Registro registro) {
        String sql = "INSERT INTO registros (monto_registro, moneda_registro, created_at, updated_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setBigDecimal(1, registro.getMontoRegistro());
            pstmt.setString(2, registro.getMonedaRegistro());
            pstmt.setTimestamp(3, Timestamp.valueOf(registro.getCreatedAt()));
            pstmt.setTimestamp(4, Timestamp.valueOf(registro.getUpdateAt()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return gson.toJson("Error al crear el registro");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    registro.setIdRegistro(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return gson.toJson("Error al crear el registro: " + e.getMessage());
        }

        return gson.toJson(registro);
    }
}