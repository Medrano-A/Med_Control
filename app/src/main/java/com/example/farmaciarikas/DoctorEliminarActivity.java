package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorEliminarActivity extends Activity {

    EditText editCarnet;
    ControlDBFarmacia controlhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_eliminar);
        controlhelper=new ControlDBFarmacia (this);
        editCarnet=(EditText)findViewById(R.id.editDoctor);
    }

    public void eliminarDoctor(View v){
        String regEliminadas;
        Doctor doctor=new Doctor();
        doctor.setIdDoctor(Integer.parseInt(editCarnet.getText().toString()));
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(doctor);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

}