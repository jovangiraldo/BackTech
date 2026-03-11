package com.proyect.tech.Model;

public enum Role {
    ADMINISTRADOR,
    CLIENTE,
    ASISTENTE;
    @Override
    public String toString() {
        return this.name(); // devuelve "ADMINISTRADOR", "ASISTENTE", "CLIENTE"
    }    
}
  
