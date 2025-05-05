package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class DoctorInsertarActivity extends Activity {
    ControlDBFarmacia helper;
    EditText editIdDoctor;
    EditText editNombreDoctor;
    EditText editEspecialidad;
    EditText editJvpm;
    EditText telefonoDoctor;
    EditText correoDoctor;
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_insertar);
        helper = new ControlDBFarmacia(this);
        editIdDoctor = (EditText) findViewById(R.id.editIdDoc);
        editNombreDoctor = (EditText) findViewById(R.id.editNombreDoctor);
        editEspecialidad = (EditText) findViewById(R.id.editEspecialidad);
        editJvpm = (EditText) findViewById(R.id.editJvpm);
        telefonoDoctor = (EditText) findViewById(R.id.editTelefonoDoctor);
        correoDoctor= ( EditText) findViewById(R.id.editCorreoDoctor);
    }
    public void insertarDoctor(View v) {
        Integer idDoctor = Integer.parseInt(editIdDoctor.getText().toString());
        String nombreDoctor=editNombreDoctor.getText().toString();
        String especialidad=editEspecialidad.getText().toString();
        String telefono=telefonoDoctor.getText().toString();
        String correo=correoDoctor.getText().toString();
        String jvpm = editJvpm.getText().toString();
        String regInsertados;
        Doctor doctor=new Doctor();
        doctor.setIdDoctor(idDoctor);
        doctor.setNombreDoctor(nombreDoctor);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefonoDoctor(telefono);
        doctor.setCorreoDoctor(correo);
        doctor.setJvpm(jvpm);
        helper.abrir();
        regInsertados=helper.insertar(doctor);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdDoctor.setText("");
        editNombreDoctor.setText("");
        editEspecialidad.setText("");
        editJvpm.setText("");
        telefonoDoctor.setText("");
        correoDoctor.setText("");
    }
}