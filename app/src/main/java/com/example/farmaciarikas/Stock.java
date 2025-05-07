package com.example.farmaciarikas;

public class Stock {
    private int idStock;
    private int codElemento;
    private int idLocal;
    private int cantidad;
    private String fechaVencimiento;

    // Constructor
    public Stock(int idStock, int codElemento, int idLocal, int cantidad, String fechaVencimiento) {
        this.idStock = idStock;
        this.codElemento = codElemento;
        this.idLocal = idLocal;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(int codElemento) {
        this.codElemento = codElemento;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}

