package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class LocalInsertarActivity extends Activity {
    ControlDBFarmacia helper;
    EditText editLocal;
    EditText editUbicacion;
    EditText editNombre;
    EditText editTipo;
    EditText telefonoLocal;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_insertar);
        helper = new ControlDBFarmacia(this);
        editLocal = (EditText) findViewById(R.id.editLocal);
        editUbicacion = (EditText) findViewById(R.id.editUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editTipo = (EditText) findViewById(R.id.editTipo);
        telefonoLocal = (EditText) findViewById(R.id.editTelefono);
    }
    public void insertarLocal(View v) {
        String regInsertados;
        Integer idLocal=Integer.parseInt(editLocal.getText().toString());
        Integer idUbicacion=Integer.parseInt(editUbicacion.getText().toString());
        String nombre=editNombre.getText().toString();
        String tipo=editTipo.getText().toString();
        String telefono= telefonoLocal.getText().toString();

        Local local= new Local();
        local.setIdLocal(idLocal);
        local.setIdUbicacion(idUbicacion);
        local.setNombreLocal(nombre);
        local.setTipoLocal(tipo);
        local.setTelefonoLocal(telefono);
        helper.abrir();
        regInsertados=helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editLocal.setText("");
        editNombre.setText("");
        editUbicacion.setText("");
        editTipo.setText("");
        telefonoLocal.setText("");

    }
}