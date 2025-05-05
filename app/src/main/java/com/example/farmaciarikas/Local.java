package com.example.farmaciarikas;

public class Local {
   private int idLocal;
   private int idUbicacion;
   private String nombreLocal;
   private String tipoLocal;
   private String telefonoLocal;

    public Local() {
    }

    public Local(int idLocal, int idUbicacion, String nombreLocal, String tipoLocal, String telefonoLocal) {
        this.idLocal = idLocal;
        this.idUbicacion = idUbicacion;
        this.nombreLocal = nombreLocal;
        this.tipoLocal = tipoLocal;
        this.telefonoLocal = telefonoLocal;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getTelefonoLocal() {
        return telefonoLocal;
    }

    public void setTelefonoLocal(String telefonoLocal) {
        this.telefonoLocal = telefonoLocal;
    }
}
