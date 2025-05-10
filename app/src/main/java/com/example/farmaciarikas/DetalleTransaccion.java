package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import java.util.ArrayList;
import java.util.List;

public class DetalleTransaccion extends Model<DetalleTransaccion> {
    // Campos de la entidad
    private int    idDetalle;
    private int    idTransaccion;
    private int    cantidad;
    private double subtotal;
    private double precioUnitario;

    // Metadatos mini-ORM
    public static final String TABLE    = "DETALLETRANSACCION";
    public static final String[] FIELDS = {
            "ID_DETALLE", "IDTRANSACCION", "CANTIDAD", "SUBTOTAL", "PRECIOUNITARIO"
    };
    public static final String SEL_PK   = "ID_DETALLE = ?";

    // Constructor
    public DetalleTransaccion(int idDetalle,
                              int idTransaccion,
                              int cantidad,
                              double subtotal,
                              double precioUnitario) {
        super();
        this.idDetalle      = idDetalle;
        this.idTransaccion  = idTransaccion;
        this.cantidad       = cantidad;
        this.subtotal       = subtotal;
        this.precioUnitario = precioUnitario;
        fillValues();
    }

    // Getters / setters
    public int getIdDetalle()            { return idDetalle; }
    public void setIdDetalle(int id)     { idDetalle = id; fillValues(); }
    public int getIdTransaccion()        { return idTransaccion; }
    public void setIdTransaccion(int id) { idTransaccion = id; fillValues(); }
    public int getCantidad()             { return cantidad; }
    public void setCantidad(int c)       { cantidad = c; fillValues(); }
    public double getSubtotal()          { return subtotal; }
    public void setSubtotal(double s)    { subtotal = s; fillValues(); }
    public double getPrecioUnitario()    { return precioUnitario; }
    public void setPrecioUnitario(double p) { precioUnitario = p; fillValues(); }

    // Sincronización de ContentValues
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idDetalle);
        valores.put(FIELDS[1], idTransaccion);
        valores.put(FIELDS[2], cantidad);
        valores.put(FIELDS[3], subtotal);
        valores.put(FIELDS[4], precioUnitario);
    }

    /* ---------- CRUD ---------- */

    @Override
    public boolean exists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(TABLE, null,
                SEL_PK, new String[]{String.valueOf(idDetalle)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() {
        if (exists()) {
            throw new SQLException("DetalleTransaccion duplicado: " + idDetalle);
        }
        Transaccion t = Transaccion.getByPk(idTransaccion);
        if (t == null) {
            throw new SQLException("Transaccion inexistente: " + idTransaccion);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            long newId = db.insertOrThrow(TABLE, null, valores);
            // Recalcular total de la transacción
            t.recalculateTotal();
            db.setTransactionSuccessful();
            return newId;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public long update() {
        if (!exists()) {
            throw new SQLException("No existe DetalleTransaccion con ID " + idDetalle);
        }
        Transaccion t = Transaccion.getByPk(idTransaccion);
        if (t == null) {
            throw new SQLException("Transaccion inexistente: " + idTransaccion);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            long count = db.update(TABLE, valores,
                    SEL_PK, new String[]{String.valueOf(idDetalle)});
            t.recalculateTotal();
            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public long delete() {
        if (!exists()) {
            throw new SQLException("No existe DetalleTransaccion con ID " + idDetalle);
        }
        Transaccion t = Transaccion.getByPk(idTransaccion);
        if (t == null) {
            throw new SQLException("Transaccion inexistente: " + idTransaccion);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            long count = db.delete(TABLE,
                    SEL_PK, new String[]{String.valueOf(idDetalle)});
            t.recalculateTotal();
            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    /* ---------- Consultas estáticas ---------- */

    public static DetalleTransaccion getByPk(int idDetalle) {
        DetalleTransaccion[] arr = getByQuery(SEL_PK,
                new String[]{String.valueOf(idDetalle)});
        return arr.length > 0 ? arr[0] : null;
    }

    public static DetalleTransaccion[] getByTransaccion(int idTransaccion) {
        return getByQuery("IDTRANSACCION = ?",
                new String[]{String.valueOf(idTransaccion)});
    }

    public static long countByTransaccion(int idTransaccion) {
        return DatabaseUtils.queryNumEntries(
                dbHelper.getReadableDatabase(),
                TABLE,
                "IDTRANSACCION = ?",
                new String[]{String.valueOf(idTransaccion)}
        );
    }

    public static DetalleTransaccion[] getAll() {
        return getByQuery(null, null);
    }

    public static DetalleTransaccion[] getByQuery(String sel, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DetalleTransaccion> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) {
                list.add(modelFromCursor(c));
            }
        }
        return list.toArray(new DetalleTransaccion[0]);
    }

    private static DetalleTransaccion modelFromCursor(Cursor c) {
        return new DetalleTransaccion(
                c.getInt(c.getColumnIndexOrThrow(FIELDS[0])),  // ID_DETALLE
                c.getInt(c.getColumnIndexOrThrow(FIELDS[1])),  // IDTRANSACCION
                c.getInt(c.getColumnIndexOrThrow(FIELDS[2])),  // CANTIDAD
                c.getDouble(c.getColumnIndexOrThrow(FIELDS[3])), // SUBTOTAL
                c.getDouble(c.getColumnIndexOrThrow(FIELDS[4]))  // PRECIOUNITARIO
        );
    }
}
