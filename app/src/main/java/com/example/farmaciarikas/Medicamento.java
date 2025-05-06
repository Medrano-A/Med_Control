package com.example.farmaciarikas;

public class Medicamento extends Elemento{

    private String idMedicamento;
    private int codElemento;
    private int idLaboratorio;
    private String viaDeAdministracion;
    private String formaFarmaceutica;

    public Medicamento() {
    }

    public Medicamento(String idMedicamento, int codElemento, int idLaboratorio, String viaDeAdministracion, String formaFarmaceutica) {
        this.idMedicamento = idMedicamento;
        this.codElemento = codElemento;
        this.idLaboratorio = idLaboratorio;
        this.viaDeAdministracion = viaDeAdministracion;
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(int codElemento) {
        this.codElemento = codElemento;
    }

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public String getViaDeAdministracion() {
        return viaDeAdministracion;
    }

    public void setViaDeAdministracion(String viaDeAdministracion) {
        this.viaDeAdministracion = viaDeAdministracion;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }
}
