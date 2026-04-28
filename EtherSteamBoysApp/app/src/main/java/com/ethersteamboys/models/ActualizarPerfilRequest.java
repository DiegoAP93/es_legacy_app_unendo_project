package com.ethersteamboys.models;

public class ActualizarPerfilRequest {
    private String nombre;
    private String nuevaPassword;
    private String passwordActual;

    public ActualizarPerfilRequest(String nombre, String nuevaPassword, String passwordActual) {
        this.nombre = nombre;
        this.nuevaPassword = nuevaPassword;
        this.passwordActual = passwordActual;
    }

    public String getNombre() { return nombre; }
    public String getNuevaPassword() { return nuevaPassword; }
    public String getPasswordActual() { return passwordActual; }
}
