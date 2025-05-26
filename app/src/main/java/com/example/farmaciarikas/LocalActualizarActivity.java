package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalActualizarActivity extends Activity {

    ControlDBFarmacia helper;
    EditText editIdLocal;
    EditText editIdUbicacion;
    EditText editNombre;
    EditText editTipo;
    EditText editTelefono;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_actualizar);
        helper = new ControlDBFarmacia(this);
        editIdLocal = (EditText) findViewById(R.id.editLocal);
        editIdUbicacion = (EditText) findViewById(R.id.editUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editTipo = (EditText) findViewById(R.id.editTipo);
        editTelefono=(EditText) findViewById(R.id.editTelefono);
    }

    public void actualizarLocal(View v) {
        Local local = new Local();
        local.setIdLocal(Integer.parseInt(editIdLocal.getText().toString()));
        local.setIdUbicacion(Integer.parseInt(editIdUbicacion.getText().toString()));
        local.setNombreLocal(editNombre.getText().toString());
        local.setTipoLocal((editTipo.getText().toString()));
        local.setTelefonoLocal((editTelefono.getText().toString()));

        helper.abrir();
        String estado = helper.actualizar(local);
        helper.cerrar();

        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();

    }
    public void consultarLocal(View v) {

        helper.abrir();

        Local local = helper.consultarLocal(Integer.parseInt(editIdLocal.getText().toString()));

        helper.cerrar();

        if(local == null) {
            Toast.makeText(this, "Local no registrado",
                    Toast.LENGTH_LONG).show();
        }else
        {
            editIdUbicacion.setText(String.valueOf(local.getIdUbicacion()));
            editNombre.setText(local.getNombreLocal());
            editTipo.setText(local.getTipoLocal());
            editTelefono.setText(local.getTelefonoLocal());
            editIdLocal.setEnabled(false);
        }

    }

    public void limpiarTexto(View v) {
        editIdLocal.setText("");
        editNombre.setText("");
        editIdUbicacion.setText("");
        editTipo.setText("");
        editTelefono.setText("");
        editIdLocal.setEnabled(true);
    }
}