package com.example.farmaciarikas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlDBFarmacia {

    private static final String[] camposDoctor = new String[]
            {"idDoctor", "nombreDoctor", "especialidad", "jvpm", "telefonoDoctor", "correoDoctor"};
    private static final String[] camposMedicamento = new String[]
            {"idMedicamento", "codElemento", "idLaboratorio", "viaDeAdministracion", "formaFarmaceutica"};
    private static final String[] camposLocal = new String[]
            {"idLocal", "idUbicacion", "nombreLocal", "tipoLocal", "telefonoLocal"};

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
                db.execSQL("CREATE TABLE doctor(idDoctor INTEGER NOT NULL PRIMARY KEY,nombreDoctor VARCHAR(30),especialidad VARCHAR(30),jvpm VARCHAR(4),telefonoDoctor VARCHAR(8),correoDoctor VARCHAR(30));");
                db.execSQL("CREATE TABLE medicamento(idMedicamento VARCHAR(4) NOT NULL PRIMARY KEY,codElemento INTEGER NOT NULL,idLaboratorio INTEGER NOT NULL,viaDeAdministracion VARCHAR(32),formaFarmaceutica VARCHAR(32));");
                db.execSQL("CREATE TABLE local(idLocal INTEGER  NOT NULL PRIMARY KEY ,idUbicacion INTEGER  NOT NULL ,nombreLocal VARCHAR(30) ,tipoLocal VARCHAR(30),telefonoLocal VARCHAR(8));");
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

    public String insertar(Medicamento medicamento) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;


            ContentValues medicamentos = new ContentValues();
            medicamentos.put("idMedicamento", medicamento.getIdMedicamento());
            medicamentos.put("codElemento", medicamento.getCodElemento());
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

    public String actualizar(Medicamento medicamento) {

            String[] id = {medicamento.getIdMedicamento()};
            ContentValues cv = new ContentValues();
            cv.put("codElemento", medicamento.getCodElemento());
            cv.put("idLaboratorio", medicamento.getIdLaboratorio());
            cv.put("viaDeAdministracion", medicamento.getViaDeAdministracion());
            cv.put("formaFarmaceutica", medicamento.getFormaFarmaceutica());

            db.update("medicamento", cv, "idMedicamento = ?", id);
            return "Registro Actualizado Correctamente";

    }

    public String actualizar(Local local) {
        if (verificarIntegridad(local, 1)) {
            String[] id = {String.valueOf(local.getIdLocal())};
            ContentValues cv = new ContentValues();
            cv.put("idUbicacion", local.getIdUbicacion());
            cv.put("nombreLocal", local.getNombreLocal());
            cv.put("tipoLocal", local.getTipoLocal());
            cv.put("telefonoLocal", local.getTelefonoLocal());
            db.update("local", cv, "idLocal = ?", id);
            return "Registro Actualizado Correctamente";
        } else {
            return "Error al actualizar: idUbicacion no existe.";
        }
    }

    public String eliminar(Doctor doctor) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("doctor", "idDoctor='" + doctor.getIdDoctor() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar(Local local) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("local", "idLocal='" + local.getIdLocal() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar(Medicamento medicamento) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("medicamento", "idMedicamento='" + medicamento.getIdMedicamento() + "'", null);
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

    public Medicamento consultarMedicamento(String idMedicamento) {
        String[] id = {idMedicamento};
        Cursor cursor = db.query("medicamento", camposMedicamento, "idMedicamento = ?", id, null, null, null, null);
        if (cursor.moveToFirst()) {
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(cursor.getString(0));
            medicamento.setCodElemento(cursor.getInt(1));
            medicamento.setIdLaboratorio(cursor.getInt(2));
            medicamento.setViaDeAdministracion(cursor.getString(3));
            medicamento.setFormaFarmaceutica(cursor.getString(4));
            return medicamento;
        } else {
            return null;
        }
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

}
