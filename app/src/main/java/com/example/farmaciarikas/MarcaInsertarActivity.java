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

public class MarcaInsertarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMarca, editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_insertar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMarca = (EditText) findViewById(R.id.marcaIdInsertarEdit);
        editNombre = (EditText) findViewById(R.id.marcaNombreInsertarEdit);
    }

    public void insertarMarca(View v) {
        try{
            if(editIdMarca.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty()){
                Toast.makeText(this, "Los campos de Marca se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                int idMarca = Integer.parseInt(editIdMarca.getText().toString());
                String nombre = editNombre.getText().toString();
                String regInsert;
                Marca m = new Marca();
                m.setIdMarca(idMarca);
                m.setNombre(nombre);
                dbFarmHelper.abrir();
                regInsert = dbFarmHelper.insertar(m);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regInsert, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Insertar en Marca", Toast.LENGTH_SHORT).show();
        }

    }

    public void limpiarCampos(View v){
        editIdMarca.setText("");
        editNombre.setText("");
    }
}