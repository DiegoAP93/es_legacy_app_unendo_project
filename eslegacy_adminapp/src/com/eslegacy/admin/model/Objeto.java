package com.eslegacy.admin.model;

public class Objeto {
	private int idObjeto;
	private String nombre;
	private String categoria;
	private Integer precio;
	
	public Objeto(int idObjeto, String nombre, String categoria, Integer precio) {
		super();
		this.idObjeto = idObjeto;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
	}

	public int getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
}
