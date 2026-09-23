package com.eslegacy.admin.model;

import java.util.List;

public class Enemigo {

    private int idEnemigo;
    private String nombre;
    private String descripcion;
    private String ejemplosHabilidades;
    private List<Objeto> objetos;

    public Enemigo(int idEnemigo, String nombre, String descripcion, String ejemplosHabilidades, List<Objeto> objetos) {
		super();
		this.idEnemigo = idEnemigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ejemplosHabilidades = ejemplosHabilidades;
		this.objetos = objetos;
	}

	public int getIdEnemigo() {
        return idEnemigo;
    }

    public void setIdEnemigo(int idEnemigo) {
        this.idEnemigo = idEnemigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEjemplosHabilidades() {
        return ejemplosHabilidades;
    }

    public void setEjemplosHabilidades(String ejemplosHabilidades) {
        this.ejemplosHabilidades = ejemplosHabilidades;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }
}