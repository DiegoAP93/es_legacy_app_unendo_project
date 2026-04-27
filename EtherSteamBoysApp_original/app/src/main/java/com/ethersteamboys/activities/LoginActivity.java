package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityLoginBinding;
import com.ethersteamboys.models.Usuario;
import com.ethersteamboys.network.RetrofitClient;
import com.ethersteamboys.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        binding.btnIniciarSesion.setOnClickListener(v -> login());
    }

    private void login() {
        String username = binding.etNombreUsuario.getText().toString().trim();
        String password = binding.etContrasena.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Introduce usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnIniciarSesion.setEnabled(false);

        // Configurar credenciales en Retrofit antes de la petición
        RetrofitClient.setCredenciales(username, password);

        RetrofitClient.getApiService().login().enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                binding.btnIniciarSesion.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    sessionManager.guardarSesion(
                            username,
                            password,
                            usuario.getNombre() != null ? usuario.getNombre() : username
                    );
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finishAffinity(); // cerrar acceso y login
                } else {
                    RetrofitClient.clearCredenciales();
                    Toast.makeText(LoginActivity.this,
                            "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                binding.btnIniciarSesion.setEnabled(true);
                RetrofitClient.clearCredenciales();
                Toast.makeText(LoginActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
