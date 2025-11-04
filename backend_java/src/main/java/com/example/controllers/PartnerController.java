package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import com.example.database.Conexion;
import com.example.models.Partner;

import com.google.gson.Gson;

public class PartnerController {

    private static final Gson gson = new Gson();

    public String index() {
        List<Partner> partners = new ArrayList<>();
        String sql = "SELECT * FROM partners";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ){
            while (rs.next()) {
                Partner p = new Partner(
                    rs.getInt("id_partner"),
                    rs.getString("name_partner"),
                    rs.getInt("id_horario"),
                    rs.getInt("status_partner"),
                    rs.getString("observation_partner"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                partners.add(p);
            }
        }catch(Exception e){
            System.err.println("Error al obtener registros: " + e.getMessage());
        }
        return gson.toJson(partners);
    }

    public String update(Integer id, String name_partner, Integer id_horario, Integer status_partner, String observation_partner) {
        String sql = "UPDATE partners SET name_partner = ?, id_horario = ?, status_partner = ?, observation_partner = ?, updated_at = ? WHERE id_partner = ?";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, name_partner);
            stmt.setInt(2, id_horario);
            stmt.setInt(3, status_partner);
            stmt.setString(4, observation_partner);
            stmt.setString(5, Timestamp.valueOf(java.time.LocalDateTime.now()).toString());
            stmt.setInt(6, id);
            
            int filas = stmt.executeUpdate(); 

            if (filas > 0) {
                return "{\"message\": \"Partner actualizado correctamente\"}";
            } else {
                return "{\"error\": \"No se encontró el partner con id " + id + "\"}";
            }
        }catch(SQLException e){
            System.out.println("Error al actualizar partner: " + e.getMessage());
            return "{\"error\": \"Error al actualizar partner\"}";
        }
    }
}
