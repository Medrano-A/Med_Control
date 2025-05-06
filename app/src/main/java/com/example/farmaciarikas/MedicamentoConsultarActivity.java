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
    EditText editNombre;
    EditText editCantidad;
    EditText editDescripcion;
    EditText editPrecio;
    EditText editUnidad;



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
        editNombre =( EditText) findViewById(R.id.ediNombre);
        editCantidad =(EditText) findViewById(R.id.editCantidad);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editUnidad = (EditText) findViewById(R.id.editUnidad);

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
            editNombre.setText(medicamento.getNombre());
            editCantidad.setText(String.valueOf(medicamento.getCantidad()));
            editDescripcion.setText(medicamento.getDescripcion());
            editPrecio.setText(String.valueOf(medicamento.getPrecioUni()));
            editUnidad.setText(medicamento.getUnidades());


        }
    }
    public void limpiarTexto(View v){
        editIdMedicamento.setText("");
        codElemento.setText("");
        editIdLab.setText("");
        editVia.setText("");
        editforma.setText("");
        editUnidad.setText("");
        editPrecio.setText("");
        editNombre.setText("");
        editCantidad.setText("");
        editDescripcion.setText("");
    }
}