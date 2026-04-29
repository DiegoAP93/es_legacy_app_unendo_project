package com.ethersteamboys.models;

public class Objeto {
    private int idObjeto;
    private String nombre;
    private String categoria;
    private String descripcion;
    private Integer precio;   // nullable: algunos objetos no tienen precio

    public int getId() { return idObjeto; }
    public int getIdObjeto() { return idObjeto; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public Integer getPrecio() { return precio; }
}
