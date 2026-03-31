package com.eslegacy.admin.model;

public class Personaje {
	private int idPersonaje;
    private String nombre;
    private String rareza;
    private String clase;
    
	public Personaje(int idPersonaje, String nombre, String rareza, String clase) {
		super();
		this.idPersonaje = idPersonaje;
		this.nombre = nombre;
		this.rareza = rareza;
		this.clase = clase;
	}

	public int getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(int idPersonaje) {
		this.idPersonaje = idPersonaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRareza() {
		return rareza;
	}

	public void setRareza(String rareza) {
		this.rareza = rareza;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

}
