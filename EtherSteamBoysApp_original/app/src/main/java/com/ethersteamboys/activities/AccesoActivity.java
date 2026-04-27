package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityAccesoBinding;
import com.ethersteamboys.network.RetrofitClient;
import com.ethersteamboys.utils.SessionManager;

public class AccesoActivity extends AppCompatActivity {

    private ActivityAccesoBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccesoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        // Si ya hay sesión guardada, ir directamente a Home
        if (sessionManager.estaLogueado()) {
            RetrofitClient.setCredenciales(sessionManager.getUsername(), sessionManager.getPassword());
            irAHome();
            return;
        }

        binding.btnInicioSesion.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        binding.btnRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void irAHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
