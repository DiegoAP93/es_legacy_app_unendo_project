package com.eslegacyapp.api.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Objeto")
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idObjeto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(nullable = true)
    private Integer precio;

    @ManyToMany(mappedBy = "objetos")
    @JsonIgnore
    private List<Enemigo> enemigos = new ArrayList<>();
    
    public Objeto() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    public List<Enemigo> getEnemigos() {
        return enemigos;
    }
}
