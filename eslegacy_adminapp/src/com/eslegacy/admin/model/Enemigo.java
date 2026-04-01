package com.eslegacy.admin.model;

public class Enemigo {
	private int idEnemigo;
	private String nombre;
	private String ejemplosHabilidades;
	
	public Enemigo(int idEnemigo, String nombre, String ejemplosHabilidades) {
		super();
		this.idEnemigo = idEnemigo;
		this.nombre = nombre;
		this.ejemplosHabilidades = ejemplosHabilidades;
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

	public String getEjemplosHabilidades() {
		return ejemplosHabilidades;
	}

	public void setEjemplosHabilidades(String ejemplosHabilidades) {
		this.ejemplosHabilidades = ejemplosHabilidades;
	}
	
}
