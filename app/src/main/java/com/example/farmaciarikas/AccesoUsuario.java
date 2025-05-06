package com.example.farmaciarikas;

public class AccesoUsuario {
    private int id_acceso;
    private String id_usuario;
    private int id_opcion_crud;

    public AccesoUsuario(int id_acceso, String id_usuario, int id_opcion_crud) {
        this.id_acceso = id_acceso;
        this.id_usuario = id_usuario;
        this.id_opcion_crud = id_opcion_crud;
    }

    public int getId_acceso() {
        return id_acceso;
    }

    public void setId_acceso(int id_acceso) {
        this.id_acceso = id_acceso;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_opcion_crud() {
        return id_opcion_crud;
    }

    public void setId_opcion_crud(int id_opcion_crud) {
        this.id_opcion_crud = id_opcion_crud;
    }
}
