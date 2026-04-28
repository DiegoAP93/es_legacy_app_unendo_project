package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityHomeBinding;
import com.ethersteamboys.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.tvPersonajes.setOnClickListener(v ->
                startActivity(new Intent(this, PersonajesActivity.class)));

        binding.tvObjetos.setOnClickListener(v ->
                startActivity(new Intent(this, ObjetosActivity.class)));

        binding.tvEnemigos.setOnClickListener(v ->
                startActivity(new Intent(this, EnemigosActivity.class)));

        binding.tvDados.setOnClickListener(v ->
                startActivity(new Intent(this, DadosActivity.class)));

        binding.tvPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));

        binding.tvSalir.setOnClickListener(v -> confirmarSalida());
    }

    private void confirmarSalida() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Seguro que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    sessionManager.cerrarSesion();
                    Intent intent = new Intent(this, AccesoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        confirmarSalida();
    }
}
