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
        try{
            if(editIdMarca.getText().toString().isEmpty()){
                Toast.makeText(this, "El campo ID Marca se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                String regElim;
                Marca m = new Marca();
                m.setIdMarca(Integer.parseInt(editIdMarca.getText().toString()));
                dbFarmHelper.abrir();
                regElim = dbFarmHelper.eliminar(m);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Ha ocurrido un error en Eliminar Marca", Toast.LENGTH_SHORT).show();
            Log.e("LAB_ERROR", "Error al Eliminar Marca", e);
        }
    }

    public void limpiarCampos(View v){
        editIdMarca.setText("");
    }
}