package com.example.farmaciarikas;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class DoctorActualizarActivity extends Activity {
    ControlDBFarmacia helper;
    EditText editIdDoctor;
    EditText editNombre;
    EditText editEspecialidad;
    EditText editJvpm;
    EditText telefono;
    EditText correo;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_actualizar);
        helper = new ControlDBFarmacia(this);
        editIdDoctor = (EditText) findViewById(R.id.editDoctor);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editEspecialidad = (EditText) findViewById(R.id.editEspecialidad);
        editJvpm = (EditText) findViewById(R.id.editJvpm);
        telefono = (EditText) findViewById(R.id.editTelefonoDoctor);
        correo = (EditText)findViewById(R.id.editCorreoDoctor);
    }
    public void actualizarDoctor(View v) {
        Doctor doctor = new Doctor();
        doctor.setIdDoctor(Integer.parseInt(editIdDoctor.getText().toString()));
        doctor.setNombreDoctor(editNombre.getText().toString());
        doctor.setEspecialidad(editEspecialidad.getText().toString());
        doctor.setJvpm(editJvpm.getText().toString());
        doctor.setTelefonoDoctor(telefono.getText().toString());
        doctor.setCorreoDoctor(correo.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(doctor);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdDoctor.setText("");
        editNombre.setText("");
        editEspecialidad.setText("");
        editJvpm.setText("");
        telefono.setText("");
        correo.setText("");
        editIdDoctor.setEnabled(true);

    }
    public void consultarDoctor(View v) {

        helper.abrir();
        Doctor doctor =
                helper.consultarDoctor(Integer.parseInt(editIdDoctor.getText().toString()));

        helper.cerrar();
        if(doctor == null)
            Toast.makeText(this, "Doctor con carnet " +
                    editIdDoctor.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(doctor.getNombreDoctor());
            editEspecialidad.setText(doctor.getEspecialidad());
            editJvpm.setText(doctor.getJvpm());
            telefono.setText(doctor.getTelefonoDoctor());
            correo.setText(String.valueOf(doctor.getCorreoDoctor()));
            editIdDoctor.setEnabled(false);

        }
    }
}