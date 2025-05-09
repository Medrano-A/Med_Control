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

public class DistritoConsultarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdDistrito, editIdMunicipioDis, editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrito_consultar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdDistrito = (EditText) findViewById(R.id.disIdConsEdit);
        editIdMunicipioDis = (EditText) findViewById(R.id.munDisiIdConsEdit);
        editNombre = (EditText) findViewById(R.id.distNomConsEdit);
    }



    public void limpiarCampos(View v){
        editIdDistrito.setText("");
        editIdMunicipioDis.setText("");
        editNombre.setText("");
    }

    public void consDist(View v) {
        int idDis, idMuni;
        idDis = Integer.parseInt(editIdDistrito.getText().toString());
        idMuni = Integer.parseInt(editIdMunicipioDis.getText().toString());
        dbFarmHelper.abrir();
        Distrito dis = dbFarmHelper.consultarDis(idDis, idMuni);
        dbFarmHelper.cerrar();
        if(dis == null){
            Toast.makeText(this, "Distrito no registrado", Toast.LENGTH_SHORT).show();
        }else{
            editNombre.setText(dis.getNombre());
        }
    }
}