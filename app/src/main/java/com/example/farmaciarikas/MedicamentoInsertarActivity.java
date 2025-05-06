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
    EditText ediforma;
    EditText editNombre;
    EditText editCantidad;
    EditText editDescripcion;
    EditText editPrecio;
    EditText editUnidad;


    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_insertar);
        helper = new ControlDBFarmacia(this);
        editIdMedicamento = (EditText) findViewById(R.id.editIdMed);

        idLaboratorio = (EditText) findViewById(R.id.editIdLab);
        editViaAdmin = (EditText) findViewById(R.id.editVia);
        ediforma = (EditText) findViewById(R.id.editForma);
        editNombre =( EditText) findViewById(R.id.editNombreMedicamento);
        editCantidad =(EditText) findViewById(R.id.editCantidad);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editUnidad = (EditText) findViewById(R.id.editUnidad);

    }
    public void insertarMedicamento(View v) {
        String idMedicamento =editIdMedicamento.getText().toString();
        Integer idLab=Integer.parseInt(idLaboratorio.getText().toString());
        String viaDeAdministracion=editViaAdmin.getText().toString();
        String form=ediforma.getText().toString();
        String nombre= editNombre.getText().toString();
        Integer cantidad = Integer.parseInt(editCantidad.getText().toString());
        String descripcion = editDescripcion.getText().toString();
        Double precio = Double.parseDouble(editPrecio.getText().toString());
        String unidad = editUnidad.getText().toString();

        String regInsertados;
        Medicamento medicamento=new Medicamento();
        medicamento.setIdMedicamento(idMedicamento);
        medicamento.setIdLaboratorio(idLab);
        medicamento.setViaDeAdministracion(viaDeAdministracion);
        medicamento.setFormaFarmaceutica(form);
        medicamento.setNombre(nombre);
        medicamento.setCantidad(cantidad);
        medicamento.setDescripcion(descripcion);
        medicamento.setPrecioUni(precio);
        medicamento.setUnidades(unidad);

        helper.abrir();
        regInsertados=helper.insertar(medicamento);
        helper.cerrar();

        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        editIdMedicamento.setText("");
        editCodElemento.setText("");
        idLaboratorio.setText("");
        editViaAdmin.setText("");
        ediforma.setText("");
        editNombre.setText("");
        editCantidad.setText("");
        editPrecio.setText("");
        editUnidad.setText("");
        editDescripcion.setText("");
    }

    public void limpiarTexto(View v) {


    }
}