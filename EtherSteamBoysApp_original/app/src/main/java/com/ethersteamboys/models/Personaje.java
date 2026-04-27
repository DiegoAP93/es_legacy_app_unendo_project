package com.ethersteamboys.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Personaje {
    private Long id;
    private String nombre;
    private String apodo;
    private String clase;
    private String rareza;
    private String historia;
    private Integer hp;
    private String ataque;
    private String iniciativa;
    private String arquetipos;
    private String origen;
    @SerializedName("imagen_url")
    private String imagenUrl;
    private List<Habilidad> habilidades;

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApodo() { return apodo; }
    public String getClase() { return clase; }
    public String getRareza() { return rareza; }
    public String getHistoria() { return historia; }
    public Integer getHp() { return hp; }
    public String getAtaque() { return ataque; }
    public String getIniciativa() { return iniciativa; }
    public String getArquetipos() { return arquetipos; }
    public String getOrigen() { return origen; }
    public String getImagenUrl() { return imagenUrl; }
    public List<Habilidad> getHabilidades() { return habilidades; }

    public static class Habilidad {
        private String nombre;
        private String descripcion;
        private String coste;

        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public String getCoste() { return coste; }
    }
}
