package com.eslegacyapp.api.dto;

public class LoginResponse {

    private String username;
    private String rol;

    public LoginResponse(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }
}