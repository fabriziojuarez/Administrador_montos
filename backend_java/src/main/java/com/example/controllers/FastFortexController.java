package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.database.Conexion;
import com.example.services.FastFortexService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FastFortexController {
    static FastFortexService fastForexService = new FastFortexService();

    public static String getTotal(String moneda_base, String mes){
        Double total_moneda_base = 0.0;
        String sql = "SELECT SUM(monto_registro) as 'total', moneda_registro as 'moneda' FROM registros WHERE MONTH(created_at) = ? GROUP BY registros.moneda_registro";
        try(
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
        ){
            stmt.setString(1, mes);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                double montoTotal = rs.getDouble("total");
                String moneda = rs.getString("moneda");
                if (moneda.equalsIgnoreCase(moneda_base)) {
                    total_moneda_base += montoTotal;
                } else {
                    String response = fastForexService.fetch_one(moneda, moneda_base);

                    JsonObject json = JsonParser.parseString(response).getAsJsonObject();
                    JsonObject result = json.getAsJsonObject("result");

                    if (result != null && result.has(moneda_base)) {
                        double tipoCambio = result.get(moneda_base).getAsDouble();
                        total_moneda_base += montoTotal * tipoCambio;
                    } else {
                        System.out.println(" No se pudo obtener tipo de cambio para " + moneda);
                    }
                }
            }
                        
        }catch(Exception e){
            System.out.println("Error al calcular total: " + e.getMessage());
            return "{\"error\": \"No se pudo calcular el total\"}";
        }
        return "{ \"total_convertido\": " + total_moneda_base + 
        ", \"moneda_base\": \"" + moneda_base + "\" }" +
        ", \"mes\": \"" + mes + "\" }";
    }
}
