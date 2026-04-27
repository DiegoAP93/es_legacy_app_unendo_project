package com.ethersteamboys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ethersteamboys.databinding.ActivityDadosBinding;

import java.util.Random;

public class DadosActivity extends AppCompatActivity {

    private ActivityDadosBinding binding;
    private final Random random = new Random();

    // Opciones de dado
    private final String[] opcionesCaras = {"d4", "d6", "d8", "d10", "d12", "d20"};
    private final int[] caras = {4, 6, 8, 10, 12, 20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(v -> irAHome());

        // Spinner de caras
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                opcionesCaras
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCaras.setAdapter(adapter);
        binding.spinnerCaras.setSelection(1); // d6 por defecto

        binding.btnLanzar.setOnClickListener(v -> lanzarDados());
    }

    private void lanzarDados() {
        int spinnerPos = binding.spinnerCaras.getSelectedItemPosition();
        int numCaras = caras[spinnerPos];
        String tipoD = opcionesCaras[spinnerPos];

        String numDadosStr = binding.etNumeroDados.getText().toString().trim();
        if (numDadosStr.isEmpty()) {
            Toast.makeText(this, "Indica el número de dados", Toast.LENGTH_SHORT).show();
            return;
        }

        int numDados;
        try {
            numDados = Integer.parseInt(numDadosStr);
            if (numDados < 1 || numDados > 100) {
                Toast.makeText(this, "Número de dados entre 1 y 100", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número de dados inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lanzar
        StringBuilder sb = new StringBuilder();
        int total = 0;
        sb.append(numDados).append(tipoD).append(" → ");

        for (int i = 0; i < numDados; i++) {
            int resultado = random.nextInt(numCaras) + 1;
            total += resultado;
            sb.append(resultado);
            if (i < numDados - 1) sb.append(" + ");
        }

        if (numDados > 1) {
            sb.append("\n\nTotal: ").append(total);
        }

        binding.tvResultado.setText(sb.toString());
    }

    private void irAHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
