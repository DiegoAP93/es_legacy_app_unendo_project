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
        Long id = getIntent().getLongExtra("enemigo_id", -1);

        if (nombre != null) binding.tvToolbarNombre.setText(nombre.toUpperCase());
        binding.btnHome.setOnClickListener(v -> irAHome());

        if (id != -1) cargarFicha(id);
    }

    private void cargarFicha(Long id) {
        RetrofitClient.getApiService().getEnemigo(id).enqueue(new Callback<Enemigo>() {
            @Override
            public void onResponse(Call<Enemigo> call, Response<Enemigo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarFicha(response.body());
                } else {
                    Toast.makeText(FichaEnemigoActivity.this,
                            "Error al cargar el enemigo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Enemigo> call, Throwable t) {
                Toast.makeText(FichaEnemigoActivity.this,
                        "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarFicha(Enemigo e) {
        binding.tvNombre.setText(e.getNombre() != null ? e.getNombre() : "");
        binding.tvDescripcion.setText(e.getDescripcion() != null ? e.getDescripcion() : "");

        // Objetos soltados
        binding.llObjetos.removeAllViews();
        if (e.getObjetosSoltados() != null) {
            for (String obj : e.getObjetosSoltados()) {
                addTextoLista(binding.llObjetos, "# " + obj, false);
            }
        }

        // Habilidades
        binding.llHabilidades.removeAllViews();
        if (e.getHabilidades() != null) {
            for (String hab : e.getHabilidades()) {
                addTextoLista(binding.llHabilidades, "· " + hab, true);
            }
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
