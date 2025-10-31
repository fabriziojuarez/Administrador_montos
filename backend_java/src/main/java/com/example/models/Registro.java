package com.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Registro 
{
    private int idRegistro;
    private BigDecimal montoRegistro;
    private String monedaRegistro;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Registro() {}

    // Constructor completo
    public Registro(int idRegistro, BigDecimal montoRegistro, String monedaRegistro, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.idRegistro = idRegistro;
        this.montoRegistro = montoRegistro;
        this.monedaRegistro = monedaRegistro;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public BigDecimal getMontoRegistro() {
        return montoRegistro;
    }
    public void setMontoRegistro(BigDecimal montoRegistro) {
        this.montoRegistro = montoRegistro;
    }

    public String getMonedaRegistro() {
        return monedaRegistro;
    }
    public void setMonedaRegistro(String monedaRegistro) {
        this.monedaRegistro = monedaRegistro;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }


}
