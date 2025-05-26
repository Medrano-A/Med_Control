package com.example.farmaciarikas;

public class Cliente {
    private String dui;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    public Cliente(String dui, String nombre, String apellido, String telefono, String correo) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Cliente() {
    }

    // Getters
    public String getDui() { return dui; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }

    // Setters
    public void setDui(String dui) { this.dui = dui; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Validación
    public boolean esValido() {
        return dui != null && dui.length() == 10 &&
                nombre != null && !nombre.isEmpty() && nombre.length() <= 30 &&
                apellido != null && !apellido.isEmpty() && apellido.length() <= 50 &&
                telefono != null && telefono.length() == 8 &&
                correo.length() <= 30;
    }
}
