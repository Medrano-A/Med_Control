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

    private static final String[] camposLaboratorio = new String[] {
      "idLaboratorio", "nombre", "tipo", "telefono"
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

                //gd21001 tablas
                db.execSQL("CREATE TABLE Laboratorio (idLaboratorio INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL, tipo CHAR(30) NOT NULL, telefono VARCHAR2(8) NOT NULL);");
                db.execSQL("CREATE TABLE Departamento (idDepartamento INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Municipio (idMunicipio INTEGER NOT NULL PRIMARY KEY, idDepartamento INTEGER NOT NULL, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Distrito (idDistrito INTEGER NOT NULL PRIMARY KEY, idMunicipio INTEGER NOT NULL, nombre VARCHAR2(30) NOT NULL);");
                db.execSQL("CREATE TABLE Marca (idMarca INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR2(30) NOT NULL);");



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
    /*----GD21001 Tablas----*/
    /*----LABORATORIO----*/
    public String insertar(Laboratorio l){
        String regInsert = "Registro insertado N°= ";
        long cont = 0;
        ContentValues lab = new ContentValues();
        lab.put("idLaboratorio", l.getIdLaboratorio());
        lab.put("nombre", l.getNombre());
        lab.put("tipo", l.getTipo());
        lab.put("telefono", l.getTelefono());
        cont = db.insert("Laboratorio", null, lab);
        if (cont == 1 || cont == 0){
            regInsert = "Error al insertar el registro, registro ya esta insertado, verificar informacion";
        }else{
            regInsert = regInsert + cont;
        }
        return regInsert;
    }
    public String actualizar(Laboratorio l){
        int idLab = l.getIdLaboratorio();
        String[] idLaboratorio = {Integer.toString(idLab)};
        ContentValues cv = new ContentValues();
        cv.put("nombre", l.getNombre());
        cv.put("tipo", l.getTipo());
        cv.put("telefono", l.getTelefono());
        db.update("Laboratorio",cv,"idLaboratorio = ?", idLaboratorio);
        return "Registro actualizado correctamente";
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
        //verificar la integridad relacionada con otras tablas
        cont += db.delete("Laboratorio", "idLaboratorio = '" + l.getIdLaboratorio() +"'", null);
        return null;
    }
    /*----MARCA----*/
    public String insertar(Marca m){
        return null;
    }
    public String actualizar(Marca m){
        return null;
    }
    public Marca consultarMarca(String idMarca){
        return null;
    }
    public String eliminar(Marca m){
        return null;
    }
    /*----DEPARTAMENTO----*/
//    public String insertar(){
//        return null;
//    }
//    public String actualizar(){
//        return null;
//    }
//    public String consultar(){
//        return null;
//    }
//    public String eliminar(){
//        return null;
//    }
    /*----MUNICIPIO----*/
//    public String insertar(){
//        return null;
//    }
//    public String actualizar(){
//        return null;
//    }
//    public String consultar(){
//        return null;
//    }
//    public String eliminar(){
//        return null;
//    }
    /*----DISTRITO----*/
//    public String insertar(){
//        return null;
//    }
//    public String actualizar(){
//        return null;
//    }
//    public String consultar(){
//        return null;
//    }
//    public String eliminar(){
//        return null;
//    }
    public boolean verificarIntegridadLab(Object dato, int relacion) throws SQLException{
        return true;
    }
    public boolean verificarIntegridadMarca(Object dato, int relacion) throws SQLException{
        return true;
    }
    public boolean verificarIntegridadDpto(Object dato, int relacion) throws SQLException{
        return true;
    }
    public boolean verificarIntegridadMncip(Object dato, int relacion) throws SQLException{
        return true;
    }
    public boolean verificarIntegridaDist(Object dato, int relacion) throws SQLException{
        return true;
    }
    public String llenadoTablasGD21001(){
        abrir();
        //limpiado de tablas
        db.execSQL("DELETE FROM Laboratorio");
        db.execSQL("DELETE FROM Departamento");
        db.execSQL("DELETE FROM Municipio");
        db.execSQL("DELETE FROM Distrito");
        db.execSQL("DELETE FROM Marca");

        //tabla Laboratorio
        /*Campos iniciales*/
        final int[] idLaboratorio = {01, 02, 03, 04, 05};
        final String[] nombre = {"Laboratorio Pfizer", "Laboratorio SanSavior", "Laboratorio Quesadillas", "LabComputo", "Laboratorio Bukeli"};
        final String[] tipo = {"Química", "Quimica", "Biología", "Clinico", "Medrano"};
        final String[] telefono = {"70017029", "70017030", "70017031", "70017032", "70017033"};
        /*Insercion de datos*/
        Laboratorio l = new Laboratorio();
        for(int i=0; i < 5; i++){
            l.setIdLaboratorio(idLaboratorio[i]);
            l.setNombre(nombre[i]);
            l.setTipo(tipo[i]);
            l.setTelefono(telefono[i]);
            insertar(l);
        }
        /*---------------------*/
        //tabla Departamento
        /*Campos iniciales*/
        final int[] idDepartamento = {01, 02, 03, 04, 05};
        final String[] nombreDep = {"San Salvador", "La Libertad", "Santa Ana", "Chalatenango", "San Miguel"};
        /*Insercion de datos*/
        Departamento d = new Departamento();
        for (int i = 0; i < 5; i++) {
            d.setIdDepartamento(idDepartamento[i]);
            d.setNombre(nombreDep[i]);
            //insertar(d);
        }
        /*---------------------*/
        //tabla Municipio
        /*Campos iniciales*/
        final int[] idMunicipio = {011, 012, 013, 014, 015};
        final int[] idDepartamentoMun = {01, 02, 03, 04, 05};
        final String[] nombreMun = {"Soyapango", "Santa Tecla", "Metapán", "Mejicanos", "Chirilagua"};
        /*Insercion de datos*/
        Municipio m = new Municipio();
        for (int i = 0; i < 5; i++) {
            m.setIdMunicipio(idMunicipio[i]);
            m.setIdDepartamento(idDepartamentoMun[i]);
            m.setNombre(nombreMun[i]);
            //insertar(m);
        }
        /*---------------------*/
        //tabla Distrito
        /*Campos iniciales*/
        final int[] idDistrito = {1001, 1002, 1003, 1004, 1005};
        final int[] idMunicipioDistrito = {011, 012, 013, 014, 015};
        final String[] nombreDist = {"Distrito 1", "Distrito 2", "Distrito 3", "Distrito 4", "Distrito 5"};
        /*Insercion de datos*/
        Distrito dis = new Distrito();
        for (int i = 0; i < 5; i++) {
            dis.setIdDistrito(idDistrito[i]);
            dis.setIdMunicipio(idMunicipioDistrito[i]);
            dis.setNombre(nombreDist[i]);
            //insertar(dis);
        }

        /*---------------------*/
        //tabla Marca
        /*Campos iniciales*/
        final int[] idMarca = {501, 502, 503, 504, 505};
        final String[] nombreMarca = {"Farvel", "EISI", "GUD", "Advi", "PDM"};
        /*Insercion de datos*/
        Marca marca = new Marca();
        for (int i = 0; i < 5; i++) {
            marca.setIdMarca(idMarca[i]);
            marca.setNombre(nombreMarca[i]);
            insertar(marca);
        }
        /*---------------------*/

        cerrar();
        return context.getResources().getString(R.string.llenadoBD);
    }
    //Fin GD21001
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

    //Metodos MM22108
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
            resultado = db.insert("distribuidor", null, valores);
        } catch (Exception e) {
            Log.e("DB", "Error insertando distribuidor", e);
        }

        return resultado != -1;
    }

    public boolean eliminarDistribuidor(int idDistribuidor) {
        db = DBHelper.getWritableDatabase();
        int resultado = 0;
        try {
            resultado = db.delete("distribuidor", "idDistribuidor = ?", new String[]{String.valueOf(idDistribuidor)});
        } catch (Exception e) {
            e.printStackTrace();
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
            db.close();
        }

        return distribuidor;
    }

    public boolean actualizarDistribuidor(Distribuidor distribuidor) {
        SQLiteDatabase db = null;
        try {
            db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("idMarca", distribuidor.getIdMarca());
            values.put("nombre", distribuidor.getNombre());
            values.put("telefono", distribuidor.getTelefono());
            values.put("nit", distribuidor.getNit());
            values.put("correo", distribuidor.getCorreo());

            int filasAfectadas = db.update(
                    "distribuidor",
                    values,
                    "idDistribuidor = ?",
                    new String[]{String.valueOf(distribuidor.getIdDistribuidor())}
            );

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
