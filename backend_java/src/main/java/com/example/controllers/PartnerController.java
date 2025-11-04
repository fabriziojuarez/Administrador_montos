package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
}
