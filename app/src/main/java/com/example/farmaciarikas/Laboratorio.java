package com.example.farmaciarikas;

public class Laboratorio {

    private int idLaboratorio;
    private String nombre;
    private String tipo;
    private String telefono;

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Laboratorio() {
    }

    public Laboratorio(int idLaboratorio, String nombre, String tipo, String telefono) {
        this.idLaboratorio = idLaboratorio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.telefono = telefono;
    }
}
