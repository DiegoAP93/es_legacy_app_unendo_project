package com.ethersteamboys.models;

public class Habilidad {
    private int idHabilidad;
    private String nombre;
    private String descripcion;
    private Integer cooldown;    // null si no es ACTIVA, número si lo es
    private String categoria;   // ACTIVA, PASIVA, TALENTO, DEFINITIVA

    public int getIdHabilidad() { return idHabilidad; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Integer getCooldown() { return cooldown; }
    public String getCategoria() { return categoria; }

    public boolean esActiva() { return "ACTIVA".equals(categoria); }
    public boolean esPasiva() { return "PASIVA".equals(categoria); }
    public boolean esTalento() { return "TALENTO".equals(categoria); }
    public boolean esDefinitiva() { return "DEFINITIVA".equals(categoria); }
}
