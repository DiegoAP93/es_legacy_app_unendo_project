package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ethersteamboys.adapters.PersonajesAdapter;
import com.ethersteamboys.databinding.ActivityPersonajesBinding;
import com.ethersteamboys.models.Personaje;
import com.ethersteamboys.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonajesActivity extends AppCompatActivity {

    private ActivityPersonajesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonajesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(v -> irAHome());
        binding.rvPersonajes.setLayoutManager(new LinearLayoutManager(this));

        cargarPersonajes();
    }

    private void cargarPersonajes() {
        RetrofitClient.getApiService().getPersonajes().enqueue(new Callback<List<Personaje>>() {
            @Override
            public void onResponse(Call<List<Personaje>> call, Response<List<Personaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PersonajesAdapter adapter = new PersonajesAdapter(response.body(), personaje -> {
                        Intent intent = new Intent(PersonajesActivity.this, FichaPersonajeActivity.class);
                        intent.putExtra("personaje_id", personaje.getId());
                        intent.putExtra("personaje_nombre", personaje.getNombre());
                        startActivity(intent);
                    });
                    binding.rvPersonajes.setAdapter(adapter);
                } else {
                    Toast.makeText(PersonajesActivity.this,
                            "Error al cargar personajes (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Personaje>> call, Throwable t) {
                Toast.makeText(PersonajesActivity.this,
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
