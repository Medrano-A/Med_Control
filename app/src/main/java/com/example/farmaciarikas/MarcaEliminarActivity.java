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

public class MarcaEliminarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_eliminar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMarca = (EditText) findViewById(R.id.marcaIdEliminarEdTxt);
    }

    public void eliminarMarca(View view) {
        String regElim;
        Marca m = new Marca();
        m.setIdMarca(Integer.parseInt(editIdMarca.getText().toString()));
        dbFarmHelper.abrir();
        regElim = dbFarmHelper.eliminar(m);
        dbFarmHelper.cerrar();
        Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(View v){
        editIdMarca.setText("");
    }
}