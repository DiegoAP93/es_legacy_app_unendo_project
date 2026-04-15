package com.eslegacyapp.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Personaje")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonaje")
    private int idPersonaje;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Clase", length = 50)
    private String clase;

    @Column(name = "Rareza", length = 5)
    private String rareza;

    @Column(name = "Historia", columnDefinition = "TEXT")
    private String historia;

    @Column(name = "AtaqueBasico", length = 3)
    private String ataqueBasico;

    @Column(name = "PuntosVida")
    private Integer puntosVida;

    @Column(name = "Iniciativa", length = 3)
    private String iniciativa;

    @Column(name = "Origen", length = 300)
    private String origen;

    @Column(name = "Arquetipo", length = 300)
    private String arquetipo;
    
    @ManyToMany
    @JoinTable(
        name = "Personaje_Habilidad",
        joinColumns = @JoinColumn(name = "idPersonaje"),
        inverseJoinColumns = @JoinColumn(name = "idHabilidad")
    )
    private List<Habilidad> habilidades = new ArrayList<>();
    
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventario> inventario = new ArrayList<>();

    // Constructor vacío (OBLIGATORIO para JPA)
    public Personaje() {}

    // Getters y setters
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
	
	public List<Inventario> getInventario() {
		return inventario;
	}

	/**
	 * Método para añadir una habilidad al personaje. Comprueba esa habilidad no está ya unida al personaje
	 * y, en caso de no estarlo, la añade. A su vez verifica que cada personaje tiene: 
	 * 3 activas, 2 pasivas, 2 talentos y 1 definitiva.
	 * @param habilidad: Habilidad
	 */
	public void addHabilidad(Habilidad habilidad) {
		//TODO ¿Estas excepciones hay que tratarlas mejor en el futuro?
	    if (this.habilidades.contains(habilidad)) {
	        return;
	    }

	    long cantidadDelTipo = this.habilidades.stream()
	            .filter(h -> h.getCategoria() == habilidad.getCategoria())
	            .count();

	    switch (habilidad.getCategoria()) {
	        case ACTIVA:
	            if (cantidadDelTipo >= 3) {
	                throw new IllegalArgumentException("El personaje ya tiene 3 habilidades ACTIVAS.");
	            }
	            break;

	        case PASIVA:
	            if (cantidadDelTipo >= 2) {
	                throw new IllegalArgumentException("El personaje ya tiene 2 habilidades PASIVAS.");
	            }
	            break;

	        case DEFINITIVA:
	            if (cantidadDelTipo >= 1) {
	                throw new IllegalArgumentException("El personaje ya tiene una DEFINITIVA.");
	            }
	            break;

	        case TALENTO:
	            if (cantidadDelTipo >= 2) {
	                throw new IllegalArgumentException("El personaje ya tiene 2 talentos de EXPLORACION.");
	            }
	            break;
	    }

	    this.habilidades.add(habilidad);
	}

    
    /**
     *  Método para eliminar una habilidad del personaje.
     * @param habilidad: Habilidad
     */
    public void removeHabilidad(Habilidad habilidad) {
    	this.habilidades.remove(habilidad);
    }
}
