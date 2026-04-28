package com.ethersteamboys.models;

import java.util.List;

public class Personaje {
    private int id;
    private String nombre;
    private String apodo;
    private String clase;
    private String rareza;
    private String historia;
    private Integer puntosVida;      // campo real del backend
    private String ataqueBasico;     // campo real del backend
    private String iniciativa;
    private String arquetipo;        // campo real del backend (singular)
    private String origen;
    private String imagenUrl;
    private List<Habilidad> habilidades;

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApodo() { return apodo; }
    public String getClase() { return clase; }
    public String getRareza() { return rareza; }
    public String getHistoria() { return historia; }
    public Integer getPuntosVida() { return puntosVida; }
    public String getAtaqueBasico() { return ataqueBasico; }
    public String getIniciativa() { return iniciativa; }
    public String getArquetipo() { return arquetipo; }
    public String getOrigen() { return origen; }
    public String getImagenUrl() { return imagenUrl; }
    public List<Habilidad> getHabilidades() { return habilidades; }
}
