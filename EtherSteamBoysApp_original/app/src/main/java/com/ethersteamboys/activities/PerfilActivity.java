package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityPerfilBinding;
import com.ethersteamboys.models.ActualizarPerfilRequest;
import com.ethersteamboys.models.Usuario;
import com.ethersteamboys.network.RetrofitClient;
import com.ethersteamboys.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.btnHome.setOnClickListener(v -> irAHome());

        // Mostrar nombre de usuario actual
        binding.tvNombreUsuarioActual.setText(sessionManager.getNombre());

        // Cargar datos del perfil desde la API
        cargarPerfil();

        binding.btnActualizar.setOnClickListener(v -> actualizarPerfil());
    }

    private void cargarPerfil() {
        RetrofitClient.getApiService().getPerfil().enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario u = response.body();
                    if (u.getNombre() != null) {
                        binding.etNombreCompleto.setHint(u.getNombre());
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // No es crítico si falla la carga del perfil, el usuario puede editar igualmente
            }
        });
    }

    private void actualizarPerfil() {
        String passwordActual = binding.etContrasenaActual.getText().toString().trim();

        if (passwordActual.isEmpty()) {
            Toast.makeText(this, "Debes introducir tu contraseña actual", Toast.LENGTH_SHORT).show();
            return;
        }

        String nuevoNombre = binding.etNombreCompleto.getText().toString().trim();
        String nuevaPassword = binding.etNuevaContrasena.getText().toString().trim();

        // Al menos un campo de actualización debe estar relleno
        if (nuevoNombre.isEmpty() && nuevaPassword.isEmpty()) {
            Toast.makeText(this, "Rellena al menos un campo a modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnActualizar.setEnabled(false);

        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                nuevoNombre.isEmpty() ? null : nuevoNombre,
                nuevaPassword.isEmpty() ? null : nuevaPassword,
                passwordActual
        );

        RetrofitClient.getApiService().actualizarPerfil(request).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                binding.btnActualizar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    Usuario u = response.body();

                    // Si cambió la contraseña, actualizar sesión y credenciales
                    String newPass = nuevaPassword.isEmpty() ? sessionManager.getPassword() : nuevaPassword;
                    String newNombre = u.getNombre() != null ? u.getNombre() : sessionManager.getNombre();
                    sessionManager.guardarSesion(sessionManager.getUsername(), newPass, newNombre);
                    RetrofitClient.setCredenciales(sessionManager.getUsername(), newPass);

                    binding.tvNombreUsuarioActual.setText(newNombre);
                    binding.etNombreCompleto.setText("");
                    binding.etNuevaContrasena.setText("");
                    binding.etContrasenaActual.setText("");

                    Toast.makeText(PerfilActivity.this,
                            "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(PerfilActivity.this,
                            "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PerfilActivity.this,
                            "Error al actualizar (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                binding.btnActualizar.setEnabled(true);
                Toast.makeText(PerfilActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
