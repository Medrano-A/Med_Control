package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MunicipioEliminarActivity extends Activity {
    ControlDBFarmacia dbFarmHelper;

    EditText editIdMunicipio, editIdDepartamentoMun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_eliminar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMunicipio = (EditText) findViewById(R.id.muniIdElimEdit);
        editIdDepartamentoMun = (EditText) findViewById(R.id.depMuniIdElimEdit);
    }

    public void elimMuni(View v) {
        try{
            if(editIdMunicipio.getText().toString().isEmpty() || editIdDepartamentoMun.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de ID Municipio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                String regElim;
                Municipio m = new Municipio();
                m.setIdMunicipio(Integer.parseInt(editIdMunicipio.getText().toString()));
                m.setIdDepartamento(Integer.parseInt(editIdDepartamentoMun.getText().toString()));
                dbFarmHelper.abrir();
                regElim = dbFarmHelper.eliminar(m);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error en Eliminar Municipio", Toast.LENGTH_SHORT).show();
            Log.e("LAB_ERROR", "Error al Eliminar Municipio", e);
        }
    }

    public void limpiarCampos(View v){
        editIdMunicipio.setText("");
        editIdDepartamentoMun.setText("");
    }

}