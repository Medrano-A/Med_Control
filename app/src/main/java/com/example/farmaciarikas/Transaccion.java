package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Transaccion extends Model<Transaccion> {
    // Campos de la entidad
    private int    idTransaccion;
    private String dui;
    private int    idUsuario;
    private int    idLocal;
    private String fecha;    // yyyy-MM-dd
    private double total;
    private String tipo;

    // Metadatos mini-ORM
    public static final String TABLE    = "TRANSACCION";
    public static final String[] FIELDS = {
            "IDTRANSACCION", "DUI", "IDUSUARIO", "ID_LOCAL", "FECHA", "TOTAL", "TIPO"
    };
    public static final String SEL_PK   = "IDTRANSACCION = ?";

    /**
     * Constructor completo, usado al leer de BD (incluye el total).
     */
    public Transaccion(int idTransaccion,
                       String dui,
                       int idUsuario,
                       int idLocal,
                       String fecha,
                       double total,
                       String tipo) {
        super();
        this.idTransaccion = idTransaccion;
        this.dui            = dui;
        this.idUsuario      = idUsuario;
        this.idLocal        = idLocal;
        this.fecha          = fecha;
        this.total          = total;
        this.tipo           = tipo;
        fillValues();
    }

    /**
     * Constructor para creación inicial; total arranca en 0.
     */
    public Transaccion(int idTransaccion,
                       String dui,
                       int idUsuario,
                       int idLocal,
                       String fecha,
                       String tipo) {
        super();
        this.idTransaccion = idTransaccion;
        this.dui            = dui;
        this.idUsuario      = idUsuario;
        this.idLocal        = idLocal;
        this.fecha          = fecha;
        this.total          = 0;
        this.tipo           = tipo;
        fillValues();
    }

    // Getters / setters
    public int getIdTransaccion()         { return idTransaccion; }
    public void setIdTransaccion(int id)  { idTransaccion = id; fillValues(); }
    public String getDui()               { return dui; }
    public void setDui(String d)         { dui = d; fillValues(); }
    public int getIdUsuario()            { return idUsuario; }
    public void setIdUsuario(int u)      { idUsuario = u; fillValues(); }
    public int getIdLocal()              { return idLocal; }
    public void setIdLocal(int l)        { idLocal = l; fillValues(); }
    public String getFecha()             { return fecha; }
    public void setFecha(String f)       { fecha = f; fillValues(); }
    public double getTotal()             { return total; }
    public void setTotal(double t)       { total = t; fillValues(); }
    public String getTipo()              { return tipo; }
    public void setTipo(String tp)       { tipo = tp; fillValues(); }

    // Rellena ContentValues
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idTransaccion);
        valores.put(FIELDS[1], dui);
        valores.put(FIELDS[2], idUsuario);
        valores.put(FIELDS[3], idLocal);
        valores.put(FIELDS[4], fecha);
        valores.put(FIELDS[5], total);
        valores.put(FIELDS[6], tipo);
    }

    /* Verifica que la tabla exista */
    private void ensureTableExists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE + "'",
                null
        );
        boolean exists = c.moveToFirst();
        c.close();
        if (!exists) throw new SQLException("Tabla '" + TABLE + "' no existe");
    }

    @Override
    public boolean exists() {
        ensureTableExists();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(
                TABLE, null,
                SEL_PK, new String[]{String.valueOf(idTransaccion)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() {
        ensureTableExists();
        if (exists()) {
            throw new SQLException("Transaccion duplicada: " + idTransaccion);
        }
        // Integridad manual sin ORM
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (!db.query("cliente", new String[]{"dui"}, "dui=?", new String[]{dui}, null, null, null).moveToFirst())
            throw new SQLException("Cliente inexistente: " + dui);
        if (!db.query("User", new String[]{"id_usuario"}, "id_usuario=?", new String[]{String.valueOf(idUsuario)}, null, null, null).moveToFirst())
            throw new SQLException("Usuario inexistente: " + idUsuario);
        if (!db.query("local", new String[]{"idLocal"}, "idLocal=?", new String[]{String.valueOf(idLocal)}, null, null, null).moveToFirst())
            throw new SQLException("Local inexistente: " + idLocal);

        return dbHelper.getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() {
        ensureTableExists();
        if (!exists()) {
            throw new SQLException("No existe Transaccion con ID " + idTransaccion);
        }
        // Validaciones idénticas a insert
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (!db.query("cliente", new String[]{"dui"}, "dui=?", new String[]{dui}, null, null, null).moveToFirst())
            throw new SQLException("Cliente inexistente: " + dui);
        if (!db.query("User", new String[]{"id_usuario"}, "id_usuario=?", new String[]{String.valueOf(idUsuario)}, null, null, null).moveToFirst())
            throw new SQLException("Usuario inexistente: " + idUsuario);
        if (!db.query("local", new String[]{"idLocal"}, "idLocal=?", new String[]{String.valueOf(idLocal)}, null, null, null).moveToFirst())
            throw new SQLException("Local inexistente: " + idLocal);

        return dbHelper.getWritableDatabase()
                .update(TABLE, valores, SEL_PK,
                        new String[]{String.valueOf(idTransaccion)});
    }

    @Override
    public long delete() {
        ensureTableExists();
        // No borrar si hay detalles asociados
        if (DetalleTransaccion.countByTransaccion(idTransaccion) > 0) {
            throw new SQLException("No se puede eliminar: existen detalles asociados");
        }
        return dbHelper.getWritableDatabase()
                .delete(TABLE, SEL_PK,
                        new String[]{String.valueOf(idTransaccion)});
    }

    /* Consultas estáticas */
    public static Transaccion getByPk(int id) {
        List<Transaccion> arr = getByQuery(SEL_PK, new String[]{String.valueOf(id)});
        return arr.isEmpty() ? null : arr.get(0);
    }

    public static Transaccion[] getAll() {
        List<Transaccion> list = getByQuery(null, null);
        return list.toArray(new Transaccion[0]);
    }

    public static List<Transaccion> getByQuery(String sel, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Transaccion> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) {
                list.add(modelFromCursor(c));
            }
        }
        return list;
    }

    private static Transaccion modelFromCursor(Cursor c) {
        return new Transaccion(
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[0])), // IDTRANSACCION
                c.getString(c.getColumnIndexOrThrow(FIELDS[1])), // DUI
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[2])), // IDUSUARIO
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[3])), // ID_LOCAL
                c.getString(c.getColumnIndexOrThrow(FIELDS[4])), // FECHA
                c.getDouble(c.getColumnIndexOrThrow(FIELDS[5])), // TOTAL
                c.getString(c.getColumnIndexOrThrow(FIELDS[6]))  // TIPO
        );
    }

    public void recalculateTotal() {
        // 1) sumar subtotales de todos sus detalles
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT IFNULL(SUM(SUBTOTAL),0) FROM DETALLETRANSACCION WHERE IDTRANSACCION = ?",
                new String[]{ String.valueOf(idTransaccion) }
        );
        double suma = 0;
        if (c.moveToFirst()) suma = c.getDouble(0);
        c.close();

        // 2) actualizar campo local y BD
        this.total = suma;
        fillValues();  // vuelve a poblar ContentValues con el nuevo total
        dbHelper.getWritableDatabase()
                .update(TABLE, valores, SEL_PK, new String[]{ String.valueOf(idTransaccion) });
    }
}
