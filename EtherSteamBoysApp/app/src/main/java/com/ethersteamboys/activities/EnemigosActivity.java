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
import com.ethersteamboys.databinding.ActivityEnemigosBinding;
import com.ethersteamboys.models.Enemigo;
import com.ethersteamboys.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnemigosActivity extends AppCompatActivity {

    private ActivityEnemigosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnemigosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(v -> irAHome());
        cargarEnemigos();
    }

    private void cargarEnemigos() {
        RetrofitClient.getApiService().getEnemigos().enqueue(new Callback<List<Enemigo>>() {
            @Override
            public void onResponse(Call<List<Enemigo>> call, Response<List<Enemigo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarLista(response.body());
                } else {
                    Toast.makeText(EnemigosActivity.this,
                            "Error al cargar enemigos (código " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Enemigo>> call, Throwable t) {
                Toast.makeText(EnemigosActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarLista(List<Enemigo> enemigos) {
        binding.llLista.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        int marginBottom = (int)(8 * density);

        for (Enemigo ene : enemigos) {
            TextView tv = new TextView(this);
            tv.setText("· " + ene.getNombre());
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
                Intent intent = new Intent(this, FichaEnemigoActivity.class);
                intent.putExtra("enemigo_id", ene.getId());
                intent.putExtra("enemigo_nombre", ene.getNombre());
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
