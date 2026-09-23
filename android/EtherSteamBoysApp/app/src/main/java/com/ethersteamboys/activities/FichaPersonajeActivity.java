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

import java.util.ArrayList;
import java.util.List;

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

        if (id != -1) {
            cargarFicha(id);
        } else {
            Toast.makeText(this, "Error: ID de personaje no recibido", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarFicha(int id) {
        RetrofitClient.getApiService().getPersonaje(id).enqueue(new Callback<Personaje>() {
            @Override
            public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarFicha(response.body());
                } else {
                    Toast.makeText(FichaPersonajeActivity.this,
                            "Error al cargar la ficha (código " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
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
        // Nombre
        binding.tvNombre.setText(p.getNombre() != null ? p.getNombre() : "");

        // Rareza con color
        String rareza = p.getRareza() != null ? p.getRareza() : "";
        binding.tvRareza.setText(rareza);
        int colorRareza;
        switch (rareza) {
            case "SSR": colorRareza = R.color.text_gold; break;
            case "SR":  colorRareza = R.color.text_silver; break;
            case "R":   colorRareza = R.color.text_bronze; break;
            default:    colorRareza = R.color.text_secondary; break;
        }
        binding.tvRareza.setTextColor(ContextCompat.getColor(this, colorRareza));

        // Clase
        binding.tvClase.setText(p.getClase() != null ? p.getClase() : "");

        // Arquetipo y Origen
        binding.tvArquetipo.setText(p.getArquetipo() != null
                ? "ARQUETIPO: " + p.getArquetipo() : "");
        binding.tvOrigen.setText(p.getOrigen() != null
                ? "ORIGEN: " + p.getOrigen() : "");

        // Stats
        binding.tvHP.setText(p.getPuntosVida() != null ? String.valueOf(p.getPuntosVida()) : "—");
        binding.tvAtaque.setText(p.getAtaqueBasico() != null ? p.getAtaqueBasico() : "—");
        binding.tvIniciativa.setText(p.getIniciativa() != null ? p.getIniciativa() : "—");

        // Historia
        binding.tvHistoria.setText(p.getHistoria() != null ? p.getHistoria() : "");

        // Habilidades agrupadas por categoría
        binding.llHabilidades.removeAllViews();
        if (p.getHabilidades() != null && !p.getHabilidades().isEmpty()) {
            agregarSeccionHabilidades("Habilidades Activas",
                    filtrar(p.getHabilidades(), "ACTIVA"));
            agregarSeccionHabilidades("Habilidades Pasivas",
                    filtrar(p.getHabilidades(), "PASIVA"));
            agregarSeccionHabilidades("Talentos de Exploración",
                    filtrar(p.getHabilidades(), "TALENTO"));
            agregarSeccionHabilidades("Definitiva",
                    filtrar(p.getHabilidades(), "DEFINITIVA"));
        } else {
            TextView tv = new TextView(this);
            tv.setText("Sin habilidades registradas");
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tv.setTextSize(13f);
            binding.llHabilidades.addView(tv);
        }
    }

    /** Filtra habilidades por categoría */
    private List<Habilidad> filtrar(List<Habilidad> lista, String categoria) {
        List<Habilidad> resultado = new ArrayList<>();
        for (Habilidad h : lista) {
            if (categoria.equals(h.getCategoria())) resultado.add(h);
        }
        return resultado;
    }

    /** Añade el título de sección y las habilidades de esa categoría */
    private void agregarSeccionHabilidades(String titulo, List<Habilidad> lista) {
        if (lista.isEmpty()) return;

        float dp = getResources().getDisplayMetrics().density;

        // Título de sección (ej: "Habilidades Activas")
        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextColor(ContextCompat.getColor(this, R.color.text_gold));
        tvTitulo.setTextSize(15f);
        tvTitulo.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tParams.setMargins(0, (int)(16 * dp), 0, (int)(8 * dp));
        tvTitulo.setLayoutParams(tParams);
        binding.llHabilidades.addView(tvTitulo);

        for (Habilidad h : lista) {
            addHabilidadView(h, dp);
        }
    }

    private void addHabilidadView(Habilidad h, float dp) {
        // Fila: nombre + badge cooldown (solo ACTIVAS)
        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams filaParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filaParams.setMargins(0, (int)(4 * dp), 0, (int)(2 * dp));
        fila.setLayoutParams(filaParams);

        // Nombre habilidad
        TextView tvNombre = new TextView(this);
        tvNombre.setText(h.getNombre() != null ? h.getNombre() : "");
        tvNombre.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        tvNombre.setTextSize(14f);
        tvNombre.setTypeface(null, Typeface.BOLD);
        tvNombre.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        fila.addView(tvNombre);

        // Badge cooldown: solo si es ACTIVA y cooldown != null
        if (h.esActiva() && h.getCooldown() != null) {
            TextView tvCooldown = new TextView(this);
            tvCooldown.setText(h.getCooldown() + "T");
            tvCooldown.setTextColor(ContextCompat.getColor(this, R.color.bg_dark));
            tvCooldown.setTextSize(12f);
            tvCooldown.setTypeface(null, Typeface.BOLD);
            tvCooldown.setBackgroundResource(R.drawable.bg_stat_badge);
            int pad = (int)(8 * dp);
            tvCooldown.setPadding(pad, pad / 2, pad, pad / 2);
            tvCooldown.setGravity(Gravity.CENTER);
            fila.addView(tvCooldown);
        }

        binding.llHabilidades.addView(fila);

        // Descripción
        if (h.getDescripcion() != null && !h.getDescripcion().isEmpty()) {
            TextView tvDesc = new TextView(this);
            tvDesc.setText(h.getDescripcion());
            tvDesc.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tvDesc.setTextSize(12f);
            tvDesc.setLineSpacing(0, 1.4f);
            LinearLayout.LayoutParams dp2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dp2.setMargins(0, 0, 0, (int)(10 * dp));
            tvDesc.setLayoutParams(dp2);
            binding.llHabilidades.addView(tvDesc);
        }
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
