package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

// Se añade herencia de Model para los métodos CRUD,
// pero no se tocan los getters/setters ni esValido()
public class DetalleReceta extends Model<DetalleReceta> {
    // — Campos originales
    private int    idDetReceta;
    private int    idReceta;
    private String dosis;

    // — Metadatos para el mini-ORM
    public static final String TABLE    = "detalleReceta";
    public static final String[] FIELDS = {"idDetReceta","idReceta","dosis"};
    public static final String SEL_PK   = "idDetReceta = ?";

    // — Constructor original
    public DetalleReceta(int idDetReceta, int idReceta, String dosis) {
        super();                           // Model inicializa ContentValues
        this.idDetReceta = idDetReceta;
        this.idReceta    = idReceta;
        this.dosis       = dosis;
        fillValues();                      // Sincroniza ContentValues
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

    public boolean esValido() {
        return idDetReceta > 0 && dosis != null && !dosis.trim().isEmpty();
    }

    // — Rellena el ContentValues a partir de los campos
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idDetReceta);
        valores.put(FIELDS[1], idReceta);
        valores.put(FIELDS[2], dosis);
    }

    // — Métodos CRUD añadidos sin alterar la lógica original

    @Override
    public boolean exists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(TABLE, null, SEL_PK,
                new String[]{String.valueOf(idDetReceta)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() {
        // Validación manual de integridad (padre Receta)
        if (Receta.getByPk(idReceta) == null) {
            throw new SQLException("No existe la receta con ID " + idReceta);
        }
        if (exists()) {
            throw new SQLException("DetalleReceta duplicado: " + idDetReceta);
        }
        return dbHelper.getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() {
        if (!exists()) {
            throw new SQLException("No existe DetalleReceta con ID " + idDetReceta);
        }
        return dbHelper.getWritableDatabase()
                .update(TABLE, valores, SEL_PK,
                        new String[]{String.valueOf(idDetReceta)});
    }

    @Override
    public long delete() {
        if (!exists()) {
            throw new SQLException("No existe DetalleReceta con ID " + idDetReceta);
        }
        return dbHelper.getWritableDatabase()
                .delete(TABLE, SEL_PK,
                        new String[]{String.valueOf(idDetReceta)});
    }

    /* — Consultas de conveniencia — */

    public static DetalleReceta getByPk(int idDetReceta) {
        DetalleReceta[] arr = getByQuery(SEL_PK,
                new String[]{String.valueOf(idDetReceta)});
        return arr.length > 0 ? arr[0] : null;
    }

    // arreglo de detalles asociados a una receta
    public static DetalleReceta[] getByReceta(int idReceta) {
        return getByQuery("idReceta = ?",
                new String[]{String.valueOf(idReceta)});
    }

    // número de detalles existentes para esa receta
    public static long countByReceta(int idReceta) {
        return DatabaseUtils.queryNumEntries(
                dbHelper.getReadableDatabase(),
                TABLE,
                "idReceta = ?",
                new String[]{String.valueOf(idReceta)}
        );
    }

    public static DetalleReceta[] getByQuery(String sel, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DetalleReceta> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) {
                list.add(modelFromCursor(c));
            }
        }
        return list.toArray(new DetalleReceta[0]);
    }

    private static DetalleReceta modelFromCursor(Cursor c) {
        return new DetalleReceta(
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[0])),
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[1])),
                c.getString(c.getColumnIndexOrThrow(FIELDS[2]))
        );
    }
}
