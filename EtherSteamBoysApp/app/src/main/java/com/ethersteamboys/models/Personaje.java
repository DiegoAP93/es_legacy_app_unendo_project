package com.ethersteamboys.models;

import java.util.List;

public class Personaje {
    private int idPersonaje;   // PK real del backend
    private String nombre;
    private String clase;
    private String rareza;
    private String historia;
    private Integer puntosVida;
    private String ataqueBasico;
    private String iniciativa;
    private String arquetipo;
    private String origen;
    private String imagenUrl;
    private List<Habilidad> habilidades;

    // Getters
    public int getId() { return idPersonaje; }   // alias cómodo para la app
    public int getIdPersonaje() { return idPersonaje; }
    public String getNombre() { return nombre; }
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
