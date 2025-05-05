package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MedicamentoActualizarActivity extends Activity {
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
        setContentView(R.layout.activity_medicamento_actualizar);
        helper = new ControlDBFarmacia(this);
        editIdMedicamento = (EditText) findViewById(R.id.editIdMed);
        codElemento = (EditText) findViewById(R.id.editCodEle);
        editIdLab = (EditText) findViewById(R.id.editIdLab);
        editVia = (EditText) findViewById(R.id.editVia);
        editforma = (EditText) findViewById(R.id.editForma);

    }
    public void actualizarMedicamento(View v) {
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(editIdMedicamento.getText().toString());
        medicamento.setCodElemento(Integer.parseInt(codElemento.getText().toString()));
        medicamento.setIdLaboratorio(Integer.parseInt(editIdLab.getText().toString()));
        medicamento.setViaDeAdministracion(editVia.getText().toString());
        medicamento.setFormaFarmaceutica(editforma.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(medicamento);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdMedicamento.setText("");
        codElemento.setText("");
        editIdLab.setText("");
        editVia.setText("");
        editforma.setText("");

    }
}