package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityFichaObjetoBinding;
import com.ethersteamboys.models.Objeto;
import com.ethersteamboys.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaObjetoActivity extends AppCompatActivity {

    private ActivityFichaObjetoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFichaObjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombre = getIntent().getStringExtra("objeto_nombre");
        int id = getIntent().getIntExtra("objeto_id", -1);

        if (nombre != null) binding.tvToolbarNombre.setText(nombre.toUpperCase());
        binding.btnHome.setOnClickListener(v -> irAHome());

        if (id != -1) cargarFicha(id);
    }

    private void cargarFicha(int id) {
        RetrofitClient.getApiService().getObjeto(id).enqueue(new Callback<Objeto>() {
            @Override
            public void onResponse(Call<Objeto> call, Response<Objeto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Objeto o = response.body();
                    binding.tvNombre.setText(o.getNombre() != null ? o.getNombre() : "");
                    binding.tvCategoria.setText(o.getCategoria() != null ? o.getCategoria() : "");
                    binding.tvDescripcion.setText(o.getDescripcion() != null ? o.getDescripcion() : "");
                    binding.tvPrecio.setText(o.getPrecio() + " Monedas de Oro");
                } else {
                    Toast.makeText(FichaObjetoActivity.this,
                            "Error al cargar el objeto (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Objeto> call, Throwable t) {
                Toast.makeText(FichaObjetoActivity.this,
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
