package com.ethersteamboys.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "EtherSteamBoysSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_LOGGED = "isLogged";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void guardarSesion(String username, String password, String nombre) {
        editor.putBoolean(KEY_LOGGED, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NOMBRE, nombre);
        editor.apply();
    }

    public boolean estaLogueado() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getPassword() {
        return prefs.getString(KEY_PASSWORD, "");
    }

    public String getNombre() {
        return prefs.getString(KEY_NOMBRE, "");
    }

    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }
}
