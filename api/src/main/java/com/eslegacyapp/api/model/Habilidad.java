package com.eslegacyapp.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Habilidad")
public class Habilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHabilidad;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "Categoria", nullable = false)
    private TipoHabilidad categoria;
    
    @Column(name = "Cooldown")
    private Integer cooldown;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany(mappedBy = "habilidades")
    @JsonIgnore
    private List<Personaje> personajes = new ArrayList<>();

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

	public TipoHabilidad getCategoria() {
		return categoria;
	}

	public void setCategoria(TipoHabilidad categoria) {
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

	public List<Personaje> getPersonajes() {
		return personajes;
	}

}
