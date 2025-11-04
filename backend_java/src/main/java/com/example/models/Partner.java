package com.example.models;

import java.sql.Timestamp;

public class Partner {
    private int id_partner;
    private String name_partner;
    private int id_horario;
    private int status_partner;
    private String observation_partner;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Partner() {}

    public Partner(int id_partner, String name_partner, int id_horario, int status_partner, String observation_partner, Timestamp created_at, Timestamp updated_at) {
        this.id_partner = id_partner;
        this.name_partner = name_partner;
        this.id_horario = id_horario;
        this.status_partner = status_partner;
        this.observation_partner = observation_partner;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId_partner() {
        return id_partner;
    }
    public void setId_partner(int id_partner) {
        this.id_partner = id_partner;
    }
    public String getName_partner() {
        return name_partner;
    }
    public void setName_partner(String name_partner) {
        this.name_partner = name_partner;
    }
    public int getId_horario() {
        return id_horario;
    }
    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }
    public int getStatus_partner() {
        return status_partner;
    }
    public void setStatus_partner(int status_partner) {
        this.status_partner = status_partner;
    }
    public String getObservation_partner() {
        return observation_partner;
    }
    public void setObservation_partner(String observation_partner) {
        this.observation_partner = observation_partner;
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