package com.example.farmaciarikas;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Transaccion extends Model<Transaccion> {
    // Campos de la entidad
    private int     idTransaccion;
    private String  dui;            // Puede ser null o vacío
    private String  idUsuario;      // FK a User.id_usuario (CHAR(2)), puede ser null o vacío
    private Integer idLocal;        // FK a Local.idLocal (INTEGER), puede ser null
    private String  fecha;          // yyyy-MM-dd
    private double  total;
    private String  tipo;

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
                       String idUsuario, // Cambiado a String
                       Integer idLocal,   // Cambiado a Integer
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
                       String idUsuario, // Cambiado a String
                       Integer idLocal,   // Cambiado a Integer
                       String fecha,
                       String tipo) {
        super();
        this.idTransaccion = idTransaccion;
        this.dui            = dui;
        this.idUsuario      = idUsuario;
        this.idLocal        = idLocal;
        this.fecha          = fecha;
        this.total          = 0; // Total inicial
        this.tipo           = tipo;
        fillValues();
    }

    // Getters / setters
    public int getIdTransaccion()             { return idTransaccion; }
    public void setIdTransaccion(int id)      { idTransaccion = id; fillValues(); }
    public String getDui()                   { return dui; }
    public void setDui(String d)             { dui = d; fillValues(); }
    public String getIdUsuario()               { return idUsuario; } // Cambiado a String
    public void setIdUsuario(String u)         { idUsuario = u; fillValues(); } // Cambiado a String
    public Integer getIdLocal()                { return idLocal; } // Cambiado a Integer
    public void setIdLocal(Integer l)          { idLocal = l; fillValues(); } // Cambiado a Integer
    public String getFecha()                 { return fecha; }
    public void setFecha(String f)           { fecha = f; fillValues(); }
    public double getTotal()                 { return total; }
    public void setTotal(double t)           { total = t; fillValues(); }
    public String getTipo()                  { return tipo; }
    public void setTipo(String tp)           { tipo = tp; fillValues(); }

    // Rellena ContentValues
    private void fillValues() {
        valores.clear();
        valores.put(FIELDS[0], idTransaccion);
        valores.put(FIELDS[1], (dui != null && dui.isEmpty()) ? null : dui); // Guardar null si es cadena vacía
        valores.put(FIELDS[2], (idUsuario != null && idUsuario.isEmpty()) ? null : idUsuario); // Guardar null si es cadena vacía
        valores.put(FIELDS[3], idLocal); // Integer puede ser null directamente
        valores.put(FIELDS[4], fecha);
        valores.put(FIELDS[5], total);
        valores.put(FIELDS[6], tipo);
    }

    private void ensureDbHelper() {
        if (dbHelper == null) {
            throw new IllegalStateException("Model.dbHelper no ha sido inicializado. Llama a Model.init() primero.");
        }
    }

    // --- Métodos de validación de FK ---
    private boolean clienteExists(String duiCliente) {
        ensureDbHelper();
        if (duiCliente == null || duiCliente.trim().isEmpty()) {
            return true; // DUI opcional, si no se da, es válido
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query("cliente", new String[]{"dui"}, "dui = ?", new String[]{duiCliente}, null, null, null)) {
            return c.moveToFirst();
        } catch (Exception e) {
            // Log.e("Transaccion", "Error verificando cliente", e); // Opcional: Loguear
            return false; // En caso de error, asumir que no existe
        }
    }

    private boolean usuarioExists(String usuarioId) {
        ensureDbHelper();
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            return true; // ID Usuario opcional, si no se da, es válido
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // User.id_usuario es CHAR(2)
        try (Cursor c = db.query("User", new String[]{"id_usuario"}, "id_usuario = ?", new String[]{usuarioId}, null, null, null)) {
            return c.moveToFirst();
        } catch (Exception e) {
            // Log.e("Transaccion", "Error verificando usuario", e); // Opcional: Loguear
            return false;
        }
    }

    private boolean localExists(Integer localId) {
        ensureDbHelper();
        if (localId == null || localId == 0) { // Asumir 0 como no especificado si se usa int primitivo en algún lado
            return true; // ID Local opcional, si no se da, es válido
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query("local", new String[]{"idLocal"}, "idLocal = ?", new String[]{String.valueOf(localId)}, null, null, null)) {
            return c.moveToFirst();
        } catch (Exception e) {
            // Log.e("Transaccion", "Error verificando local", e); // Opcional: Loguear
            return false;
        }
    }
    // --- Fin Métodos de validación de FK ---


    @Override
    public boolean exists() {
        ensureDbHelper();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor c = db.query(
                TABLE, null,
                SEL_PK, new String[]{String.valueOf(idTransaccion)},
                null, null, null)) {
            return c.moveToFirst();
        }
    }

    @Override
    public long insert() throws SQLException {
        ensureDbHelper();
        if (exists()) {
            throw new SQLException("Transaccion duplicada: " + idTransaccion);
        }

        // Validaciones de FK
        if (!clienteExists(this.dui)) {
            throw new SQLException("Cliente inexistente: " + this.dui);
        }
        if (!usuarioExists(this.idUsuario)) {
            throw new SQLException("Usuario inexistente: " + this.idUsuario);
        }
        if (!localExists(this.idLocal)) {
            throw new SQLException("Local inexistente: " + this.idLocal);
        }

        return dbHelper.getWritableDatabase()
                .insertOrThrow(TABLE, null, valores);
    }

    @Override
    public long update() throws SQLException {
        ensureDbHelper();
        if (!exists()) {
            throw new SQLException("No existe Transaccion con ID " + idTransaccion + " para actualizar.");
        }

        // Validaciones de FK
        if (!clienteExists(this.dui)) {
            throw new SQLException("Cliente inexistente: " + this.dui);
        }
        if (!usuarioExists(this.idUsuario)) {
            throw new SQLException("Usuario inexistente: " + this.idUsuario);
        }
        if (!localExists(this.idLocal)) {
            throw new SQLException("Local inexistente: " + this.idLocal);
        }

        return dbHelper.getWritableDatabase()
                .update(TABLE, valores, SEL_PK,
                        new String[]{String.valueOf(idTransaccion)});
    }

    @Override
    public long delete() throws SQLException {
        ensureDbHelper();
        if (!exists()) { // Primero verificar si la transacción existe para evitar error en countByTransaccion
            throw new SQLException("No existe Transaccion con ID " + idTransaccion + " para eliminar.");
        }
        // No borrar si hay detalles asociados
        if (DetalleTransaccion.countByTransaccion(idTransaccion) > 0) {
            throw new SQLException("No se puede eliminar Transaccion con ID " + idTransaccion + ": existen detalles asociados.");
        }
        return dbHelper.getWritableDatabase()
                .delete(TABLE, SEL_PK,
                        new String[]{String.valueOf(idTransaccion)});
    }

    /* Consultas estáticas */
    public static Transaccion getByPk(int id) {
        ensureDbHelper_static(); // Necesario para métodos estáticos
        List<Transaccion> arr = getByQuery(SEL_PK, new String[]{String.valueOf(id)});
        return arr.isEmpty() ? null : arr.get(0);
    }

    public static Transaccion[] getAll() {
        ensureDbHelper_static();
        List<Transaccion> list = getByQuery(null, null);
        return list.toArray(new Transaccion[0]);
    }

    public static List<Transaccion> getByQuery(String sel, String[] args) {
        ensureDbHelper_static();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Transaccion> list = new ArrayList<>();
        try (Cursor c = db.query(TABLE, null, sel, args, null, null, null)) {
            while (c.moveToNext()) {
                list.add(modelFromCursor(c));
            }
        }
        return list;
    }

    private static void ensureDbHelper_static() { // Método helper para estáticos
        if (dbHelper == null) {
            throw new IllegalStateException("Model.dbHelper no ha sido inicializado. Llama a Model.init() primero.");
        }
    }

    private static Transaccion modelFromCursor(Cursor c) {
        // Leer idLocal como Integer para manejar NULLs correctamente desde la BD
        Integer idLocalVal = null;
        int idLocalColumnIndex = c.getColumnIndex(FIELDS[3]); // ID_LOCAL
        if (!c.isNull(idLocalColumnIndex)) {
            idLocalVal = c.getInt(idLocalColumnIndex);
        }

        return new Transaccion(
                c.getInt   (c.getColumnIndexOrThrow(FIELDS[0])), // IDTRANSACCION
                c.getString(c.getColumnIndexOrThrow(FIELDS[1])), // DUI
                c.getString(c.getColumnIndexOrThrow(FIELDS[2])), // IDUSUARIO (leído como String)
                idLocalVal,                                      // ID_LOCAL (leído como Integer)
                c.getString(c.getColumnIndexOrThrow(FIELDS[4])), // FECHA
                c.getDouble(c.getColumnIndexOrThrow(FIELDS[5])), // TOTAL
                c.getString(c.getColumnIndexOrThrow(FIELDS[6]))  // TIPO
        );
    }

    public void recalculateTotal() {
        ensureDbHelper();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Usar readable para query
        Cursor c = null;
        double suma = 0;
        try {
            c = db.rawQuery(
                    "SELECT IFNULL(SUM(SUBTOTAL),0) FROM DETALLETRANSACCION WHERE IDTRANSACCION = ?",
                    new String[]{ String.valueOf(idTransaccion) }
            );
            if (c.moveToFirst()) suma = c.getDouble(0);
        } finally {
            if (c != null) c.close();
        }

        this.total = suma;
        fillValues();  // vuelve a poblar ContentValues con el nuevo total

        // Usar writable para update
        dbHelper.getWritableDatabase()
                .update(TABLE, valores, SEL_PK, new String[]{ String.valueOf(idTransaccion) });
    }

    // Eliminé ensureTableExists() porque la creación de la tabla debe ser manejada por SQLiteOpenHelper.
    // Si la tabla no existe, las operaciones fallarán con una excepción de SQLite, lo cual es informativo.
}