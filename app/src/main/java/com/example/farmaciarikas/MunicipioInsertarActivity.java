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

public class MunicipioInsertarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMunicipio, editIdDepartamentoMun, editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_insertar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMunicipio = (EditText) findViewById(R.id.muniIdInsertarEdit);
        editIdDepartamentoMun = (EditText) findViewById(R.id.depMuniIdInsertarEdit);
        editNombre = (EditText) findViewById(R.id.muniNomInsertarEdit);
    }


    public void insertarMuni(View v) {
        int idMunicipio = Integer.parseInt(editIdMunicipio.getText().toString());
        int idDepaMun = Integer.parseInt(editIdDepartamentoMun.getText().toString());
        String nombre = editNombre.getText().toString();
        String regInsert;
        Municipio muni = new Municipio();
        muni.setIdMunicipio(idMunicipio);
        muni.setIdDepartamento(idDepaMun);
        muni.setNombre(nombre);
        dbFarmHelper.abrir();
        regInsert = dbFarmHelper.insertar(muni);
        dbFarmHelper.cerrar();
        Toast.makeText(this, regInsert, Toast.LENGTH_SHORT).show();
    }



    public void limpiarCampos(View v){
        editIdMunicipio.setText("");
        editIdDepartamentoMun.setText("");
        editNombre.setText("");
    }
}