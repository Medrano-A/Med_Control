package com.example.farmaciarikas;

public class Distrito {
    private int idDistrito;
    private int idMunicipio;
    private String nombre;

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Distrito() {
    }

    public Distrito(int idDistrito, int idMunicipio, String nombre) {
        this.idDistrito = idDistrito;
        this.idMunicipio = idMunicipio;
        this.nombre = nombre;
    }
}
