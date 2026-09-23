package com.ethersteamboys.models;

import java.util.List;

public class Enemigo {
    private int idEnemigo;
    private String nombre;
    private String descripcion;
    private String ejemplosHabilidades;  // "Mordisco, Garras, Embestida"
    private List<Objeto> objetos;

    public int getId() { return idEnemigo; }
    public int getIdEnemigo() { return idEnemigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getEjemplosHabilidades() { return ejemplosHabilidades; }
    public List<Objeto> getObjetos() { return objetos; }
}
