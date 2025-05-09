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

public class DistritoEliminarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdDistrito, editIdMunicipioDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrito_eliminar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdDistrito = (EditText) findViewById(R.id.disIdElimEdit);
        editIdMunicipioDis = (EditText) findViewById(R.id.muDisiIdElimEdit);
    }

    public void elimDis(View v) {
        try{
            if(editIdDistrito.getText().toString().isEmpty() || editIdMunicipioDis.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de ID Distrito se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                String regElim;
                Distrito dis = new Distrito();
                dis.setIdDistrito(Integer.parseInt(editIdDistrito.getText().toString()));
                dis.setIdMunicipio(Integer.parseInt(editIdMunicipioDis.getText().toString()));
                dbFarmHelper.abrir();
                regElim = dbFarmHelper.eliminar(dis);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error en Eliminar Distrito", Toast.LENGTH_SHORT).show();
            Log.e("LAB_ERROR", "Error al Eliminar Distrito", e);
        }
    }

    public void limpiarCampos(View v){
        editIdDistrito.setText("");
        editIdMunicipioDis.setText("");
    }

}