package com.ethersteamboys.models;

import java.util.List;

public class Enemigo {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<String> objetosSoltados;
    private List<String> habilidades;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<String> getObjetosSoltados() { return objetosSoltados; }
    public List<String> getHabilidades() { return habilidades; }
}
