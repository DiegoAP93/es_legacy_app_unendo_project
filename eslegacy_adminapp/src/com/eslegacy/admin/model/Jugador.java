package com.eslegacy.admin.model;

public class Jugador {
	private String username;
	private String nombreCompleto;
	private String correo;
	private boolean activo;
	private boolean bloqueado;
	
	public Jugador(String username, String nombreCompleto, String correo, boolean activo, boolean bloqueado) {
		super();
		this.username = username;
		this.nombreCompleto = nombreCompleto;
		this.correo = correo;
		this.activo = activo;
		this.bloqueado = bloqueado;
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

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
}
