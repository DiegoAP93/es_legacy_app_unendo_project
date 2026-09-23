package com.ethersteamboys.models;

public class Jugador {
    private String username;
    private String password;
    private String nombreCompleto;
    private String correo;
    private String rol;
    private boolean activo;
    private boolean bloqueado;
    private boolean newsletter;

    // Constructor vacío necesario para Gson
    public Jugador() {}

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public boolean isBloqueado() { return bloqueado; }
    public boolean isNewsletter() { return newsletter; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setRol(String rol) { this.rol = rol; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
    public void setNewsletter(boolean newsletter) { this.newsletter = newsletter; }
}
