package com.example.controllers;

import com.example.database.Conexion;
import com.example.models.Registro;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroController 
{
    private static final Gson gson = new Gson();

    public String index() {
        List<Registro> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Registro r = new Registro(
                    rs.getInt("id_registro"),
                    rs.getBigDecimal("monto_registro"),
                    rs.getString("moneda_registro"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                registros.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener registros: " + e.getMessage());
        }
        return gson.toJson(registros);
    }

    public String show(Integer id){
        Registro registro = new Registro();
        String sql = "SELECT * FROM registros WHERE id_registro = ?";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                return "Registro no encontrado";
            }
            registro.setId_registro(rs.getInt("id_registro"));
            registro.setMonto_registro(rs.getBigDecimal("monto_registro"));
            registro.setMoneda_registro(rs.getString("moneda_registro"));
            registro.setCreated_at(rs.getTimestamp("created_at"));
            registro.setUpdated_at(rs.getTimestamp("updated_at"));
        }catch(SQLException e){
            System.err.println("Error al obtener registro: " + e.getMessage());
        }
          return gson.toJson(registro);
    }

    public String store(BigDecimal monto, String moneda){
        String sql = "INSERT INTO registros (monto_registro, moneda_registro) VALUES (?, ?)";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setBigDecimal(1, monto);
            stmt.setString(2, moneda);
            int filas = stmt.executeUpdate();
            if(filas == 0){
                return "Error en crear registro";
            }
        }catch(Exception e){
            System.out.println("Error al crear registro: " + e.getMessage());
        }
        return "Registro creado";
    }

    public String destroy(Integer id){
        String sql = "DELETE FROM registros WHERE id_registro = ?";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if(filas == 0){
                return "Error al eliminar registro";
            }
        }catch(Exception e){
            System.out.println("Error al eliminar registro: " + e.getMessage());
        }
        return "Registro eliminado";
    }

}