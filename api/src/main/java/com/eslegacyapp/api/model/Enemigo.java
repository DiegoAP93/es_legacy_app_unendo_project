package com.eslegacyapp.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Enemigo")
public class Enemigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEnemigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 255)
    private String ejemplosHabilidades;
    
    @ManyToMany
    @JoinTable(
        name = "Enemigo_Objeto",
        joinColumns = @JoinColumn(name = "idEnemigo"),
        inverseJoinColumns = @JoinColumn(name = "idObjeto")
    )
    private List<Objeto> objetos = new ArrayList<>();

    public Enemigo() {
    }

    public int getIdEnemigo() {
        return idEnemigo;
    }

    public void setIdEnemigo(int idEnemigo) {
        this.idEnemigo = idEnemigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEjemplosHabilidades() {
        return ejemplosHabilidades;
    }

    public void setEjemplosHabilidades(String ejemplosHabilidades) {
        this.ejemplosHabilidades = ejemplosHabilidades;
    }
    
    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }
}
