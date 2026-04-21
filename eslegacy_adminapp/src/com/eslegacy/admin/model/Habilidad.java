package com.eslegacy.admin.model;

public class Habilidad {
	private int idHabilidad;
	private String nombre;
	private String categoria;
	private Integer cooldown;
	private String descripcion;
	
	public Habilidad(int idHabilidad, String nombre, String categoria, Integer cooldown, String descripcion) {
		super();
		this.idHabilidad = idHabilidad;
		this.nombre = nombre;
		this.categoria = categoria;
		this.cooldown = cooldown;
		this.descripcion = descripcion;
	}
	
	public Habilidad() {}

	public int getIdHabilidad() {
		return idHabilidad;
	}

	public void setIdHabilidad(int idHabilidad) {
		this.idHabilidad = idHabilidad;
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

	public Integer getCooldown() {
		return cooldown;
	}

	public void setCooldown(Integer cooldown) {
		this.cooldown = cooldown;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
