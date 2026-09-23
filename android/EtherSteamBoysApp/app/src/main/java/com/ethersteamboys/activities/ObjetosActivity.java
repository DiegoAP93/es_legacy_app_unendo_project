package com.ethersteamboys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ethersteamboys.R;
import com.ethersteamboys.databinding.ActivityObjetosBinding;
import com.ethersteamboys.models.Objeto;
import com.ethersteamboys.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjetosActivity extends AppCompatActivity {

    private ActivityObjetosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityObjetosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(v -> irAHome());
        cargarObjetos();
    }

    private void cargarObjetos() {
        RetrofitClient.getApiService().getObjetos().enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarLista(response.body());
                } else {
                    Toast.makeText(ObjetosActivity.this,
                            "Error al cargar objetos (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Toast.makeText(ObjetosActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarLista(List<Objeto> objetos) {
        binding.llLista.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        int marginBottom = (int)(8 * density);

        for (Objeto obj : objetos) {
            TextView tv = new TextView(this);
            tv.setText("· " + obj.getNombre());
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            tv.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, marginBottom);
            tv.setLayoutParams(params);
            tv.setClickable(true);
            tv.setFocusable(true);

            tv.setOnClickListener(v -> {
                Intent intent = new Intent(this, FichaObjetoActivity.class);
                intent.putExtra("objeto_id", obj.getId());
                intent.putExtra("objeto_nombre", obj.getNombre());
                startActivity(intent);
            });

            binding.llLista.addView(tv);
        }
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
