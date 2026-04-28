package com.ethersteamboys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ethersteamboys.R;
import com.ethersteamboys.databinding.ActivityFichaPersonajeBinding;
import com.ethersteamboys.models.Habilidad;
import com.ethersteamboys.models.Personaje;
import com.ethersteamboys.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaPersonajeActivity extends AppCompatActivity {

    private ActivityFichaPersonajeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFichaPersonajeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombre = getIntent().getStringExtra("personaje_nombre");
        int id = getIntent().getIntExtra("personaje_id", -1);

        if (nombre != null) binding.tvToolbarNombre.setText(nombre.toUpperCase());
        binding.btnHome.setOnClickListener(v -> irAHome());

        if (id != -1) cargarFicha(id);
    }

    private void cargarFicha(int id) {
        RetrofitClient.getApiService().getPersonaje(id).enqueue(new Callback<Personaje>() {
            @Override
            public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarFicha(response.body());
                } else {
                    Toast.makeText(FichaPersonajeActivity.this,
                            "Error al cargar la ficha", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Personaje> call, Throwable t) {
                Toast.makeText(FichaPersonajeActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarFicha(Personaje p) {
        // Nombre completo + apodo
        String nombreCompleto = p.getNombre() != null ? p.getNombre() : "";
        if (p.getApodo() != null && !p.getApodo().isEmpty()) {
            nombreCompleto += ", " + p.getApodo();
        }
        binding.tvNombreCompleto.setText(nombreCompleto);
        binding.tvClase.setText(p.getClase() != null ? p.getClase() : "");
        binding.tvRareza.setText(p.getRareza() != null ? p.getRareza() : "");

        // Stats - campos reales del backend
        binding.tvHP.setText(p.getPuntosVida() != null ? String.valueOf(p.getPuntosVida()) : "—");
        binding.tvAtaque.setText(p.getAtaqueBasico() != null ? p.getAtaqueBasico() : "—");
        binding.tvIniciativa.setText(p.getIniciativa() != null ? p.getIniciativa() : "—");

        // Arquetipo y origen
        StringBuilder info = new StringBuilder();
        if (p.getArquetipo() != null && !p.getArquetipo().isEmpty()) {
            info.append("ARQUETIPO: ").append(p.getArquetipo()).append("\n");
        }
        if (p.getOrigen() != null && !p.getOrigen().isEmpty()) {
            info.append("ORIGEN: ").append(p.getOrigen());
        }
        binding.tvArquetipos.setText(info.toString());

        // Historia
        binding.tvHistoria.setText(p.getHistoria() != null ? p.getHistoria() : "");

        // Habilidades
        binding.llHabilidades.removeAllViews();
        if (p.getHabilidades() != null && !p.getHabilidades().isEmpty()) {
            for (Habilidad h : p.getHabilidades()) {
                addHabilidadView(h);
            }
        } else {
            TextView tvSin = new TextView(this);
            tvSin.setText("Sin habilidades registradas");
            tvSin.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tvSin.setTextSize(13f);
            binding.llHabilidades.addView(tvSin);
        }
    }

    private void addHabilidadView(Habilidad h) {
        float density = getResources().getDisplayMetrics().density;

        // Fila: nombre + badge coste
        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams filaParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filaParams.setMargins(0, (int)(8 * density), 0, (int)(4 * density));
        fila.setLayoutParams(filaParams);

        TextView tvNombre = new TextView(this);
        tvNombre.setText(h.getNombre() != null ? h.getNombre() : "");
        tvNombre.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        tvNombre.setTextSize(14f);
        tvNombre.setTypeface(null, Typeface.BOLD);
        tvNombre.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        fila.addView(tvNombre);

        if (h.getCoste() != null && !h.getCoste().isEmpty()) {
            TextView tvCoste = new TextView(this);
            tvCoste.setText(h.getCoste());
            tvCoste.setTextColor(ContextCompat.getColor(this, R.color.bg_dark));
            tvCoste.setTextSize(11f);
            tvCoste.setBackgroundResource(R.drawable.bg_stat_badge);
            int pad = (int)(6 * density);
            tvCoste.setPadding(pad, pad, pad, pad);
            tvCoste.setGravity(Gravity.CENTER);
            fila.addView(tvCoste);
        }

        binding.llHabilidades.addView(fila);

        // Descripción
        if (h.getDescripcion() != null && !h.getDescripcion().isEmpty()) {
            TextView tvDesc = new TextView(this);
            tvDesc.setText(h.getDescripcion());
            tvDesc.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tvDesc.setTextSize(12f);
            LinearLayout.LayoutParams dp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dp.setMargins(0, 0, 0, (int)(12 * density));
            tvDesc.setLayoutParams(dp);
            binding.llHabilidades.addView(tvDesc);
        }
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
