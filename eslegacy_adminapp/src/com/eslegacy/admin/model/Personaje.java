package com.eslegacy.admin.model;

import java.util.ArrayList;
import java.util.List;

public class Personaje {
	private int idPersonaje;
    private String nombre;
    private String clase;
    private String rareza;
    private String historia;
    private String ataqueBasico;
    private Integer puntosVida;
    private String iniciativa;
    private String origen;
    private String arquetipo;
    private List<Habilidad> habilidades = new ArrayList<>();
    
	public Personaje(int idPersonaje, String nombre, String clase, String rareza, String historia, String ataqueBasico,
			Integer puntosVida, String iniciativa, String origen, String arquetipo, List<Habilidad> habilidades) {
		super();
		this.idPersonaje = idPersonaje;
		this.nombre = nombre;
		this.clase = clase;
		this.rareza = rareza;
		this.historia = historia;
		this.ataqueBasico = ataqueBasico;
		this.puntosVida = puntosVida;
		this.iniciativa = iniciativa;
		this.origen = origen;
		this.arquetipo = arquetipo;
		this.habilidades = habilidades;
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

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getRareza() {
		return rareza;
	}

	public void setRareza(String rareza) {
		this.rareza = rareza;
	}

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	public String getAtaqueBasico() {
		return ataqueBasico;
	}

	public void setAtaqueBasico(String ataqueBasico) {
		this.ataqueBasico = ataqueBasico;
	}

	public Integer getPuntosVida() {
		return puntosVida;
	}

	public void setPuntosVida(Integer puntosVida) {
		this.puntosVida = puntosVida;
	}

	public String getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(String iniciativa) {
		this.iniciativa = iniciativa;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getArquetipo() {
		return arquetipo;
	}

	public void setArquetipo(String arquetipo) {
		this.arquetipo = arquetipo;
	}

	public List<Habilidad> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidad> habilidades) {
		this.habilidades = habilidades;
	}

    

}
