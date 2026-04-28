package com.ethersteamboys.models;

import java.util.List;

public class Enemigo {
    private int id;
    private String nombre;
    private String descripcion;
    private String ejemplosHabilidades;  // campo real del backend (String, no lista)
    private List<Objeto> objetos;        // lista de Objeto relacionados

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getEjemplosHabilidades() { return ejemplosHabilidades; }
    public List<Objeto> getObjetos() { return objetos; }
}
