package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MedicamentoEliminarActivity extends Activity {

    EditText editIdMed;
    ControlDBFarmacia controlhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_eliminar);
        controlhelper=new ControlDBFarmacia (this);
        editIdMed=(EditText)findViewById(R.id.editMed);
    }

    public void eliminarMedicamento(View v){
        String regEliminadas;
        Medicamento medicamento=new Medicamento();
        medicamento.setIdMedicamento(editIdMed.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(medicamento);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

}