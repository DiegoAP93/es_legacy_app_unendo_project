package com.ethersteamboys.models;

public class RegistroRequest {
    private String nombre;
    private String username;
    private String password;
    private String email;
    private boolean newsletter;

    public RegistroRequest(String nombre, String username, String password, String email, boolean newsletter) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.email = email;
        this.newsletter = newsletter;
    }

    public String getNombre() { return nombre; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isNewsletter() { return newsletter; }
}
