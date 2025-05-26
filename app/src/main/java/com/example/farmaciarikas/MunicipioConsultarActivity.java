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

public class MunicipioConsultarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMunicipio, editIdDepartamentoMun, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_consultar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMunicipio = (EditText) findViewById(R.id.muniIdConsEdit);
        editIdDepartamentoMun = (EditText) findViewById(R.id.depMuniIdConsEdit);
        editNombre = (EditText) findViewById(R.id.muniNomConsEdit);
    }



    public void limpiarCampos(View v){
        editIdMunicipio.setText("");
        editIdDepartamentoMun.setText("");
        editNombre.setText("");
    }

    public void consMuni(View v) {
        try{
            if(editIdMunicipio.getText().toString().isEmpty() || editIdDepartamentoMun.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de ID Municipio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                int idMuni, idDepto;
                idMuni = Integer.parseInt(editIdMunicipio.getText().toString());
                idDepto = Integer.parseInt(editIdDepartamentoMun.getText().toString());
                dbFarmHelper.abrir();
                Municipio muni = dbFarmHelper.consultarMuni(idMuni, idDepto);
                dbFarmHelper.cerrar();
                if(muni == null){
                    Toast.makeText(this, "Municipio no registrado", Toast.LENGTH_SHORT).show();
                }else{
                    editNombre.setText(muni.getNombre());
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Consultar en Municipio", Toast.LENGTH_SHORT).show();
        }
    }
}