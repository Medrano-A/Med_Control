package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MunicipioActualizarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMunicipio, editIdDepartamentoMun, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_actualizar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMunicipio = (EditText) findViewById(R.id.muniIdActuEdit);
        editIdDepartamentoMun = (EditText) findViewById(R.id.depMuniIdActuEdit);
        editNombre = (EditText) findViewById(R.id.muniNomActuEdit);
    }

    public void actuCampos(View v){
        Municipio muni = new Municipio();
        muni.setIdMunicipio(Integer.parseInt(editIdMunicipio.getText().toString()));
        muni.setIdDepartamento(Integer.parseInt(editIdDepartamentoMun.getText().toString()));
        muni.setNombre(editNombre.getText().toString());
        dbFarmHelper.abrir();
        String estado = dbFarmHelper.actualizar(muni);
        dbFarmHelper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(View v){
        editIdMunicipio.setText("");
        editIdDepartamentoMun.setText("");
        editNombre.setText("");
    }
}