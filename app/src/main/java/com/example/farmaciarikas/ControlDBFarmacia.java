package com.example.farmaciarikas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ControlDBFarmacia {

    private static final String[] camposDoctor = new String[]
            {"idDoctor", "nombreDoctor", "especialidad", "jvpm", "telefonoDoctor", "correoDoctor"};
    private static final String[] camposMedicamento = new String[]
            {"idMedicamento", "codElemento", "idLaboratorio", "viaDeAdministracion", "formaFarmaceutica"};
    private static final String[] camposLocal = new String[]
            {"idLocal", "idUbicacion", "nombreLocal", "tipoLocal", "telefonoLocal"};
    private static final String[] camposElemento = new String[] {
            "codElemento", "nombre", "cantidad", "descripcion", "precioUni", "unidades"
    };

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public ControlDBFarmacia(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "farmacia.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
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
                        "    unidades VARCHAR(5)\n" +
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
        DBHelper.close();
    }
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
        //if(verificarIntegridad(doctor, 5)){
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
            regInsertados = "Error al Insertar el registro. Verificar existencia de codElemento o idLaboratorio, o si es duplicado.";
        } else {
            regInsertados = regInsertados + contador;
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
        ContentValues valores = new ContentValues();
        valores.put("idLaboratorio", medicamento.getIdLaboratorio());
        valores.put("viaDeAdministracion", medicamento.getViaDeAdministracion());
        valores.put("formaFarmaceutica", medicamento.getFormaFarmaceutica());

        String where = "idMedicamento=?";
        String[] whereArgs = {medicamento.getIdMedicamento()};

        int filasAfectadas = db.update("medicamento", valores, where, whereArgs);
        return filasAfectadas > 0;  // Si se actualizaron filas, es verdadero
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


            ContentValues loc = new ContentValues();
            loc.put("idLocal", local.getIdLocal());
            loc.put("idUbicacion", local.getIdUbicacion());
            loc.put("nombreLocal", local.getNombreLocal());
            loc.put("tipoLocal", local.getTipoLocal());
            loc.put("telefonoLocal", local.getTelefonoLocal());
            contador = db.insert("local", null, loc);


        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro. Verificar si existe la ubicación o si el registro es duplicado.";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public String actualizar(Local local) {

            String[] id = {String.valueOf(local.getIdLocal())};
            ContentValues cv = new ContentValues();
            cv.put("idUbicacion", local.getIdUbicacion());
            cv.put("nombreLocal", local.getNombreLocal());
            cv.put("tipoLocal", local.getTipoLocal());
            cv.put("telefonoLocal", local.getTelefonoLocal());
            db.update("local", cv, "idLocal = ?", id);
            return "Registro Actualizado Correctamente";

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
                // Verificar codElemento y idLaboratorio antes de insertar Medicamento
                Medicamento med = (Medicamento) dato;
                String[] codElem = {String.valueOf(med.getCodElemento())};
                String[] idLab = {String.valueOf(med.getIdLaboratorio())};
                Cursor c1 = db.query("elemento", null, "codElemento = ?", codElem, null, null, null);
                Cursor c2 = db.query("laboratorio", null, "idLaboratorio = ?", idLab, null, null, null);
                return c1.moveToFirst() && c2.moveToFirst();
            }
            default:
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
