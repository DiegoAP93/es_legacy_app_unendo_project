package com.eslegacyapp.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Jugador")
public class Jugador {

    @Id
    @Column(name = "NombreUsuario", length = 50)
    private String username;

    @Column(name = "Contrasena", nullable = false, length = 255)
    private String password;

    @Column(name = "NombreCompleto", length = 200)
    private String nombreCompleto;

    @Column(name = "Correo", length = 150)
    private String correo;
    
    @Column(name = "rol", length = 30, nullable = false)
    private String rol = "USER";
    
    @Column(name = "Activo")
    private boolean activo = true;

    @Column(name = "Bloqueado")
    private boolean bloqueado = false;

    @Column(name = "FechaEliminacion")
    private LocalDateTime fechaEliminacion;

    @ManyToMany
    @JoinTable(
        name = "Personaje_Jugador",
        joinColumns = @JoinColumn(name = "NombreUsuario"),
        inverseJoinColumns = @JoinColumn(name = "idPersonaje")
    )
    private List<Personaje> personajes = new ArrayList<>();

    public Jugador() {}

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public String getRol() {
	    return rol;
	}

	public void setRol(String rol) {
	    this.rol = rol;
	}

	public List<Personaje> getPersonajes() {
		return personajes;
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

	public LocalDateTime getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public void addPersonaje(Personaje personaje) {
        if (!this.personajes.contains(personaje)) {
            this.personajes.add(personaje);
        }
    }
	
	public boolean isAdmin() {
	    return "ADMIN".equalsIgnoreCase(this.rol);
	}
}