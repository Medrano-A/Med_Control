package com.example.farmaciarikas;

public class DetalleReceta {
    private int idDetReceta;
    private int idReceta;
    private String dosis;

    public DetalleReceta(int idDetReceta, int idReceta, String dosis) {
        this.idDetReceta = idDetReceta;
        this.idReceta = idReceta;
        this.dosis = dosis;
    }

    public DetalleReceta(){

    }
    public int getIdDetReceta() {
        return idDetReceta;
    }

    public void setIdDetReceta(int idDetReceta) {
        this.idDetReceta = idDetReceta;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    // Método para validar que los datos no estén vacíos o inválidos
    public boolean esValido() {
        return idDetReceta > 0 && dosis != null && !dosis.trim().isEmpty();
    }
}
