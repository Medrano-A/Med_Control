package com.example.farmaciarikas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ControlDBFarmacia {

    /**-------------------------------CAMPOS DE ENTIDADES----------------------------------------------**/
    private static final String[] camposDoctor = new String[]
            {"idDoctor", "nombreDoctor", "especialidad", "jvpm", "telefonoDoctor", "correoDoctor"};
    private static final String[] camposMedicamento = new String[]
            {"idMedicamento", "codElemento", "idLaboratorio", "viaDeAdministracion", "formaFarmaceutica"};
    private static final String[] camposLocal = new String[]
            {"idLocal", "idUbicacion", "nombreLocal", "tipoLocal", "telefonoLocal"};
    private static final String[] camposElemento = new String[]{
            "codElemento", "nombre", "cantidad", "descripcion", "precioUni", "unidades"
    };

    private static final String[] camposLaboratorio = new String[]{
            "idLaboratorio", "nombre", "tipo", "telefono"
    };
    private static final String[] camposUbicacion = new String[]{
            "idUbicacion", "idDistrito", "idMarca", "detalle"
    };

    private static final String[] camposStock = new String[]{
            "idStock", "codElemento", "idLocal", "cantidad", "fechaVencimiento"
    };

    private static final String[] camposArticulo = new String[] {
            "idArticulo", "idDistribuidor", "nombreArticulo", "clasificacion"
    };
    private static final String[] camposMarca = new String[]{
            "idMarca", "nombre"
    };
    private static final String[] camposDepartamento = new String[]{
            "idDepartamento", "nombre"
    };
    private static final String[] camposMunicipio = new String[]{
            "idMunicipio", "idDepartamento", "nombre"
    };
    private static final String[] camposDistrito = new String[]{
            "idDistrito", "idMunicipio", "nombre"
    };

    private final Context context;
    public DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public ControlDBFarmacia(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "farmacia.s3db";
        private static final int VERSION = 2;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE cliente (dui CHAR(10) NOT NULL PRIMARY KEY, nombre VARCHAR(30) NOT NULL, apellido VARCHAR(50) NOT NULL, telefono CHAR(8) NOT NULL, correo VARCHAR(30));");
                db.execSQL("CREATE TABLE detalleReceta (idDetReceta INTEGER NOT NULL PRIMARY KEY, idReceta INTEGER, dosis VARCHAR(50) NOT NULL);");
                db.execSQL("CREATE TABLE distribuidor (idDistribuidor INTEGER NOT NULL PRIMARY KEY, idMarca INTEGER, nombre VARCHAR(30) NOT NULL, telefono CHAR(8) NOT NULL, nit CHAR(10) NOT NULL, correo VARCHAR(30));");


                /*TABLA DOCTOR*/
                db.execSQL("CREATE TABLE doctor" +
                        "(idDoctor INTEGER NOT NULL PRIMARY KEY," +
                        "nombreDoctor VARCHAR(30)," +
                        "especialidad VARCHAR(30)," +
                        "jvpm VARCHAR(4)," +
                        "telefonoDoctor VARCHAR(8)," +
                        "correoDoctor VARCHAR(30));");

                /*TABLA ELEMENTO*/

                db.execSQL("CREATE TABLE elemento (\n" +
                        "    codElemento INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    nombre VARCHAR(30) NOT NULL,\n" +
                        "    cantidad INTEGER,\n" +
                        "    descripcion TEXT,\n" +
                        "    precioUni REAL,\n" +
                        "    unidades VARCHAR(10)\n" +
                        ");");

                /*TABLA MEDICAMENTO*/

                db.execSQL("CREATE TABLE medicamento(idMedicamento VARCHAR(4) NOT NULL PRIMARY KEY," +
                        "codElemento INTEGER NOT NULL," +
                        "idLaboratorio INTEGER NOT NULL," +
                        "viaDeAdministracion VARCHAR(32)," +
                        "formaFarmaceutica VARCHAR(32));");


                /*TABLA LOCAL*/

                db.execSQL("CREATE TABLE local(idLocal INTEGER  NOT NULL PRIMARY KEY ," +
                        "idUbicacion INTEGER  NOT NULL ," +
                        "nombreLocal VARCHAR(30) ," +
                        "tipoLocal VARCHAR(30)," +
                        "telefonoLocal VARCHAR(8));");

                /*TABLA USUARIO*/

                db.execSQL("CREATE TABLE User (" +
                        "id_usuario CHAR(2) NOT NULL," +
                        " nom_usuario VARCHAR2(30) NOT NULL," +
                        " clave VARCHAR(10) NOT NULL," +
                        "CONSTRAINT PK_USUARIO PRIMARY KEY (id_usuario));");

                /*TABLA OPCIONCRUD*/

                db.execSQL("CREATE TABLE OpcionCrud(" +
                        "id_opcion_crud VARCHAR(3) NOT NULL PRIMARY KEY," +
                        "des_opcion VARCHAR2(30) NOT NULL," +
                        "num_crud INTEGER NOT NULL);");

                /*TABLA ACCESOUSUARIO*/
                db.execSQL("CREATE TABLE AccesoUsuario (" +
                        "id_acceso INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " id_usuario CHAR(2) NOT NULL," +
                        " id_opcion_crud INTEGER NOT NULL, " +
                        "FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE, " +
                        "FOREIGN KEY(id_opcion_crud) REFERENCES OpcionCrud(id_opcion_crud) ON DELETE CASCADE)");

                //gd21001 tablas
                db.execSQL("CREATE TABLE Laboratorio (idLaboratorio INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL, tipo CHAR(30) NOT NULL, telefono VARCHAR2(8) NOT NULL);");
                db.execSQL("CREATE TABLE Departamento (idDepartamento INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Municipio (idMunicipio INTEGER NOT NULL PRIMARY KEY, idDepartamento INTEGER NOT NULL, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Distrito (idDistrito INTEGER NOT NULL PRIMARY KEY, idMunicipio INTEGER NOT NULL, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Marca (idMarca INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL);");

                //Triggers GD21001
                db.execSQL("CREATE TRIGGER borrar_municipios_al_borrar_departamento " +
                        "AFTER DELETE ON Departamento " +
                        "FOR EACH ROW BEGIN " +
                        "DELETE FROM Municipio WHERE idDepartamento = OLD.idDepartamento; " +
                        "END;");

                db.execSQL("CREATE TRIGGER borrar_distritos_al_borrar_municipio " +
                        "AFTER DELETE ON Municipio " +
                        "FOR EACH ROW BEGIN " +
                        "DELETE FROM Distrito WHERE idMunicipio = OLD.idMunicipio; " +
                        "END;");


                db.execSQL("CREATE TABLE ubicacion(idUbicacion INTEGER NOT NULL PRIMARY KEY," +
                        " idDistrito INTEGER NOT NULL," +
                        " idMarca INTEGER NOT NULL," +
                        "detalle TEXT NOT NULL)");
                //TABLA STOCK
                db.execSQL("CREATE TABLE stock(idStock INTEGER NOT NULL PRIMARY KEY," +
                        " codElemento INTEGER NOT NULL," +
                        " idlocal INTEGER NOT NULL," +
                        " cantidad INTEGER NOT NULL," +
                        " fechavencimiento TEXT NOT NULL)");
                //TABLA ARTICULO
                db.execSQL("CREATE TABLE articulo(idArticulo INTEGER NOT NULL PRIMARY KEY," +
                        " idDistribuidor INTEGER NOT NULL," +
                        " nombreArticulo TEXT NOT NULL," +
                        " clasificacion TEXT NOT NULL)");
                // Crear tabla RECETA
                db.execSQL(
                        "CREATE TABLE RECETA (" +
                                "IDRECETA INTEGER    NOT NULL PRIMARY KEY, " +
                                "DUI       TEXT, " +
                                "IDDOCTOR  INTEGER    NOT NULL, " +
                                "NOMBREPACIENTE TEXT NOT NULL, " +
                                "FECHA     DATE       NOT NULL, " +
                                "EDAD      INTEGER    NOT NULL, " +
                                "OBSERVACIONES TEXT NOT NULL" +
                                ");"
                );

                // Tabla TRANSACCION
                db.execSQL(
                        "CREATE TABLE TRANSACCION (" +
                                "IDTRANSACCION INTEGER NOT NULL PRIMARY KEY, " +
                                "DUI            CHAR(10), " +
                                "IDUSUARIO      INTEGER, " +
                                "ID_LOCAL       INTEGER, " +
                                "FECHA          DATE    NOT NULL, " +
                                "TOTAL          REAL    NOT NULL DEFAULT 0, " +
                                "TIPO           CHAR(30) NOT NULL" +
                                ");"
                );

                // Tabla DETALLETRANSACCION
                db.execSQL(
                        "CREATE TABLE DETALLETRANSACCION (" +
                                "ID_DETALLE     INTEGER NOT NULL PRIMARY KEY, " +
                                "IDTRANSACCION  INTEGER NOT NULL, " +
                                "CANTIDAD       INTEGER NOT NULL, " +
                                "SUBTOTAL       REAL    NOT NULL, " +
                                "PRECIOUNITARIO REAL    NOT NULL" +
                                ");"
                );




            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        }
    }

    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar() {
        // DBHelper.close();
    }

    public SQLiteDatabase getReadableDatabase() {
        if (db == null || !db.isOpen()) {
            abrir();
        }
        return db;
    }

    public SQLiteDatabase getWritableDatabase() {
        if (db == null || !db.isOpen()) {
            abrir();
        }
        return db;
    }
    /*--CRUDS DE MM2108
    /*-----------------------------------------------TABLA ARTICULO-----------------------------------------------------------*/
    public String insertar(Articulo articulo) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues doc = new ContentValues();
        doc.put("idArticulo", articulo.getIdArticulo());
        doc.put("idDistribuidor", articulo.getIdDistribuidor());
        doc.put("nombreArticulo", articulo.getNombreArticulo());
        doc.put("clasificacion", articulo.getClasificacion());

        contador = db.insert("articulo",null,doc);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public Articulo consultarArticulo(int idArticulo) {
        String[] id = {String.valueOf(idArticulo)};
        Cursor cursor = db.query("articulo", camposArticulo, "idArticulo = ?", id, null, null, null);

        if (cursor.moveToFirst()) {
            Articulo articulo = new Articulo();
            articulo.setIdArticulo(cursor.getInt(0));
            articulo.setIdDistribuidor(cursor.getInt(1));
            articulo.setNombreArticulo(cursor.getString(2));
            articulo.setClasificacion(cursor.getString(3));

            return articulo;
        } else {
            return null;
        }
    }
    public String actualizar(Articulo articulo) {
        String[] id = {String.valueOf(articulo.getIdArticulo())};
        ContentValues cv = new ContentValues();
        cv.put("idDistribuidor", articulo.getIdDistribuidor());
        cv.put("nombreArticulo", articulo.getNombreArticulo());
        cv.put("clasificacion", articulo.getClasificacion());
        db.update("articulo", cv, "idArticulo = ?", id);
        return "Registro Actualizado Correctamente";
    }
    public String eliminarArticulo(int idArticulo) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("articulo", "idArticulo='" + idArticulo + "'", null);
        regAfectados += contador;
        return regAfectados;
    }






    /*-----------------------------------------------TABLA UBICACION----------------------------------------------------------*/
    public String insertar(Ubicacion ubicacion) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues doc = new ContentValues();
        doc.put("idUbicacion", ubicacion.getIdUbicacion());
        doc.put("idDistrito", ubicacion.getIdDistrito());
        doc.put("idMarca", ubicacion.getIdMarca());
        doc.put("detalle", ubicacion.getDetalle());

        contador = db.insert("ubicacion", null, doc);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public Ubicacion consultarUbicacion(int idUbicacion) {
        String[] id = {String.valueOf(idUbicacion)};
        Cursor cursor = db.query("ubicacion", camposUbicacion, "idUbicacion = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setIdUbicacion(cursor.getInt(0));
            ubicacion.setIdDistrito(cursor.getInt(1));
            ubicacion.setIdMarca(cursor.getInt(2));
            ubicacion.setDetalle(cursor.getString(3));

            return ubicacion;
        } else {
            return null;
        }
    }

    public String actualizar(Ubicacion ubicacion) {
        String[] id = {String.valueOf(ubicacion.getIdUbicacion())};
        ContentValues cv = new ContentValues();
        cv.put("idDistrito", ubicacion.getIdDistrito());
        cv.put("idMarca", ubicacion.getIdMarca());
        cv.put("detalle", ubicacion.getDetalle());
        db.update("ubicacion", cv, "idUbicacion = ?", id);
        return "Registro Actualizado Correctamente";
    }

    public String eliminar(Ubicacion ubicacion) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        String idUbicacion = String.valueOf(ubicacion.getIdUbicacion());
        contador += db.delete("ubicacion", "idUbicacion='" + idUbicacion + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    /*-----------------------------------------------TABLA STOCKS----------------------------------------------------------*/
    public String insertar(Stock stock) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues doc = new ContentValues();
        doc.put("idStock", stock.getIdStock());
        doc.put("codElemento", stock.getCodElemento());
        doc.put("idlocal", stock.getIdLocal());
        doc.put("cantidad", stock.getCantidad());
        doc.put("fechavencimiento", stock.getFechaVencimiento());

        contador = db.insert("stock", null, doc);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public String eliminar(Stock stock) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        String idStock = String.valueOf(stock.getIdStock());
        contador += db.delete("stock", "idStock='" + idStock + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public Stock consultarStock(int idStock) {
        String[] id = {String.valueOf(idStock)};
        Cursor cursor = db.query("stock", camposStock, "idStock = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Stock stock = new Stock();
            stock.setIdStock(cursor.getInt(0));
            stock.setCodElemento(cursor.getInt(1));
            stock.setIdLocal(cursor.getInt(2));
            ;
            stock.setCantidad(cursor.getInt(3));
            stock.setFechaVencimiento(cursor.getString(4));
            return stock;
        } else {
            return null;
        }
    }

    public String actualizar(Stock stock) {
        String[] id = {String.valueOf(stock.getIdStock())};
        ContentValues cv = new ContentValues();
        cv.put("codElemento", stock.getCodElemento());
        cv.put("idlocal", stock.getIdLocal());
        cv.put("cantidad", stock.getCantidad());
        cv.put("fechavencimiento", stock.getFechaVencimiento());
        db.update("stock", cv, "idStock = ?", id);
        return "Registro Actualizado Correctamente";
    }
    /*TABLAS DE GA21090*/
    /*-----------------------------------------------TABLA DOCTOR----------------------------------------------------------*/
    public String insertar(Doctor doctor) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues doc = new ContentValues();
        doc.put("idDoctor", doctor.getIdDoctor());
        doc.put("nombreDoctor", doctor.getNombreDoctor());
        doc.put("especialidad", doctor.getEspecialidad());
        doc.put("jvpm", doctor.getJvpm());
        doc.put("telefonoDoctor", doctor.getTelefonoDoctor());
        doc.put("correoDoctor", doctor.getCorreoDoctor());
        contador = db.insert("doctor", null, doc);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public String actualizar(Doctor doctor) {

        try {
            if (verificarIntegridad(doctor, 5)) {
                String[] id = {String.valueOf(doctor.getIdDoctor())};
                ContentValues cv = new ContentValues();
                cv.put("nombreDoctor", doctor.getNombreDoctor());
                cv.put("especialidad", doctor.getEspecialidad());
                cv.put("jvpm", doctor.getJvpm());
                cv.put("telefonoDoctor", doctor.getTelefonoDoctor());
                cv.put("correoDoctor", doctor.getCorreoDoctor());
                db.update("doctor", cv, "idDoctor = ?", id);
                return "Registro Actualizado Correctamente";
       /* }else{
            return "Registro con carnet " + doctor.getIdDoctor() + " no existe";
        }*/
            }else{
            Log.e("ActualizarDoctor", "Error: El Doctor con ID " + doctor.getIdDoctor() + " no existe.");
            return "Error no existe el codigo del Doctor";
        }

    }catch (SQLException e){
        Log.e("ActualizarDoctor", "Error en la base de datos: " + e.getMessage());
        return "Error en la base de datos";
    }


    }

    public String eliminar(Doctor doctor) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("doctor", "idDoctor='" + doctor.getIdDoctor() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public Doctor consultarDoctor(int idDoctor) {
        String[] id = {String.valueOf(idDoctor)};
        Cursor cursor = db.query("doctor", camposDoctor, "idDoctor = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Doctor doctor = new Doctor();
            doctor.setIdDoctor(cursor.getInt(0));
            doctor.setNombreDoctor(cursor.getString(1));
            doctor.setEspecialidad(cursor.getString(2));
            doctor.setJvpm(cursor.getString(3));
            doctor.setTelefonoDoctor(cursor.getString(4));
            doctor.setCorreoDoctor(cursor.getString(5));
            return doctor;
        } else {
            return null;
        }
    }

    /*-----------------------------------------------TABLA MEDICAMENTO----------------------------------------------------------*/
    public String insertar(Medicamento medicamento) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        long codElementoGenerado;
        try {
            if (verificarIntegridad(medicamento, 2)) {
                // 1. Insertar en la tabla elemento (sin codElemento)
                ContentValues valoresElemento = new ContentValues();
                valoresElemento.put("nombre", medicamento.getNombre());
                valoresElemento.put("cantidad", medicamento.getCantidad());
                valoresElemento.put("descripcion", medicamento.getDescripcion());
                valoresElemento.put("precioUni", medicamento.getPrecioUni());
                valoresElemento.put("unidades", medicamento.getUnidades());

                codElementoGenerado = db.insert("elemento", null, valoresElemento);
                if (codElementoGenerado == -1) {
                    return " Error al insertar en la tabla 'elemento'.";
                }

                // 2.Insertar un medicamento
                ContentValues medicamentos = new ContentValues();
                medicamentos.put("idMedicamento", medicamento.getIdMedicamento());
                medicamentos.put("codElemento", codElementoGenerado);
                medicamentos.put("idLaboratorio", medicamento.getIdLaboratorio());
                medicamentos.put("viaDeAdministracion", medicamento.getViaDeAdministracion());
                medicamentos.put("formaFarmaceutica", medicamento.getFormaFarmaceutica());
                contador = db.insert("medicamento", null, medicamentos);


                if (contador == -1 || contador == 0) {
                    regInsertados = "Error al Insertar el registro por que es duplicado.";
                } else {
                    regInsertados = regInsertados + contador;
                }
            } else {
                regInsertados = "Error: El Laboratorio con ID " + medicamento.getIdLaboratorio() + " no existe.";
            }

        } catch (SQLException e) {
            regInsertados = "Error en la base de datos: " + e.getMessage();
        }
        return regInsertados;
    }

    public int obtenerCodElementoPorIdMedicamento(String idMedicamento) {
        int codElemento = -1;
        Cursor cursor = db.rawQuery("SELECT codElemento FROM medicamento WHERE idMedicamento = ?", new String[]{idMedicamento});
        if (cursor.moveToFirst()) {
            codElemento = cursor.getInt(0);  // Este es el codElemento relacionado con el medicamento
        }
        cursor.close();
        return codElemento;
    }

    public boolean actualizarElemento(Medicamento medicamento) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", medicamento.getNombre());
        valores.put("cantidad", medicamento.getCantidad());
        valores.put("descripcion", medicamento.getDescripcion());
        valores.put("precioUni", medicamento.getPrecioUni());
        valores.put("unidades", medicamento.getUnidades());

        String where = "codElemento=?";
        String[] whereArgs = {String.valueOf(medicamento.getCodElemento())};

        int filasAfectadas = db.update("elemento", valores, where, whereArgs);
        return filasAfectadas > 0;  // Si se actualizaron filas, es verdadero
    }


    public boolean actualizarMedicamento(Medicamento medicamento) {

        try {
            if (verificarIntegridad(medicamento, 2)&& verificarIntegridad(medicamento, 4)) {
                ContentValues valores = new ContentValues();
                valores.put("idLaboratorio", medicamento.getIdLaboratorio());
                valores.put("viaDeAdministracion", medicamento.getViaDeAdministracion());
                valores.put("formaFarmaceutica", medicamento.getFormaFarmaceutica());

               String where = "idMedicamento=?";
               String [] whereArgs = {medicamento.getIdMedicamento()};
                int filasAfectadas = db.update("medicamento", valores, where, whereArgs);
                return filasAfectadas > 0;  // Si se actualizaron filas, es verdadero

            } else {
                Log.e("ActualizarMedicamento", "Error: El Laboratorio con ID " + medicamento.getIdLaboratorio() + " no existe.");
                return false;
            }
        } catch (SQLException e) {
            Log.e("ActualizarMedicamento", "Error en la base de datos: " + e.getMessage());
            return false;
        }

    }

    public String eliminar(Medicamento medicamento) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("medicamento", "idMedicamento='" + medicamento.getIdMedicamento() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }
    public Medicamento consultarMedicamento(String idMed) {
        db = DBHelper.getReadableDatabase();


        String query = "SELECT m.idMedicamento, m.codElemento, m.idLaboratorio, " +
                "m.viaDeAdministracion, m.formaFarmaceutica, " +
                "e.nombre, e.cantidad, e.descripcion, e.precioUni, e.unidades " +
                "FROM medicamento m INNER JOIN elemento e ON m.codElemento = e.codElemento " +
                "WHERE m.idMedicamento = ?";

        Cursor cursor = db.rawQuery(query, new String[]{idMed});

        Medicamento medicamento = null;

        if (cursor.moveToFirst()) {
            medicamento = new Medicamento();
            medicamento.setIdMedicamento(cursor.getString(0));
            medicamento.setCodElemento(cursor.getInt(1));
            medicamento.setIdLaboratorio(cursor.getInt(2));
            medicamento.setViaDeAdministracion(cursor.getString(3));
            medicamento.setFormaFarmaceutica(cursor.getString(4));

            // Campos heredados de Elemento
            medicamento.setNombre(cursor.getString(5));
            medicamento.setCantidad(cursor.getInt(6));
            medicamento.setDescripcion(cursor.getString(7));
            medicamento.setPrecioUni(cursor.getDouble(8));
            medicamento.setUnidades(cursor.getString(9));
        }

        cursor.close();
        return medicamento;
    }


    /*-----------------------------------------------TABLA LOCAL----------------------------------------------------------*/
    public String insertar(Local local) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        try {
            if (verificarIntegridad(local, 1)) {  // Verifica que idUbicacion exista
                ContentValues loc = new ContentValues();
                loc.put("idLocal", local.getIdLocal());
                loc.put("idUbicacion", local.getIdUbicacion());
                loc.put("nombreLocal", local.getNombreLocal());
                loc.put("tipoLocal", local.getTipoLocal());
                loc.put("telefonoLocal", local.getTelefonoLocal());

                contador = db.insert("local", null, loc);

                if (contador == -1 || contador == 0) {
                    regInsertados = "Error al Insertar el registro. Verificar si el registro es duplicado.";
                } else {
                    regInsertados = regInsertados + contador;
                }
            } else {
                regInsertados = "Error: La ubicación con ID " + local.getIdUbicacion() + " no existe.";
            }
        } catch (SQLException e) {
            regInsertados = "Error en la base de datos: " + e.getMessage();
        }

        return regInsertados;
    }

    public String actualizar(Local local) {

        try {
            if(verificarIntegridad(local,1)&& verificarIntegridad(local, 3) ) {
                String[] id = {String.valueOf(local.getIdLocal())};
                ContentValues cv = new ContentValues();
                cv.put("idUbicacion", local.getIdUbicacion());
                cv.put("nombreLocal", local.getNombreLocal());
                cv.put("tipoLocal", local.getTipoLocal());
                cv.put("telefonoLocal", local.getTelefonoLocal());

                db.update("local", cv, "idLocal = ?", id);
                return "Registro Actualizado Correctamente";
            }else{
                Log.e("ActualizarMedicamento", "Error: El Laboratorio con ID " + local.getIdUbicacion() + " no existe.");
                return "Error no existe el codigo de laboratorio";
            }

        }catch (SQLException e){
            Log.e("ActualizarLocal", "Error en la base de datos: " + e.getMessage());
            return "Error en la base de datos";
        }

    }

    public String eliminar(Local local) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("local", "idLocal='" + local.getIdLocal() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public Local consultarLocal(Integer idLocal) {
        String[] id = {String.valueOf(idLocal)};
        Cursor cursor = db.query("local", camposLocal, "idLocal = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Local local = new Local();
            local.setIdLocal(cursor.getInt(0));
            local.setIdUbicacion(cursor.getInt(1));
            local.setNombreLocal(cursor.getString(2));
            local.setTipoLocal(cursor.getString(3));
            local.setTelefonoLocal(cursor.getString(4));
            return local;
        } else {
            return null;
        }
    }
    /*----GD21001 Tablas----*/
    /*---------------------------------------TABLA DE LABORATORIO------------------------------------------------*/
    public String insertar(Laboratorio l){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;
        ContentValues lab = new ContentValues();
        lab.put("idLaboratorio", l.getIdLaboratorio());
        lab.put("nombre", l.getNombre());
        lab.put("tipo", l.getTipo());
        lab.put("telefono", l.getTelefono());
        cont = db.insert("Laboratorio", null, lab);
        if (cont == -1 || cont == 0){
            regInsert = "Error al insertar el registro, registro ya esta insertado, verificar informacion o si ya existe ID";
        }else{
            regInsert = regInsert + cont;
        }
        return regInsert;
    }
    public String actualizar(Laboratorio l){
        if(verificarIntegridadLab(l, 1)){
            int idLab = l.getIdLaboratorio();
            String[] idLaboratorio = {Integer.toString(idLab)};
            ContentValues cv = new ContentValues();
            cv.put("nombre", l.getNombre());
            cv.put("tipo", l.getTipo());
            cv.put("telefono", l.getTelefono());
            db.update("Laboratorio",cv,"idLaboratorio = ?", idLaboratorio);
            return "Registro actualizado correctamente";
        }else{
            return "Registro con Id " + l.getIdLaboratorio() + "no existe";
        }

    }
    public Laboratorio consultarLab(int idLabo){
        String[] idLaboratorio = {Integer.toString(idLabo)};
        Cursor c = db.query("Laboratorio", camposLaboratorio, "idLaboratorio = ?", idLaboratorio, null, null, null);
        if(c.moveToFirst()){
            Laboratorio lab = new Laboratorio();
            lab.setIdLaboratorio(Integer.parseInt(c.getString(0)));
            lab.setNombre(c.getString(1));
            lab.setTipo(c.getString(2));
            lab.setTelefono(c.getString(3));
            return lab;
        }else{
            return null;
        }
    }
    public String eliminar(Laboratorio l){
        String regAfect = "Filas afectadas = ";
        int cont = 0;
        //verificar la integridad relacionada con el id
        if(verificarIntegridadLab(l, 1)){
            cont += db.delete("Laboratorio", "idLaboratorio = '" + l.getIdLaboratorio() +"'", null);
        }else{
            regAfect = "ID no existe o no se encuentra, Filas afectadas = ";
            regAfect += cont;
            return regAfect;
        }
        regAfect += cont;
        return regAfect;

    }
    /*----MARCA----*/
    public String insertar(Marca m){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;
        ContentValues v = new ContentValues();
        v.put("idMarca", m.getIdMarca());
        v.put("nombre", m.getNombre());
        cont = db.insert("Marca", null, v);
        if(cont == -1 || cont == 0){
            regInsert = "Error al insertar el registro en la base de datos, verificar la insercion";
        }else{
            regInsert=regInsert+cont;
        }
        return regInsert;
    }
    public String actualizar(Marca m){
        if(verificarIntegridadMarca(m, 1)){
            String[] id = {Integer.toString(m.getIdMarca())};
            ContentValues cMarcam = new ContentValues();
            cMarcam.put("nombre", m.getNombre());
            db.update("Marca", cMarcam, "idMarca = ?", id);
            return "Registro actualizado correctamente";
        }else{
            return "Registro no existe o no se encuentra";
        }
    }
    public Marca consultarMarca(int idMarca){
        String[] id = {Integer.toString(idMarca)};
        Cursor c = db.query("Marca", camposMarca, "idMarca = ?", id, null, null, null);
        if(c.moveToFirst()){
            Marca m = new Marca();
            m.setIdMarca(Integer.parseInt(c.getString(0)));
            m.setNombre(c.getString(1));
            return m;
        }else{
            return null;
        }
    }
    public String eliminar(Marca m){
        String regAfect = "Filas afectadas = ";
        int cont = 0;
        //verificar la integridad relacionada con el id
        if(verificarIntegridadMarca(m, 1)){
            cont += db.delete("Marca", "idMarca = '" + m.getIdMarca() +"'", null);
        }else{
            regAfect = "ID no existe o no se encuentra, Filas afectadas = ";
            regAfect += cont;
            return regAfect;
        }
        regAfect += cont;
        return regAfect;
    }
    /*----DEPARTAMENTO----*/
    public String insertar(Departamento d){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;
        ContentValues v = new ContentValues();
        v.put("idDepartamento", d.getIdDepartamento());
        v.put("nombre", d.getNombre());
        cont = db.insert("Departamento", null, v);
        if(cont == -1 || cont == 0){
            regInsert = "Error al insertar el registro en la base de datos, verificar la insercion o Si ya existe el ID";
        }else{
            regInsert=regInsert+cont;
        }
        return regInsert;
    }
    public String actualizar(Departamento d){
        if(verificarIntegridadDpto(d, 1)){
            String[] id = {Integer.toString(d.getIdDepartamento())};
            ContentValues c = new ContentValues();
            c.put("nombre", d.getNombre());
            db.update("Departamento", c, "idDepartamento = ?", id);
            return "Registro actualizado correctamente";
        }else{
            return "Registro con Id " + d.getIdDepartamento() + " no existe";
        }

    }
    public Departamento consultarDpto(int idDpto){
        String[] id = {Integer.toString(idDpto)};
        Cursor c = db.query("Departamento", camposDepartamento, "idDepartamento = ?", id, null, null, null);
        if(c.moveToFirst()){
            Departamento d = new Departamento();
            d.setIdDepartamento(Integer.parseInt(c.getString(0)));
            d.setNombre(c.getString(1));
            return d;
        }else{
            return null;
        }
    }
    public String eliminar(Departamento d){
        String regAfect = "Filas afectadas = ";
        int cont = 0;
        if(verificarIntegridadDpto(d, 1)){
            String[] args = { String.valueOf(d.getIdDepartamento()) };
            cont += db.delete("Departamento", "idDepartamento = ?", args);
        } else {
            regAfect = "ID no existe o no se encuentra, Filas afectadas = ";
            regAfect += cont;
            return regAfect;
        }
        regAfect += cont;
        return regAfect;
    }
    /*----MUNICIPIO----*/
    public String insertar(Municipio m){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;

        if(verificarIntegridadMncip(m, 1)){
            ContentValues c = new ContentValues();
            c.put("idMunicipio", m.getIdMunicipio());
            c.put("idDepartamento", m.getIdDepartamento());
            c.put("nombre", m.getNombre());
            cont = db.insert("Municipio", null, c);
        }

        if(cont == -1 || cont == 0){
            regInsert = "Error al insertar el registro en la base de datos, verificar la insercion";
        }else{
            regInsert=regInsert+cont;
        }

        return regInsert;
    }
    public String actualizar(Municipio m){
        if(verificarIntegridadMncip(m,2)){
            String[] id = {Integer.toString(m.getIdMunicipio()), Integer.toString(m.getIdDepartamento())};
            ContentValues cv = new ContentValues();
            cv.put("nombre", m.getNombre());
            db.update("Municipio", cv, "idMunicipio = ? AND idDepartamento = ?", id);
            return "Registro actualizado correctamente";
        }else{
            return "Registro con Ids: " + m.getIdMunicipio() + " " +  m.getIdDepartamento() + " no existe";
        }
    }
    public Municipio consultarMuni(int idMuni, int idDepto){
        String[] id = {String.valueOf(idMuni), String.valueOf(idDepto)};
        Cursor muniCursor = db.query("Municipio", camposMunicipio, "idMunicipio = ? AND idDepartamento = ?", id, null, null, null);
        if(muniCursor.moveToFirst()){
            Municipio muni = new Municipio();
            muni.setIdMunicipio(Integer.parseInt(muniCursor.getString(0)));
            muni.setIdDepartamento(Integer.parseInt(muniCursor.getString(1)));
            muni.setNombre(muniCursor.getString(2));
            return muni;
        }else{
            return null;
        }
    }
    public String eliminar(Municipio m){
        String regAfectados = "Filas Afectadas = ";
        int cont = 0;
        String where = "idMunicipio = '" + m.getIdMunicipio() + "'";
        where = where + "AND idDepartamento = '" + m.getIdDepartamento() + "'";
        cont += db.delete("Municipio", where, null);
        regAfectados+=cont;
        return regAfectados;
    }
    /*----DISTRITO----*/
    public String insertar(Distrito dis){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;

        if(verificarIntegridaDist(dis, 1)){
            ContentValues c = new ContentValues();
            c.put("idDistrito", dis.getIdDistrito());
            c.put("idMunicipio", dis.getIdMunicipio());
            c.put("nombre", dis.getNombre());
            cont = db.insert("Distrito", null, c);
        }

        if(cont == -1 || cont == 0){
            regInsert = "Error al insertar el registro en la base de datos, verificar la insercion";
        }else{
            regInsert=regInsert+cont;
        }

        return regInsert;
    }
    public String actualizar(Distrito dis){
        if(verificarIntegridaDist(dis,2)){
            String[] id = {Integer.toString(dis.getIdDistrito()), Integer.toString(dis.getIdMunicipio())};
            ContentValues cv = new ContentValues();
            cv.put("nombre", dis.getNombre());
            db.update("Distrito", cv, "idDistrito = ? AND idMunicipio = ?", id);
            return "Registro actualizado correctamente";
        }else{
            return "Registro con Ids: " + dis.getIdDistrito() + " " + dis.getIdMunicipio() + " No existe";
        }
    }
    public Distrito consultarDis(int idDis, int idMun){
        String[] id = {String.valueOf(idDis), String.valueOf(idMun)};
        Cursor disCursor = db.query("Distrito", camposDistrito, "idDistrito = ? AND idMunicipio = ?", id, null, null, null);
        if(disCursor.moveToFirst()){
            Distrito dis = new Distrito();
            dis.setIdDistrito(Integer.parseInt(disCursor.getString(0)));
            dis.setIdMunicipio(Integer.parseInt(disCursor.getString(1)));
            dis.setNombre(disCursor.getString(2));
            return dis;
        }else{
            return null;
        }
    }
    public String eliminar(Distrito dis){
        String regAfectados = "Filas Afectadas = ";
        int cont = 0;
        String where = "idDistrito = '" + dis.getIdDistrito() + "'";
        where = where + "AND idMunicipio = '" + dis.getIdMunicipio() + "'";
        cont += db.delete("Distrito", where, null);
        regAfectados+=cont;
        return regAfectados;
    }
    public boolean verificarIntegridadLab(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:{
                //verificar que el ID Exista
                Laboratorio labExiste = (Laboratorio) dato;
                String[] id = {Integer.toString(labExiste.getIdLaboratorio())};
                abrir();
                Cursor cExist = db.query("Laboratorio", null, "idLaboratorio = ?", id, null, null, null);
                if(cExist.moveToFirst()){
                    //Se encontro el lab
                    return true;
                }
                return false;
            }
            default:
                return false;
        }
    }
    public boolean verificarIntegridadMarca(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:{
                //verificar que el Id de la marca que se quiere usar exista en la tabla
                Marca m = (Marca) dato;
                String[] id = {Integer.toString(m.getIdMarca())};
                abrir();
                Cursor cExistMarca = db.query("Marca", null, "idMarca = ?", id, null, null, null);
                if(cExistMarca.moveToFirst()){
                    //se encontro el ID
                    return true;
                }
                return false;
            }
            default:
                return false;
        }
    }
    public boolean verificarIntegridadDpto(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:{
                //verificar que el ID Exista
                Departamento dptoExiste = (Departamento) dato;
                String[] id = {Integer.toString(dptoExiste.getIdDepartamento())};
                abrir();
                Cursor cExist = db.query("Departamento", null, "idDepartamento = ?", id, null, null, null);
                if(cExist.moveToFirst()){
                    //Se encontro el dpto
                    return true;
                }
                return false;
            }
            default:
                return false;
        }
    }
    public boolean verificarIntegridadMncip(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:{
                //verificar que al insertar el municipio exista el departamento seleccionado con el ID
                Municipio mun = (Municipio) dato;
                String[] idDepto = {Integer.toString(mun.getIdDepartamento())};
                //se consulta en la tabla relacionada si existe el id, si lo encuentra entonces es posible realizar la insercion
                Cursor c1 = db.query("Departamento", null, "idDepartamento = ?", idDepto, null, null, null);
                if(c1.moveToFirst()){
                    //Se encontro el id
                    return true;
                }
                return false;
            }
            case 2:{
                //verificar que al actualizar el municipio exista el departamento seleccionado con el ID asi como el municipio previamente insertado
                Municipio m = (Municipio) dato;
                String[] ids = {Integer.toString(m.getIdMunicipio()), Integer.toString(m.getIdDepartamento())};
                abrir();
                Cursor actu = db.query("Municipio", null, "idMunicipio = ? AND idDepartamento = ?", ids, null, null, null);
                if(actu.moveToFirst()){
                    //se encontro el registro
                    return true;
                }
                actu.close();
                cerrar();
                return false;
            }
            default:
                return false;
        }
    }
    public boolean verificarIntegridaDist(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:{
                //verificar que al insertar el distrito exista el municipio seleccionado con el ID
                Distrito dis = (Distrito)dato;
                String[] idMuni = {Integer.toString(dis.getIdMunicipio())};
                Cursor curMuni = db.query("Municipio", null, "idMunicipio = ?", idMuni, null, null, null);
                if(curMuni.moveToFirst()){
                    //Se encontro el id
                    return true;
                }
                return false;
            }
            case 2:{
                //verificar que al actualizar el distrito exista el municipio seleccionado con el ID asi como el distrito previamente insertado
                Distrito dis = (Distrito) dato;
                String[] ids = {Integer.toString(dis.getIdDistrito()), Integer.toString(dis.getIdMunicipio())};
                abrir();
                Cursor actu = db.query("Distrito", null, "idDistrito = ? AND idMunicipio = ?", ids, null, null, null);
                if(actu.moveToFirst()){
                    //se encontro el registro
                    return true;
                }
                actu.close();
                cerrar();
                return false;
            }
            default:
                return false;
        }
    }
    /*----------------------------------------------------LLENADO DE TABLAS------------------------------------------------*/

    public String llenadoTablas(){
        abrir();

        // Limpieza completa de tablas, incluyendo las que generan conflicto
        db.execSQL("DELETE FROM distrito");
        db.execSQL("DELETE FROM municipio");
        db.execSQL("DELETE FROM departamento");
        db.execSQL("DELETE FROM marca");
        db.execSQL("DELETE FROM laboratorio");
        db.execSQL("DELETE FROM doctor");
        db.execSQL("DELETE FROM medicamento");
        db.execSQL("DELETE FROM elemento");
        db.execSQL("DELETE FROM ubicacion");
        db.execSQL("DELETE FROM local");
        db.execSQL("DELETE FROM cliente");
        db.execSQL("DELETE FROM detalleReceta");
        db.execSQL("DELETE FROM distribuidor");

        // Departamento
        final int[] idDepartamento = {1, 2, 3, 4, 5};
        final String[] nombreDep = {"San Salvador", "La Libertad", "Santa Ana", "Chalatenango", "San Miguel"};
        Departamento d = new Departamento();
        for (int i = 0; i < 5; i++) {
            d.setIdDepartamento(idDepartamento[i]);
            d.setNombre(nombreDep[i]);
            insertar(d);
        }

        // Municipio
        final int[] idMunicipio = {100, 200, 300, 400, 500};
        final int[] idDepartamentoMun = {1, 2, 3, 4, 5};
        final String[] nombreMun = {"Soyapango", "Santa Tecla", "Metapán", "Mejicanos", "Chirilagua"};
        Municipio m = new Municipio();
        for (int i = 0; i < 5; i++) {
            m.setIdMunicipio(idMunicipio[i]);
            m.setIdDepartamento(idDepartamentoMun[i]);
            m.setNombre(nombreMun[i]);
            insertar(m);
        }

        // Distrito
        final int[] idDistrito = {1000, 2000, 3000, 4000, 5000};
        final int[] idMunicipioDistrito = {100, 200, 300, 400, 500};
        final String[] nombreDist = {"Distrito 1", "Distrito 2", "Distrito 3", "Distrito 4", "Distrito 5"};
        Distrito dis = new Distrito();
        for (int i = 0; i < 5; i++) {
            dis.setIdDistrito(idDistrito[i]);
            dis.setIdMunicipio(idMunicipioDistrito[i]);
            dis.setNombre(nombreDist[i]);
            insertar(dis);
        }

        // Marca
        final int[] idMarca = {501, 502, 503, 504, 505};
        final String[] nombreMarca = {"Farvel", "EISI", "GUD", "Advi", "PDM"};
        Marca marca = new Marca();
        for (int i = 0; i < 5; i++) {
            marca.setIdMarca(idMarca[i]);
            marca.setNombre(nombreMarca[i]);
            insertar(marca);
        }

        // Doctor
        final int[] idDoctor = {1, 2, 3, 4, 5};
        final String[] nombreDoctor = {"Juan Lopez", "Elmer Chavez", "Gustavo Fring", "Melissa Hernandez", "Daniela Diaz"};
        final String[] especialidad = {"Cirujano", "Pediatra", "Odontologo", "Ginecologa", "Cardiologo"};
        final String[] jvpm = {"1234", "7894", "7531", "8754", "4561"};
        final String[] telefonoDoctor = {"80017029", "80017030", "80017031", "80017032", "80017033"};
        final String[] correoDoctor = {"juan@gmail.com", "elmer@gmail.com", "gustavo@gmail.com", "melissa@gamil.com", "daniela@gmail.com"};
        Doctor doctor = new Doctor();
        for (int i = 0; i < 5; i++) {
            doctor.setIdDoctor(idDoctor[i]);
            doctor.setNombreDoctor(nombreDoctor[i]);
            doctor.setEspecialidad(especialidad[i]);
            doctor.setJvpm(jvpm[i]);
            doctor.setTelefonoDoctor(telefonoDoctor[i]);
            doctor.setCorreoDoctor(correoDoctor[i]);
            insertar(doctor);
        }

        // Laboratorio
        final int[] idLaboratorio = {1, 2, 3, 4, 5};
        final String[] nombre = {"Laboratorio Pfizer", "Laboratorio SanSavior", "Laboratorio Quesadillas", "LabComputo", "Laboratorio Bukeli"};
        final String[] tipo = {"Química", "Quimica", "Biología", "Clinico", "Medrano"};
        final String[] telefono = {"70017029", "70017030", "70017031", "70017032", "70017033"};
        Laboratorio l = new Laboratorio();
        for (int i = 0; i < 5; i++) {
            l.setIdLaboratorio(idLaboratorio[i]);
            l.setNombre(nombre[i]);
            l.setTipo(tipo[i]);
            l.setTelefono(telefono[i]);
            insertar(l);
        }

        // Elemento y Medicamento
        final int[] idElemento = {1, 2, 3, 4, 5};
        final String[] nombreElemento = {"Paracetamol", "Aspirina", "Cefalexina", "Clonazepam", "Alca D"};
        final int[] cantidad = {4, 10, 5, 20, 25};
        final String[] descripcion = {"Para dolores leves a moderados", "Para dolores y fiebre", "Para infecciones bacterianas", "Trastornos de ansiedad y epilepsia", "Alivio de diarrea"};
        final Double[] precioUnitario = {12.50, 15.20, 5.60, 40.30, 50.00};
        final String[] unidad = {"Blísteres", "Caja", "Cápsulas", "Ampollas", "Frascos"};
        final String[] idMedicamento = {"A1", "A2", "A3", "A4", "A5"};
        final String[] viaAdmin = {"Oral", "Sublingual", "Rectal", "Intravenosa", "Intramuscular"};
        final String[] forma = {"Solida", "Liquida", "Solida", "Liquida", "Semisolida"};

        Medicamento medicamento = new Medicamento();
        for (int i = 0; i < 5; i++) {
            medicamento.setCodElemento(idElemento[i]);
            medicamento.setIdMedicamento(idMedicamento[i]);
            medicamento.setNombre(nombreElemento[i]);
            medicamento.setCantidad(cantidad[i]);
            medicamento.setDescripcion(descripcion[i]);
            medicamento.setPrecioUni(precioUnitario[i]);
            medicamento.setUnidades(unidad[i]);
            medicamento.setIdLaboratorio(idLaboratorio[i]);
            medicamento.setViaDeAdministracion(viaAdmin[i]);
            medicamento.setFormaFarmaceutica(forma[i]);
            insertar(medicamento);
        }

        // Ubicacion
        final int[] idUbicacion = {1, 2, 3, 4, 5};
        final String[] descripcionUbi = {"Distrito 1, Soyapango, San Salvador", "Distrito 2, Santa Tecla, La Libertad", "Distrito 3, Metapán, Santa Ana", "Distrito 4, Mejicanos, Chalatenango", "Distrito 5, Chirilagua, San Miguel"};
        Ubicacion ubicacion = new Ubicacion();
        for (int i = 0; i < 5; i++) {
            ubicacion.setIdUbicacion(idUbicacion[i]);
            ubicacion.setDetalle(descripcionUbi[i]);
            ubicacion.setIdMarca(idMarca[i]);
            ubicacion.setIdDistrito(idDistrito[i]);
            insertar(ubicacion);
        }

        // Local
        final int[] idLocal = {1, 2, 3, 4, 5};
        final String[] nombreLocal = {"Local A, Planta Baja", "Local B, Segunda Planta", "Sucursal San Benito", "Farmacia Plaza Futura", "Sucursal Plaza Mayor"};
        final String[] tipoLocal = {"Local", "Local", "Sucursal", "Farmacia", "Sucursal"};
        final String[] telefonoLocal = {"70017029", "70017030", "70017031", "70017032", "70017033"};
        Local local = new Local();
        for (int i = 0; i < 5; i++) {
            local.setIdUbicacion(idUbicacion[i]);
            local.setIdLocal(idLocal[i]);
            local.setNombreLocal(nombreLocal[i]);
            local.setTipoLocal(tipoLocal[i]);
            local.setTelefonoLocal(telefonoLocal[i]);
            insertar(local);
        }

        // Cliente
        final String[] duiCliente = {"0123456789", "1234567890", "2345678901", "3456789012", "4567890123"};
        final String[] nombreCliente = {"Carlos", "Andrea", "Luis", "Sofia", "Pedro"};
        final String[] apellidoCliente = {"Martinez", "Lopez", "Gomez", "Hernandez", "Diaz"};
        final String[] telefonoCliente = {"70112233", "70223344", "70334455", "70445566", "70556677"};
        final String[] correoCliente = {"carlos@mail.com", "andrea@mail.com", "luis@mail.com", "sofia@mail.com", "pedro@mail.com"};
        Cliente cliente = new Cliente();
        for (int i = 0; i < 5; i++) {
            cliente.setDui(duiCliente[i]);
            cliente.setNombre(nombreCliente[i]);
            cliente.setApellido(apellidoCliente[i]);
            cliente.setTelefono(telefonoCliente[i]);
            cliente.setCorreo(correoCliente[i]);
            insertarCliente(cliente);
        }

        // DetalleReceta
        final int[] idDetReceta = {1, 2, 3, 4, 5};
        final int[] idReceta = {10, 20, 30, 40, 50};
        final String[] dosis = {"1 cada 8 horas", "2 diarias", "1 antes de dormir", "Cada 6 horas", "1 diaria"};
        DetalleReceta detalleReceta = new DetalleReceta();
        for (int i = 0; i < 5; i++) {
            detalleReceta.setIdDetReceta(idDetReceta[i]);
            detalleReceta.setIdDetReceta(idReceta[i]);
            detalleReceta.setDosis(dosis[i]);
            insertarDetalleReceta(detalleReceta);
        }

        // Distribuidor
        final int[] idDistribuidor = {101, 102, 103, 104, 105};
        final int[] idMarcaDistribuidor = {501, 502, 503, 504, 505};
        final String[] nombreDistribuidor = {"Droguería San Jose", "Distribuidora EISI", "Medical Supply", "Global Farma", "Distribuciones PDM"};
        final String[] telefonoDistribuidor = {"71112233", "72223344", "73334455", "74445566", "75556677"};
        final String[] nitDistribuidor = {"1234567890", "2345678901", "3456789012", "4567890123", "5678901234"};
        final String[] correoDistribuidor = {"sanjo@mail.com", "eisi@mail.com", "medsup@mail.com", "gfarma@mail.com", "pdm@mail.com"};
        Distribuidor distribuidor = new Distribuidor();
        for (int i = 0; i < 5; i++) {
            distribuidor.setIdDistribuidor(idDistribuidor[i]);
            distribuidor.setIdMarca(idMarcaDistribuidor[i]);
            distribuidor.setNombre(nombreDistribuidor[i]);
            distribuidor.setTelefono(telefonoDistribuidor[i]);
            distribuidor.setNit(nitDistribuidor[i]);
            distribuidor.setCorreo(correoDistribuidor[i]);
            insertarDistribuidor(distribuidor);
        }

        cerrar();
        return context.getResources().getString(R.string.llenadoBD);
    }



    //Fin GD21001/////////////
    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException {
        switch (relacion) {
            case 1: {
                // Verificar que la ubicación exista antes de insertar Local
                Local local = (Local) dato;
                String[] idUbicacion = {String.valueOf(local.getIdUbicacion())};
                Cursor c = db.query("ubicacion", null, "idUbicacion = ?", idUbicacion, null, null, null);
                return c.moveToFirst();
            }
            case 2: {
                // Verificar idLaboratorio antes de insertar Medicamento
                Medicamento med = (Medicamento) dato;
                String[] idLab = {String.valueOf(med.getIdLaboratorio())};
                Cursor c1 = db.query("laboratorio", null, "idLaboratorio = ?", idLab, null, null, null);
                return c1.moveToFirst() ;
            }
            case 3:{
                Local local = (Local) dato;
                String[] idLocal = {String.valueOf(local.getIdLocal())};
                Cursor c2 = db.query("local", null, "idLocal = ?", idLocal, null, null, null);
                return c2.moveToFirst();
            }
            case 4: {

                Medicamento med = (Medicamento) dato;
                String[] idMed = {String.valueOf(med.getIdMedicamento())};
                Cursor c = db.query("medicamento", null, "idMedicamento = ?", idMed, null, null, null);
                return c.moveToFirst();
            }  case 5:{
                Doctor doctor = (Doctor) dato;
                String[] codDoctor = {String.valueOf(doctor.getIdDoctor())};
                Cursor c2 = db.query("doctor", null, "idDoctor = ?", codDoctor, null, null, null);
                return c2.moveToFirst();
            }

            default:
                return false;
        }
    }

    //Metodos DE MM22108
    // Cliente
    public boolean insertarCliente(Cliente cliente) {
        db = DBHelper.getWritableDatabase();
        if (cliente == null) {
            Log.e("DB", "Cliente es null");
            return false;
        }

        Log.d("DB", "Insertando cliente: " + cliente.getDui() + ", " + cliente.getNombre());

        ContentValues values = new ContentValues();
        values.put("dui", cliente.getDui());
        values.put("nombre", cliente.getNombre());
        values.put("apellido", cliente.getApellido());
        values.put("telefono", cliente.getTelefono());
        values.put("correo", cliente.getCorreo());

        try {
            long resultado = db.insert("cliente", null, values);
            Log.d("DB", "Resultado insert: " + resultado);

            return resultado != -1;
        } catch (Exception e) {
            Log.e("DB", "Error insertando cliente", e);

            return false;
        }
    }


    public Cliente consultarCliente(String dui) {
        db = DBHelper.getWritableDatabase();
        Cliente cliente = null;
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "cliente",
                    new String[]{"dui", "nombre", "apellido", "telefono", "correo"},
                    "dui = ?",
                    new String[]{dui},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                cliente = new Cliente(
                        cursor.getString(cursor.getColumnIndexOrThrow("dui")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                        cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                        cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                );
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return cliente;
    }

    public boolean eliminarCliente(String dui) {
        db = DBHelper.getWritableDatabase();
        int filasAfectadas = 0;

        try {
            filasAfectadas = db.delete("cliente", "dui = ?", new String[]{dui});
            Log.d("DB", "Filas eliminadas: " + filasAfectadas);
        } catch (Exception e) {
            Log.e("DB", "Error eliminando cliente", e);
        }

        return filasAfectadas > 0;
    }

    public boolean actualizarCliente(Cliente cliente) {
        db = DBHelper.getWritableDatabase();
        int filasAfectadas = 0;

        if (cliente == null) {
            Log.e("DB", "Cliente es null");
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("apellido", cliente.getApellido());
        values.put("telefono", cliente.getTelefono());
        values.put("correo", cliente.getCorreo());

        try {
            filasAfectadas = db.update(
                    "cliente",             // nombre de la tabla
                    values,                // nuevos valores
                    "dui = ?",             // cláusula WHERE
                    new String[]{cliente.getDui()} // argumentos WHERE
            );
            Log.d("DB", "Filas actualizadas: " + filasAfectadas);

        } catch (Exception e) {
            Log.e("DB", "Error actualizando cliente", e);
        }

        return filasAfectadas > 0;
    }

    //Detalle Receta
    public boolean insertarDetalleReceta(DetalleReceta detalle) {
        try {
            db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("idDetReceta", detalle.getIdDetReceta());
            values.put("idReceta", detalle.getIdReceta());
            values.put("dosis", detalle.getDosis());

            long resultado = db.insert("detalleReceta", null, values);

            return resultado != -1;
        } catch (Exception e) {
            Log.e("DB", "Error insertando detalle receta", e);
            return false;
        }
    }

    public boolean eliminarDetalleReceta(int idDetReceta) {
        try {
            db = DBHelper.getWritableDatabase();
            int filasAfectadas = db.delete("detalleReceta", "idDetReceta = ?", new String[]{String.valueOf(idDetReceta)});

            return filasAfectadas > 0;
        } catch (Exception e) {
            Log.e("DB", "Error eliminando detalle receta", e);
            return false;
        }
    }

    public DetalleReceta consultarDetalleReceta(int idDetReceta) {
        DetalleReceta detalle = null;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT idReceta, dosis FROM detalleReceta WHERE idDetReceta = ?",
                    new String[]{String.valueOf(idDetReceta)}
            );

            if (cursor != null && cursor.moveToFirst()) {
                int idReceta = cursor.getInt(0);
                String dosis = cursor.getString(1);
                detalle = new DetalleReceta(idDetReceta, idReceta, dosis);
            }
        } catch (Exception e) {
            Log.e("DB", "Error consultando detalle receta", e);
        } finally {
            if (cursor != null) cursor.close();

        }

        return detalle;
    }

    public boolean actualizarDetalleReceta(DetalleReceta detalle) {
        try {
            db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("idReceta", detalle.getIdReceta());
            values.put("dosis", detalle.getDosis());

            String whereClause = "idDetReceta = ?";
            String[] whereArgs = {String.valueOf(detalle.getIdDetReceta())};

            int filasAfectadas = db.update("detalleReceta", values, whereClause, whereArgs);

            return filasAfectadas > 0;
        } catch (Exception e) {
            Log.e("DB", "Error actualizando detalle receta", e);

            return false;
        }
    }

    //Distribuidor
    public boolean insertarDistribuidor(Distribuidor distribuidor) {
        db = DBHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("idDistribuidor", distribuidor.getIdDistribuidor());
        valores.put("idMarca", distribuidor.getIdMarca());
        valores.put("nombre", distribuidor.getNombre());
        valores.put("telefono", distribuidor.getTelefono());
        valores.put("nit", distribuidor.getNit());
        valores.put("correo", distribuidor.getCorreo());

        long resultado = -1;
        try {
            boolean verificar = verificarMarca(distribuidor.getIdMarca());
            if(verificar){
                resultado = db.insert("distribuidor", null, valores);
            }

        } catch (Exception e) {
            Log.e("DB", "Error insertando distribuidor", e);
        }

        return resultado != -1;
    }

    public boolean verificarMarca(int idMarca) {
        db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT 1 FROM marca WHERE idMarca = ?", new String[]{String.valueOf(idMarca)});
            return cursor.moveToFirst(); // Retorna true si encontró al menos una coincidencia
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
    }

    public boolean eliminarDistribuidor(int idDistribuidor) {
        db = DBHelper.getWritableDatabase();
        int resultado = 0;
        try {
            boolean eliminado = eliminarDistribuidorArticulo(idDistribuidor);
            if(eliminado){
                resultado = db.delete("distribuidor", "idDistribuidor = ?", new String[]{String.valueOf(idDistribuidor)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado > 0;
    }

    private boolean eliminarDistribuidorArticulo(int idDistribuidor){
        db = DBHelper.getWritableDatabase();
        int resultado = 0;
        try{
            resultado = db.delete("articulo","idDistribuidor = ?", new String[]{String.valueOf(idDistribuidor)});
        }
        catch (Exception e){
            return false;
        }

        return resultado > 0;

    }

    public Distribuidor consultarDistribuidor(int idDistribuidor) {
        db = DBHelper.getReadableDatabase();
        Distribuidor distribuidor = null;
        Cursor cursor = null;

        try {
            cursor = db.query("distribuidor", null, "idDistribuidor = ?", new String[]{String.valueOf(idDistribuidor)}, null, null, null);
            if (cursor.moveToFirst()) {
                int idMarca = cursor.getInt(cursor.getColumnIndexOrThrow("idMarca"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
                String nit = cursor.getString(cursor.getColumnIndexOrThrow("nit"));
                String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));

                distribuidor = new Distribuidor(idDistribuidor, idMarca, nombre, telefono, nit, correo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();

        }

        return distribuidor;
    }

    public boolean actualizarDistribuidor(Distribuidor distribuidor) {
        SQLiteDatabase db = null;
        try {
            db = DBHelper.getWritableDatabase();
            int filasAfectadas = 0;
            ContentValues values = new ContentValues();

            values.put("idMarca", distribuidor.getIdMarca());
            values.put("nombre", distribuidor.getNombre());
            values.put("telefono", distribuidor.getTelefono());
            values.put("nit", distribuidor.getNit());
            values.put("correo", distribuidor.getCorreo());

            boolean verificar = verificarMarca(distribuidor.getIdMarca());
            if(verificar){
            filasAfectadas = db.update(
                    "distribuidor",
                    values,
                    "idDistribuidor = ?",
                    new String[]{String.valueOf(distribuidor.getIdDistribuidor())}
            );}


            return filasAfectadas > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /* Insertar Usuario*/
    public String insertar(User usuario) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues user = new ContentValues();
        user.put("id_usuario", usuario.getId_usuario());
        user.put("nom_usuario", usuario.getNom_usuario());
        user.put("clave", usuario.getClave());
        contador = db.insert("User", null, user);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public String insertar(AccesoUsuario accesoUsuario) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues accUsuario = new ContentValues();
        accUsuario.put("id_acceso", accesoUsuario.getId_acceso());
        accUsuario.put("id_usuario", accesoUsuario.getId_usuario());
        accUsuario.put("id_opcion_crud", accesoUsuario.getId_opcion_crud());

        ;
        contador = db.insert("AccesoUsuario", null, accUsuario);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    // CAMPOS: {"id_opcion_crud", "des_opcion"}
    public String insertar(OpcionCrud opcionCrud) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues opcCrud = new ContentValues();
        opcCrud.put("id_opcion_crud", opcionCrud.getId_opcion_crud());
        opcCrud.put("des_opcion", opcionCrud.getDes_opcion());
        opcCrud.put("num_crud",opcionCrud.getNumCrud());
        ;
        contador = db.insert("OpcionCrud", null, opcCrud);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public void permisosUsuarios() {
        db = DBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM User");
        db.execSQL("DELETE FROM OpcionCrud");
        db.execSQL("DELETE FROM AccesoUsuario");

        //User
        final String[] IDusuario = {"01", "02", "03", "04", "05"};
        final String[] nomUsuario = {"Erick", "Jaime", "Medrano", "Camilo", "Rodrigo"};
        final String[] clave = {"GA21090", "GD21001", "MM21030", "MM22108", "PG22010"};

        User user = new User();

        for (int i = 0; i < 5; i++) {
            user.setId_usuario(IDusuario[i]);
            user.setNom_usuario(nomUsuario[i]);
            user.setClave(clave[i]);
            insertar(user);
        }
        //OPCIONCRUD

        final int[] idOpcionCrud = {1, 2, 3, 4};
        final String[] opcionCrud = {"Insertar", "Actualizar", "Eliminar", "Consultar"};
        for (int i = 0; i < opcionCrud.length; i++) {
            OpcionCrud opcion = new OpcionCrud(idOpcionCrud[i], opcionCrud[i],i);
            insertar(opcion);
        }

        //ACCESOUSUARIO
        final int[] idsAccesoUsuario = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final String[] IDusuarios = {"01", "01", "01", "01", "02", "02", "03", "03", "04", "05"};
        final int[] idOpcionCrud_Access = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2};
        for (int i = 0; i < idsAccesoUsuario.length; i++) {
            AccesoUsuario accesoUsuario = new AccesoUsuario(idsAccesoUsuario[i], IDusuarios[i], idOpcionCrud_Access[i]);
            insertar(accesoUsuario);
        }
    }
}
