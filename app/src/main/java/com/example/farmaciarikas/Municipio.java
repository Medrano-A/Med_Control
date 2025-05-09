package com.example.farmaciarikas;

public class Municipio {
    private int idMunicipio;
    private int idDepartamento;
    private String nombre;

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipio() {
    }

    public Municipio(int idMunicipio, int idDepartamento, String nombre) {
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
    }
}
