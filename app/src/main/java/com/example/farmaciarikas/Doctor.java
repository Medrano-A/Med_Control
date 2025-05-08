package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Model<Doctor> {
    // Campos de la entidad
    private int idDoctor;
    private String nombreDoctor;
    private String especialidad;
    private String jvpm;
    private String telefonoDoctor;
    private String correoDoctor;

    // Metadatos para mini-ORM
    public static final String TABLE    = "doctor";
    public static final String[] FIELDS = {
            "idDoctor",
            "nombreDoctor",
            "especialidad",
            "jvpm",
            "telefonoDoctor",
            "correoDoctor"
    };
    public static final String SEL_PK = "idDoctor = ?";

    // Constructores originales
    public Doctor() {
        super();
    }

    public Doctor(int idDoctor,
                  String nombreDoctor,
                  String especialidad,
                  String jvpm,
                  String telefonoDoctor,
                  String correoDoctor) {
        super();
        this.idDoctor = idDoctor;
        this.nombreDoctor = nombreDoctor;
        this.especialidad = especialidad;
        this.jvpm = jvpm;
        this.telefonoDoctor = telefonoDoctor;
        this.correoDoctor = correoDoctor;
        fillValues();
    }

    // Getters y setters originales
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
        fillValues();
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
        fillValues();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
        fillValues();
    }

    public String getJvpm() {
        return jvpm;
    }

    public void setJvpm(String jvpm) {
        this.jvpm = jvpm;
        fillValues();
    }

    public String getTelefonoDoctor() {
        return telefonoDoctor;
    }

    public void setTelefonoDoctor(String telefonoDoctor) {
        this.telefonoDoctor = telefonoDoctor;
        fillValues();
    }

    public String getCorreoDoctor() {
        return correoDoctor;
    }

    public void setCorreoDoctor(String correoDoctor) {
        this.correoDoctor = correoDoctor;
        fillValues();
    }

    // Sincroniza ContentValues
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idDoctor);
        valores.put(FIELDS[1], nombreDoctor);
        valores.put(FIELDS[2], especialidad);
        valores.put(FIELDS[3], jvpm);
        valores.put(FIELDS[4], telefonoDoctor);
        valores.put(FIELDS[5], correoDoctor);
    }

    /* ---------- Métodos CRUD de Model ---------- */
    @Override
    public boolean exists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(
                TABLE, null,
                SEL_PK,
                new String[]{String.valueOf(idDoctor)},
                null, null, null
        )) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() {
        if (exists()) {
            throw new SQLException("Doctor ya existe: " + idDoctor);
        }
        return dbHelper
                .getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() {
        if (!exists()) {
            throw new SQLException("No existe Doctor con ID " + idDoctor);
        }
        return dbHelper
                .getWritableDatabase()
                .update(TABLE, valores, SEL_PK,
                        new String[]{String.valueOf(idDoctor)});
    }

    @Override
    public long delete() {
        if (!exists()) {
            throw new SQLException("No existe Doctor con ID " + idDoctor);
        }
        return dbHelper
                .getWritableDatabase()
                .delete(TABLE, SEL_PK,
                        new String[]{String.valueOf(idDoctor)});
    }

    /* ---------- Consultas estáticas ---------- */
    public static Doctor getByPk(int idDoctor) {
        Doctor[] arr = getByQuery(SEL_PK,
                new String[]{String.valueOf(idDoctor)});
        return arr.length > 0 ? arr[0] : null;
    }

    public static Doctor[] getAll() {
        return getByQuery(null, null);
    }

    public static Doctor[] getByQuery(String sel, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Doctor> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) {
                list.add(modelFromCursor(c));
            }
        }
        return list.toArray(new Doctor[0]);
    }

    private static Doctor modelFromCursor(Cursor c) {
        return new Doctor(
                c.getInt(c.getColumnIndexOrThrow(FIELDS[0])),  // idDoctor
                c.getString(c.getColumnIndexOrThrow(FIELDS[1])),  // nombreDoctor
                c.getString(c.getColumnIndexOrThrow(FIELDS[2])),  // especialidad
                c.getString(c.getColumnIndexOrThrow(FIELDS[3])),  // jvpm
                c.getString(c.getColumnIndexOrThrow(FIELDS[4])),  // telefonoDoctor
                c.getString(c.getColumnIndexOrThrow(FIELDS[5]))   // correoDoctor
        );
    }
}
