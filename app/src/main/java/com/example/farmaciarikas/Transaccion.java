package com.example.farmaciarikas;

import java.time.LocalDate;

public class Transaccion {

    private int idVenta;
    private String Dui;
    private int IdUsuario;
    private int id_local;
    private LocalDate fechaEmision;
    private double total;
    private String tipo;

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getDui() {
        return Dui;
    }

    public void setDui(String dui) {
        Dui = dui;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Transaccion() {
    }

    public Transaccion(int idVenta, String dui, int idUsuario, int id_local, LocalDate fechaEmision, double total, String tipo) {
        this.idVenta = idVenta;
        Dui = dui;
        IdUsuario = idUsuario;
        this.id_local = id_local;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.tipo = tipo;
    }
}
