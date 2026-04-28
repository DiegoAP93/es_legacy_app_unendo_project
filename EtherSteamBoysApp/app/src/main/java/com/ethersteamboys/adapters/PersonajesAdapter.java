package com.ethersteamboys.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ethersteamboys.R;
import com.ethersteamboys.models.Personaje;

import java.util.List;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Personaje personaje);
    }

    private final List<Personaje> personajes;
    private final OnItemClickListener listener;

    public PersonajesAdapter(List<Personaje> personajes, OnItemClickListener listener) {
        this.personajes = personajes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_personaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Personaje p = personajes.get(position);
        holder.tvNombre.setText(p.getNombre() != null ? p.getNombre() : "");
        holder.tvApodo.setText(p.getApodo() != null ? p.getApodo() : "");
        holder.tvClase.setText(p.getClase() != null ? p.getClase() : "");
        String rareza = p.getRareza() != null ? p.getRareza() : "";

        // Cambiar color según rareza
        int colorRes;

        switch (rareza) {
            case "SSR":
                colorRes = R.color.text_gold;
                break;
            case "SR":
                colorRes = R.color.text_silver;
                break;
            case "R":
                colorRes = R.color.text_bronze;
                break;
            default:
                colorRes = R.color.text_secondary;
                break;
        }

        holder.tvRareza.setTextColor(
                holder.itemView.getContext().getColor(colorRes)
        );
        holder.tvRareza.setText(rareza);

        if (p.getImagenUrl() != null && !p.getImagenUrl().isEmpty()) {
            Glide.with(holder.ivPersonaje.getContext())
                    .load(p.getImagenUrl())
                    .centerCrop()
                    .into(holder.ivPersonaje);
        } else {
            holder.ivPersonaje.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPersonaje;
        TextView tvNombre, tvApodo, tvClase, tvRareza;

        ViewHolder(View itemView) {
            super(itemView);
            ivPersonaje = itemView.findViewById(R.id.ivPersonaje);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvApodo = itemView.findViewById(R.id.tvApodo);
            tvClase = itemView.findViewById(R.id.tvClase);
            tvRareza = itemView.findViewById(R.id.tvRareza);
        }
    }
}
