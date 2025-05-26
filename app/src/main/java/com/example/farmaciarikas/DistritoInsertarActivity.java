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

public class DistritoInsertarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdDistrito, editIdMunicipioDis, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrito_insertar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdDistrito = (EditText) findViewById(R.id.disIdInsertarEdit);
        editIdMunicipioDis = (EditText) findViewById(R.id.muDisiIdInsertarEdit);
        editNombre = (EditText) findViewById(R.id.disNomInsertarEdit);
    }

    public void insertarDis(View v){
        try{
            if(editIdDistrito.getText().toString().isEmpty() || editIdMunicipioDis.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de Distrito se estan enviando vacio o los Id puedan existir, por favor verificar", Toast.LENGTH_SHORT).show();
            }else{
                int idDistrito = Integer.parseInt(editIdDistrito.getText().toString());
                int idMunicipio = Integer.parseInt(editIdMunicipioDis.getText().toString());
                String nombre = editNombre.getText().toString();
                String regInsert;

                Distrito dis = new Distrito();
                dis.setIdDistrito(idDistrito);
                dis.setIdMunicipio(idMunicipio);
                dis.setNombre(nombre);

                dbFarmHelper.abrir();
                regInsert = dbFarmHelper.insertar(dis);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regInsert, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Insertar en Distrito", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarCampos(View v){
        editIdDistrito.setText("");
        editIdMunicipioDis.setText("");
        editNombre.setText("");
    }

}