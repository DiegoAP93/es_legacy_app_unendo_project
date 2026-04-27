package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityRegistroBinding;
import com.ethersteamboys.models.RegistroRequest;
import com.ethersteamboys.models.Usuario;
import com.ethersteamboys.network.RetrofitClient;
import com.ethersteamboys.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        binding.btnRegistrar.setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String nombre = binding.etNombre.getText().toString().trim();
        String username = binding.etNombreUsuario.getText().toString().trim();
        String password = binding.etContrasena.getText().toString().trim();
        String email = binding.etCorreo.getText().toString().trim();
        boolean newsletter = binding.cbNewsletter.isChecked();

        if (nombre.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnRegistrar.setEnabled(false);
        RegistroRequest request = new RegistroRequest(nombre, username, password, email, newsletter);

        RetrofitClient.getApiService().registro(request).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                binding.btnRegistrar.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroActivity.this,
                            "¡Registro exitoso! Inicia sesión.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegistroActivity.this,
                            "Error en el registro (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                binding.btnRegistrar.setEnabled(true);
                Toast.makeText(RegistroActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
