package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityLoginBinding;
import com.ethersteamboys.models.LoginRequest;
import com.ethersteamboys.models.LoginResponse;
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
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        LoginRequest request = new LoginRequest(username, password);

        RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.btnIniciarSesion.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    // Guardar sesión: guardamos también la contraseña en claro para usarla si hace falta editar perfil
                    sessionManager.guardarSesion(loginResponse.getUsername(), password, loginResponse.getUsername());
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finishAffinity();
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this,
                            "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(LoginActivity.this,
                            "Cuenta bloqueada o eliminada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error al iniciar sesión (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.btnIniciarSesion.setEnabled(true);
                Toast.makeText(LoginActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
