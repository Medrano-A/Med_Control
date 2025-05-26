package com.example.farmaciarikas;

public class Distribuidor {
    private int idDistribuidor;
    private int idMarca;
    private String nombre;
    private String telefono;
    private String nit;
    private String correo;

    public Distribuidor(int idDistribuidor, int idMarca, String nombre, String telefono, String nit, String correo) {
        this.idDistribuidor = idDistribuidor;
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nit = nit;
        this.correo = correo;
    }

    public Distribuidor(){

    }

    public int getIdDistribuidor() {
        return idDistribuidor;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNit() {
        return nit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setIdDistribuidor(int idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean esValido() {
        return idDistribuidor > 0 && nombre != null && !nombre.isEmpty()
                && telefono != null && telefono.length() == 8
                && nit != null && nit.length() == 10;
    }
}
