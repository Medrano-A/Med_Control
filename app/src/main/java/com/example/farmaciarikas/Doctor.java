package com.example.farmaciarikas;

public class Doctor {
   private int idDoctor;
   private String nombreDoctor;
   private String especialidad;
   private String jvpm;
   private String telefonoDoctor;
   private String correoDoctor;

    public Doctor() {
    }

    public Doctor(int idDoctor, String nombreDoctor, String especialidad, String jvpm, String telefonoDoctor, String correoDoctor) {
        this.idDoctor = idDoctor;
        this.nombreDoctor=nombreDoctor;
        this.especialidad=especialidad;
        this.correoDoctor=correoDoctor;
        this.jvpm=jvpm;
        this.telefonoDoctor=telefonoDoctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getJvpm() {
        return jvpm;
    }

    public void setJvpm(String jvpm) {
        this.jvpm = jvpm;
    }

    public String getTelefonoDoctor() {
        return telefonoDoctor;
    }

    public void setTelefonoDoctor(String telefonoDoctor) {
        this.telefonoDoctor = telefonoDoctor;
    }

    public String getCorreoDoctor() {
        return correoDoctor;
    }

    public void setCorreoDoctor(String correoDoctor) {
        this.correoDoctor = correoDoctor;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }
}
