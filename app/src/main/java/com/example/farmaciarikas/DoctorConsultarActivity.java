package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorConsultarActivity extends Activity {

    ControlDBFarmacia helper;
    EditText editiIdDoctor;
    EditText editNombreDoctor;
    EditText editEspecialidad;
    EditText editJvpm;
    EditText editTelefono;
    EditText correo;

    /** Called when the activity is first created. */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consultar);
        helper = new ControlDBFarmacia(this);

        editiIdDoctor = (EditText) findViewById(R.id.editDoctor);
        editNombreDoctor = (EditText) findViewById(R.id.editNombreDoctor);
        editEspecialidad = (EditText) findViewById(R.id.editEspecialidad);
        editJvpm = (EditText) findViewById(R.id.editJvpm);
        editTelefono = (EditText) findViewById(R.id.editTelefonoDoctor);
        correo = (EditText) findViewById(R.id.editCorreoDoctor);
    }

    public void consultarDoctor(View v) {

        helper.abrir();
        Doctor doctor =
                helper.consultarDoctor(Integer.parseInt(editiIdDoctor.getText().toString()));

        helper.cerrar();
        if(doctor == null)
            Toast.makeText(this, "Doctor con carnet " +
                    editiIdDoctor.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombreDoctor.setText(doctor.getNombreDoctor());
            editEspecialidad.setText(doctor.getEspecialidad());
            editJvpm.setText(doctor.getJvpm());
            editTelefono.setText(doctor.getTelefonoDoctor());
            correo.setText(String.valueOf(doctor.getCorreoDoctor()));
        }
    }
    public void limpiarTexto(View v){
        editiIdDoctor.setText("");
        editNombreDoctor.setText("");
        editEspecialidad.setText("");
        editJvpm.setText("");
        editTelefono.setText("");
        correo.setText("");
    }
}