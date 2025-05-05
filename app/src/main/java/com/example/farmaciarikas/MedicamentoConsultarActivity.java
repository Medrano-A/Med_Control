package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MedicamentoConsultarActivity extends Activity {

    ControlDBFarmacia helper;
    EditText editIdMedicamento;
    EditText codElemento;
    EditText editIdLab;
    EditText editVia;
    EditText editforma;


    /** Called when the activity is first created. */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_consultar);
        helper = new ControlDBFarmacia(this);

        editIdMedicamento = (EditText) findViewById(R.id.editMed);
        codElemento = (EditText) findViewById(R.id.editcodEle);
        editIdLab = (EditText) findViewById(R.id.editIdLab);
        editVia = (EditText) findViewById(R.id.editVia);
        editforma = (EditText) findViewById(R.id.editForma);

    }

    public void consultarMedicamento(View v) {

        helper.abrir();
        Medicamento medicamento =
                helper.consultarMedicamento(editIdMedicamento.getText().toString());

        helper.cerrar();
        if(medicamento == null)
            Toast.makeText(this, "Medicamento con codigo " +
                    editIdMedicamento.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            codElemento.setText(String.valueOf(medicamento.getCodElemento()));
            editIdLab.setText(String.valueOf(medicamento.getIdLaboratorio()));
            editVia.setText(medicamento.getViaDeAdministracion());
            editforma.setText(medicamento.getFormaFarmaceutica());

        }
    }
    public void limpiarTexto(View v){
        editIdMedicamento.setText("");
        codElemento.setText("");
        editIdLab.setText("");
        editVia.setText("");
        editforma.setText("");
    }
}