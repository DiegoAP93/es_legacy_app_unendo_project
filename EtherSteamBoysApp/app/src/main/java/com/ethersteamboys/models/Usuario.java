package com.ethersteamboys.models;

public class Usuario {
    private Long id;
    private String nombre;
    private String username;
    private String email;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUsername(String username) { this.username = username; }
}
