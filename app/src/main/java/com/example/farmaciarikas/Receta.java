package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException; // Asegúrate de usar android.database.SQLException
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Receta extends Model<Receta> {
    // Campos originales
    private int idReceta;
    private String dui; // DUI del Cliente
    private int idDoctor; // Este es el ID del Doctor
    private String nombrePaciente;
    private String fecha;        // yyyy-MM-dd
    private int edad;
    private String observaciones;

    // Metadatos mini-ORM
    public static final String TABLE    = "RECETA"; // Coincide con tu DDL
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
        super(); // Llama al constructor de Model
        this.idReceta       = idReceta;
        this.dui            = dui;
        this.idDoctor       = idDoctor;
        this.nombrePaciente = nombrePaciente;
        this.fecha          = fecha;
        this.edad           = edad;
        this.observaciones  = observaciones;
        fillValues(); // Sincroniza con ContentValues de Model
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
        valores.put(FIELDS[2], idDoctor); // Se guarda el idDoctor
        valores.put(FIELDS[3], nombrePaciente);
        valores.put(FIELDS[4], fecha);
        valores.put(FIELDS[5], edad);
        valores.put(FIELDS[6], observaciones);
    }

    /* Métodos CRUD */

    // Método privado para verificar si un Doctor existe en la tabla 'doctor'
    private boolean doctorExists(int doctorId) {
        if (dbHelper == null) {
            throw new IllegalStateException("Model.dbHelper no ha sido inicializado. Llama a Model.init() primero.");
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("doctor",
                    new String[]{"idDoctor"},
                    "idDoctor = ?",
                    new String[]{String.valueOf(doctorId)},
                    null, null, null);
            return cursor.moveToFirst();
        } catch (Exception e) {
            // e.printStackTrace(); // Opcional: loguear error
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // Método privado para verificar si un Cliente existe en la tabla 'cliente'
    private boolean clienteExists(String duiCliente) {
        if (dbHelper == null) {
            throw new IllegalStateException("Model.dbHelper no ha sido inicializado. Llama a Model.init() primero.");
        }
        // Si el DUI es nulo o vacío, no se realiza la verificación (asumiendo que el DUI es opcional en la receta)
        // La tabla RECETA tiene DUI TEXT, lo que permite NULL.
        if (duiCliente == null || duiCliente.trim().isEmpty()) {
            return true; // Se considera válido si no se proporciona DUI
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Consulta directa a la tabla 'cliente'
            // El nombre de la tabla es 'cliente' y la columna PK es 'dui'
            cursor = db.query("cliente",                 /* tabla */
                    new String[]{"dui"},            /* columnas a retornar */
                    "dui = ?",                      /* selección */
                    new String[]{duiCliente},       /* argumentos de selección */
                    null,                           /* groupBy */
                    null,                           /* having */
                    null);                          /* orderBy */
            return cursor.moveToFirst(); // True si el cursor tiene al menos una fila (el cliente existe)
        } catch (Exception e) {
            // e.printStackTrace(); // Opcional: loguear error
            return false; // Asumir que no existe en caso de error
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public boolean exists() {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(TABLE, null,
                SEL_PK,
                new String[]{String.valueOf(idReceta)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() throws SQLException {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        if (exists()) {
            throw new SQLException("Receta ya existe: " + idReceta);
        }
        // Validar existencia de Doctor
        if (!doctorExists(this.idDoctor)) {
            throw new SQLException("Doctor inexistente: " + this.idDoctor + ". No se puede crear la receta.");
        }
        // Validar existencia de Cliente (si se proporciona DUI)
        if (!clienteExists(this.dui)) { // clienteExists maneja internamente DUI nulo/vacío
            throw new SQLException("Cliente con DUI " + this.dui + " inexistente. No se puede crear la receta.");
        }

        return dbHelper.getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() throws SQLException {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        if (!exists()) {
            throw new SQLException("No existe Receta con ID " + idReceta + " para actualizar.");
        }
        // Validar que el nuevo idDoctor (si se cambió) exista
        if (!doctorExists(this.idDoctor)) {
            throw new SQLException("Doctor inexistente: " + this.idDoctor + ". No se puede actualizar la receta.");
        }
        // Validar que el nuevo DUI (si se cambió y no es nulo/vacío) exista
        if (!clienteExists(this.dui)) { // clienteExists maneja internamente DUI nulo/vacío
            throw new SQLException("Cliente con DUI " + this.dui + " inexistente. No se puede actualizar la receta.");
        }

        return dbHelper.getWritableDatabase()
                .update(TABLE, valores,
                        SEL_PK,
                        new String[]{String.valueOf(idReceta)});
    }

    @Override
    public long delete() throws SQLException {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        if (!exists()) {
            throw new SQLException("No existe Receta con ID " + idReceta + " para eliminar.");
        }
        if (DetalleReceta.countByReceta(idReceta) > 0) {
            throw new SQLException(
                    "No se puede eliminar Receta con ID " + idReceta + " porque tiene detalles asociados."
            );
        }
        return dbHelper.getWritableDatabase()
                .delete(TABLE,
                        SEL_PK,
                        new String[]{String.valueOf(idReceta)});
    }

    /* Consultas estáticas */
    public static Receta getByPk(int idReceta) {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        Receta[] arr = getByQuery(SEL_PK,
                new String[]{String.valueOf(idReceta)});
        return arr.length > 0 ? arr[0] : null;
    }

    public static Receta[] getAll() {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
        return getByQuery(null, null);
    }

    public static Receta[] getByQuery(String sel, String[] args) {
        if (dbHelper == null) throw new IllegalStateException("Model.dbHelper no inicializado.");
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