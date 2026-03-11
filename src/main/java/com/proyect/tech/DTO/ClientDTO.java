package com.proyect.tech.DTO;

import com.proyect.tech.Model.Client;

public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password; // solo para entrada, nunca se devuelve

    // Constructor vacío para deserializar JSON (registro) 👈
    public ClientDTO() {}

    // Constructor desde entidad para devolver respuesta 👈
    public ClientDTO(Client client) {
        this.id      = client.getId();
        this.phone   = client.getPhone();
        this.address = client.getAddress();
        this.name    = client.getUser().getName();
        this.email   = client.getUser().getEmail();
    }

    // Getters
    public Long getId()        { return id; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getPhone()   { return phone; }
    public String getAddress() { return address; }
    public String getPassword(){ return password; }

    // Setters (necesarios para deserializar el JSON entrante)
    public void setId(Long id)             { this.id = id; }
    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setPhone(String phone)     { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setPassword(String password){ this.password = password; }
}