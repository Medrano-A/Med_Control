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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MarcaConsultarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMarca, editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_consultar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMarca = (EditText) findViewById(R.id.marcaIdConsEdit);
        editNombre = (EditText) findViewById(R.id.marcaNomConsEdit);
    }

    public void consultarMarca(View v) {
        try{
            if(editIdMarca.getText().toString().isEmpty()){
                Toast.makeText(this, "El Campos de ID Marca se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                int idMarc;
                idMarc = Integer.parseInt(editIdMarca.getText().toString());
                dbFarmHelper.abrir();
                Marca m = dbFarmHelper.consultarMarca(idMarc);
                dbFarmHelper.cerrar();
                if(m == null){
                    Toast.makeText(this, "Marca no registrada", Toast.LENGTH_SHORT).show();
                }else{
                    editNombre.setText(m.getNombre());
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Consultar en Marca", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarCampos(View v){
        editIdMarca.setText("");
        editNombre.setText("");
    }
}