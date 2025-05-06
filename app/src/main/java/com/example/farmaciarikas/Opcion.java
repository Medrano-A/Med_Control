package com.example.farmaciarikas;

public class Opcion {
    private String nombre;
    private int iconoResId;

    public Opcion(String nombre, int iconoResId) {
        this.nombre = nombre;
        this.iconoResId = iconoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIconoResId() {
        return iconoResId;
    }
}
