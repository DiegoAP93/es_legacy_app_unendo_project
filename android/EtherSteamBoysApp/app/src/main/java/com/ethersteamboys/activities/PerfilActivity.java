package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityPerfilBinding;
import com.ethersteamboys.models.Jugador;
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

        // Mostrar username actual como título
        binding.tvNombreUsuarioActual.setText(sessionManager.getUsername());

        // Cargar nombre completo actual como hint
        cargarPerfil();

        binding.btnActualizar.setOnClickListener(v -> actualizarPerfil());
    }

    private void cargarPerfil() {
        RetrofitClient.getApiService().getJugador(sessionManager.getUsername())
                .enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Jugador j = response.body();
                    if (j.getNombreCompleto() != null) {
                        binding.etNombreCompleto.setHint(j.getNombreCompleto());
                    }
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                // No crítico, el usuario puede editar igualmente
            }
        });
    }

    private void actualizarPerfil() {
        String passwordActual = binding.etContrasenaActual.getText().toString().trim();
        if (passwordActual.isEmpty()) {
            Toast.makeText(this, "Debes introducir tu contraseña actual", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar contraseña actual contra la guardada en sesión
        if (!passwordActual.equals(sessionManager.getPassword())) {
            Toast.makeText(this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
            return;
        }

        String nuevoNombre = binding.etNombreCompleto.getText().toString().trim();
        String nuevaPassword = binding.etNuevaContrasena.getText().toString().trim();

        if (nuevoNombre.isEmpty() && nuevaPassword.isEmpty()) {
            Toast.makeText(this, "Rellena al menos un campo a modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnActualizar.setEnabled(false);

        // Construimos solo los cambios (el backend actualiza solo los campos no nulos)
        Jugador cambios = new Jugador();
        if (!nuevoNombre.isEmpty()) cambios.setNombreCompleto(nuevoNombre);
        if (!nuevaPassword.isEmpty()) cambios.setPassword(nuevaPassword);

        RetrofitClient.getApiService()
                .editarJugador(sessionManager.getUsername(), cambios)
                .enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                binding.btnActualizar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    Jugador actualizado = response.body();

                    // Actualizar sesión con nueva contraseña si se cambió
                    String newPass = nuevaPassword.isEmpty() ? sessionManager.getPassword() : nuevaPassword;
                    String newNombre = actualizado.getNombreCompleto() != null
                            ? actualizado.getNombreCompleto()
                            : sessionManager.getNombre();
                    sessionManager.guardarSesion(sessionManager.getUsername(), newPass, newNombre);

                    // Limpiar campos
                    binding.etNombreCompleto.setText("");
                    binding.etNuevaContrasena.setText("");
                    binding.etContrasenaActual.setText("");

                    Toast.makeText(PerfilActivity.this,
                            "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PerfilActivity.this,
                            "Error al actualizar (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
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
