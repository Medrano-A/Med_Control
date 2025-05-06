package com.example.farmaciarikas;

public abstract class Elemento {
    protected int codElemento;
    protected String nombre;
    protected int cantidad;
    protected String descripcion;
    protected double precioUni;
    protected String unidades;
    public Elemento() {}

    public int getCodElemento() {
        return codElemento;
    }

    public Elemento(int codElemento, int cantidad, String nombre, String descripcion, double precioUni, String unidades) {
        this.codElemento = codElemento;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUni = precioUni;
        this.unidades = unidades;
    }

    public void setCodElemento(int codElemento) {
        this.codElemento = codElemento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUni() {
        return precioUni;
    }

    public void setPrecioUni(double precioUni) {
        this.precioUni = precioUni;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
}

