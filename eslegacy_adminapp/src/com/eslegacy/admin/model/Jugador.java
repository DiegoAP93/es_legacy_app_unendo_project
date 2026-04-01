package com.eslegacy.admin.model;

public class Jugador {
	private String username;
	private String nombreCompleto;
	private String correo;
	private boolean activo;
	
	public Jugador(String username, String nombreCompleto, String correo, boolean activo) {
		super();
		this.username = username;
		this.nombreCompleto = nombreCompleto;
		this.correo = correo;
		this.activo = activo;
	}

	public String getNombreUsuario() {
		return username;
	}

	public void setNombreUsuario(String username) {
		this.username = username;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
