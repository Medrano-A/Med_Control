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

public class LaboratorioInsertarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdLaboratorio, editNombre, editTipoLab, editTelefonoLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_insertar);

        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdLaboratorio = (EditText) findViewById(R.id.laboIdInsertaredit);
        editNombre = (EditText) findViewById(R.id.laboNombreInsertarEdit);
        editTipoLab = (EditText) findViewById(R.id.laboTipoInsertarEdit);
        editTelefonoLab = (EditText) findViewById(R.id.laboTelInsertarEdit);

//        EdgeToEdge.enable(this);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    public void insertarLabo(View v){
        try{
            if(editIdLaboratorio.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editTipoLab.getText().toString().isEmpty() || editTelefonoLab.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de Laboratorio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                //almacenamiento de datos de entrada, Conversion a los tipos de la BD
                String idLabStng = editIdLaboratorio.getText().toString();
                int idLaboratorio = Integer.parseInt(idLabStng);
                String nombre = editNombre.getText().toString();
                String tipo = editTipoLab.getText().toString();
                String telefono = editTelefonoLab.getText().toString();
                String regInsertados;
                //creacion de objeto laboratoio
                Laboratorio lab = new Laboratorio();
                lab.setIdLaboratorio(idLaboratorio);
                lab.setNombre(nombre);
                lab.setTipo(tipo);
                lab.setTelefono(telefono);
                //insercion de datos obtenidos del layout a la bd
                dbFarmHelper.abrir();
                regInsertados = dbFarmHelper.insertar(lab);
                dbFarmHelper.cerrar();
                Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Insertar en Laboratorio", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiarCampos(View v){
        editIdLaboratorio.setText("");
        editNombre.setText("");
        editTipoLab.setText("");
        editTelefonoLab.setText("");
    }

}