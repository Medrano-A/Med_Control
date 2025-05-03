package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalEliminarActivity extends Activity {


    EditText editLocal;
    ControlDBFarmacia controlhelper;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_eliminar);
        controlhelper=new ControlDBFarmacia(this);
        editLocal=(EditText)findViewById(R.id.editLocal);

    }

    public void eliminarNota(View v){
        String regEliminadas;
        Local local=new Local();
        local.setIdLocal(Integer.parseInt(editLocal.getText().toString()));

        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(local);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){
       editLocal.setText("");
    }
}