package com.ethersteamboys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ethersteamboys.R;
import com.ethersteamboys.databinding.ActivityFichaEnemigoBinding;
import com.ethersteamboys.models.Enemigo;
import com.ethersteamboys.models.Objeto;
import com.ethersteamboys.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaEnemigoActivity extends AppCompatActivity {

    private ActivityFichaEnemigoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFichaEnemigoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombre = getIntent().getStringExtra("enemigo_nombre");
        int id = getIntent().getIntExtra("enemigo_id", -1);

        if (nombre != null) binding.tvToolbarNombre.setText(nombre.toUpperCase());
        binding.btnHome.setOnClickListener(v -> irAHome());

        if (id != -1) cargarFicha(id);
    }

    private void cargarFicha(int id) {
        RetrofitClient.getApiService().getEnemigo(id).enqueue(new Callback<Enemigo>() {
            @Override
            public void onResponse(Call<Enemigo> call, Response<Enemigo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarFicha(response.body());
                } else {
                    Toast.makeText(FichaEnemigoActivity.this,
                            "Error al cargar el enemigo (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Enemigo> call, Throwable t) {
                Toast.makeText(FichaEnemigoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarFicha(Enemigo e) {
        binding.tvNombre.setText(e.getNombre() != null ? e.getNombre() : "");
        binding.tvDescripcion.setText(e.getDescripcion() != null ? e.getDescripcion() : "");

        // Objetos soltados (lista de Objeto)
        binding.llObjetos.removeAllViews();
        if (e.getObjetos() != null && !e.getObjetos().isEmpty()) {
            for (Objeto obj : e.getObjetos()) {
                addTextoLista(binding.llObjetos, "# " + obj.getNombre(), false);
            }
        } else {
            addTextoLista(binding.llObjetos, "Ninguno", true);
        }

        // Ejemplos de habilidades (String del backend)
        binding.llHabilidades.removeAllViews();
        if (e.getEjemplosHabilidades() != null && !e.getEjemplosHabilidades().isEmpty()) {
            // Puede venir como texto separado por saltos de línea o comas
            String[] habs = e.getEjemplosHabilidades().split("[,\n]+");
            for (String hab : habs) {
                String h = hab.trim();
                if (!h.isEmpty()) addTextoLista(binding.llHabilidades, "· " + h, true);
            }
        } else {
            addTextoLista(binding.llHabilidades, "Sin ejemplos registrados", true);
        }
    }

    private void addTextoLista(LinearLayout container, String texto, boolean italic) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        tv.setTextSize(13f);
        if (italic) tv.setTypeface(null, Typeface.ITALIC);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        float density = getResources().getDisplayMetrics().density;
        params.setMargins(0, 0, 0, (int)(4 * density));
        tv.setLayoutParams(params);
        container.addView(tv);
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
