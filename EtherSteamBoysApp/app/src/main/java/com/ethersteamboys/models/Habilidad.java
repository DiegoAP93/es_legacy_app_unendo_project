package com.ethersteamboys.models;

public class Habilidad {
    private int id;
    private String nombre;
    private String descripcion;
    private String coste;       // ej: "3T", "5T"
    private String categoria;   // TipoHabilidad enum del backend

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getCoste() { return coste; }
    public String getCategoria() { return categoria; }
}
