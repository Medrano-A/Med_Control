package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalConsultarActivity extends Activity {

    ControlDBFarmacia helper;
    EditText editIdLocal;
    EditText editIdUbicacion;
    EditText editNombre;
    EditText editTipo;

    EditText editTelefono;

    /** Called when the activity is first created. */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_consultar);
        helper = new ControlDBFarmacia(this);

        editIdLocal = (EditText) findViewById(R.id.editLocal);
        editIdUbicacion = (EditText) findViewById(R.id.idUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editTipo = (EditText) findViewById(R.id.idTipo);
        editTelefono = (EditText) findViewById(R.id.editTelefono);

    }

    public void consultarLocal(View v) {

        helper.abrir();

        Local local = helper.consultarLocal(Integer.parseInt(editIdLocal.getText().toString()));

        helper.cerrar();

        if(local == null)
            Toast.makeText(this, "Local no registrado",
                    Toast.LENGTH_LONG).show();

    }

    public void limpiarTexto(View v) {
        editIdLocal.setText("");
        editNombre.setText("");
        editIdUbicacion.setText("");
        editTipo.setText("");
        editTelefono.setText("");
    }
}