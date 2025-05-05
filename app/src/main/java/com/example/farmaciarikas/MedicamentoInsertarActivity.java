package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MedicamentoInsertarActivity extends Activity {
    ControlDBFarmacia helper;
    EditText editIdMedicamento;
    EditText editCodElemento;
    EditText idLaboratorio;
    EditText editViaAdmin;
    EditText forma;

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_insertar);
        helper = new ControlDBFarmacia(this);
        editIdMedicamento = (EditText) findViewById(R.id.editIdMed);
        editCodElemento = (EditText) findViewById(R.id.editCodEle);
        idLaboratorio = (EditText) findViewById(R.id.editIdLab);
        editViaAdmin = (EditText) findViewById(R.id.editVia);
        forma = (EditText) findViewById(R.id.editForma);

    }
    public void insertarMedicamento(View v) {
        String idMedicamento =editIdMedicamento.getText().toString();
        Integer codElemento=Integer.parseInt(editCodElemento.getText().toString());
        Integer idLab=Integer.parseInt(idLaboratorio.getText().toString());
        String viaDeAdministracion=editViaAdmin.getText().toString();
        String form=forma.getText().toString();

        String regInsertados;
        Medicamento medicamento=new Medicamento();
        medicamento.setIdMedicamento(idMedicamento);
        medicamento.setCodElemento(codElemento);
        medicamento.setIdLaboratorio(idLab);
        medicamento.setViaDeAdministracion(viaDeAdministracion);
        medicamento.setFormaFarmaceutica(form);

        helper.abrir();
        regInsertados=helper.insertar(medicamento);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdMedicamento.setText("");
        editCodElemento.setText("");
        idLaboratorio.setText("");
        editViaAdmin.setText("");
        forma.setText("");

    }
}