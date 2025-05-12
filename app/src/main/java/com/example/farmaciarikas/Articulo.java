package com.example.farmaciarikas;

public class Articulo extends Elemento {


    int idArticulo;
    int idDistribuidor;
    String nombreArticulo;
    String clasificacion;

    public Articulo(int idArticulo, int idDistribuidor, String nombreArticulo, String clasificacion) {
        this.idArticulo = idArticulo;
        this.idDistribuidor = idDistribuidor;
        this.nombreArticulo = nombreArticulo;
        this.clasificacion = clasificacion;
    }

    public Articulo() {

    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdDistribuidor() {
        return idDistribuidor;
    }

    public void setIdDistribuidor(int idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
