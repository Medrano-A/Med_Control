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

public class LaboratorioEliminarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdLaboratorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_eliminar);

        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdLaboratorio = (EditText) findViewById(R.id.labIdEliminarEdTxt);
    }

    public void eliminarLabo(View v){
        String regElim;
        Laboratorio l = new Laboratorio();
        l.setIdLaboratorio(Integer.valueOf(editIdLaboratorio.getText().toString()));
        dbFarmHelper.abrir();
        regElim = dbFarmHelper.eliminar(l);
        dbFarmHelper.cerrar();
        Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(View v){
        editIdLaboratorio.setText("");
    }
}