package com.eslegacyapp.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Personaje_Objeto")
public class Inventario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdInventario")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idPersonaje")
    @JsonIgnore
    private Personaje personaje;

    @ManyToOne
    @JoinColumn(name = "idObjeto")
    private Objeto objeto;

    private int cantidad = 1;
    private boolean equipado = false;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	public Objeto getObjeto() {
		return objeto;
	}
	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public boolean isEquipado() {
		return equipado;
	}
	public void setEquipado(boolean equipado) {
		this.equipado = equipado;
	}

    
}