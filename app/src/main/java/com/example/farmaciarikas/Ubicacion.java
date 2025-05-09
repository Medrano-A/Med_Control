package com.example.farmaciarikas;

public class Ubicacion {
    int idUbicacion;
    int idDistrito;
    int idMarca;
    String detalle;
    public Ubicacion(int idUbicacion, int idDistrito, int idMarca, String detalle) {
        this.idUbicacion = idUbicacion;
        this.idDistrito = idDistrito;
        this.idMarca = idMarca;
        this.detalle = detalle;
    }
    public Ubicacion() {

    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
