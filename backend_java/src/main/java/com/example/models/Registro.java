package com.example.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Registro 
{
    private int id_registro;
    private BigDecimal monto_registro;
    private String moneda_registro;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Registro() {}

    // Constructor completo
    public Registro(int id_registro, BigDecimal monto_registro, String moneda_registro, Timestamp created_at, Timestamp updated_at) {
        this.id_registro = id_registro;
        this.monto_registro = monto_registro;
        this.moneda_registro = moneda_registro;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters y Setters
    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public BigDecimal getMonto_registro() {
        return monto_registro;
    }

    public void setMonto_registro(BigDecimal monto_registro) {
        this.monto_registro = monto_registro;
    }

    public String getMoneda_registro() {
        return moneda_registro;
    }

    public void setMoneda_registro(String moneda_registro) {
        this.moneda_registro = moneda_registro;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
