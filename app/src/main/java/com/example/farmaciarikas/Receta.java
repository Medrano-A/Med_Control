package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Receta extends Model<Receta> {
    // Campos originales
    private int idReceta;
    private String dui;
    private int idDoctor;
    private String nombrePaciente;
    private String fecha;        // yyyy-MM-dd
    private int edad;
    private String observaciones;

    // Metadatos mini-ORM
    public static final String TABLE    = "RECETA";
    public static final String[] FIELDS = {
            "IDRECETA", "DUI", "IDDOCTOR",
            "NOMBREPACIENTE", "FECHA", "EDAD", "OBSERVACIONES"
    };
    public static final String SEL_PK = "IDRECETA = ?";

    // Constructores originales
    public Receta(int idReceta,
                  String dui,
                  int idDoctor,
                  String nombrePaciente,
                  String fecha,
                  int edad,
                  String observaciones) {
        super();
        this.idReceta       = idReceta;
        this.dui            = dui;
        this.idDoctor       = idDoctor;
        this.nombrePaciente = nombrePaciente;
        this.fecha          = fecha;
        this.edad           = edad;
        this.observaciones  = observaciones;
        fillValues();
    }

    // Getters y setters originales
    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; fillValues(); }
    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; fillValues(); }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; fillValues(); }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; fillValues(); }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; fillValues(); }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; fillValues(); }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; fillValues(); }

    // Sincroniza ContentValues
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idReceta);
        valores.put(FIELDS[1], dui);
        valores.put(FIELDS[2], idDoctor);
        valores.put(FIELDS[3], nombrePaciente);
        valores.put(FIELDS[4], fecha);
        valores.put(FIELDS[5], edad);
        valores.put(FIELDS[6], observaciones);
    }

    /* Métodos CRUD */
    @Override
    public boolean exists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(TABLE, null,
                SEL_PK,
                new String[]{String.valueOf(idReceta)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() {
        if (exists()) {
            throw new SQLException("Receta ya existe: " + idReceta);
        }
        // Validar existencia de Doctor
        if (Doctor.getByPk(idDoctor) == null) {
            throw new SQLException("Doctor inexistente: " + idDoctor);
        }
        return dbHelper.getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() {
        if (!exists()) {
            throw new SQLException("No existe Receta con ID " + idReceta);
        }
        // Validar que el nuevo idDoctor exista
        if (Doctor.getByPk(idDoctor) == null) {
            throw new SQLException("Doctor inexistente: " + idDoctor);
        }
        return dbHelper.getWritableDatabase()
                .update(TABLE, valores,
                        SEL_PK,
                        new String[]{String.valueOf(idReceta)});
    }

    @Override
    public long delete() {
        // No borrar si hay detalles vinculados
        if (DetalleReceta.countByReceta(idReceta) > 0) {
            throw new SQLException(
                    "No se puede eliminar Receta con detalles asociados"
            );
        }
        return dbHelper.getWritableDatabase()
                .delete(TABLE,
                        SEL_PK,
                        new String[]{String.valueOf(idReceta)});
    }

    /* Consultas estáticas */
    public static Receta getByPk(int idReceta) {
        Receta[] arr = getByQuery(SEL_PK,
                new String[]{String.valueOf(idReceta)});
        return arr.length > 0 ? arr[0] : null;
    }

    public static Receta[] getAll() {
        return getByQuery(null, null);
    }

    public static Receta[] getByQuery(String sel, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Receta> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) list.add(modelFromCursor(c));
        }
        return list.toArray(new Receta[0]);
    }

    private static Receta modelFromCursor(Cursor c) {
        return new Receta(
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[0])), // IDRECETA
                c.getString(c.getColumnIndexOrThrow(FIELDS[1])), // DUI
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[2])), // IDDOCTOR
                c.getString(c.getColumnIndexOrThrow(FIELDS[3])), // NOMBREPACIENTE
                c.getString(c.getColumnIndexOrThrow(FIELDS[4])), // FECHA
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[5])), // EDAD
                c.getString(c.getColumnIndexOrThrow(FIELDS[6]))  // OBSERVACIONES
        );
    }
}
