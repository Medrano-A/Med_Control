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

public class LaboratorioConsultarActivity extends Activity {

    //controles y db
    ControlDBFarmacia dbFarmHelper;

    EditText editIdLaboratorio, editNombre, editTipoLab, editTelefonoLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_consultar);

        dbFarmHelper = new ControlDBFarmacia(this);
        editIdLaboratorio = (EditText) findViewById(R.id.laboIdConsEdit);
        editNombre = (EditText) findViewById(R.id.laboNombreConsEdt);
        editTipoLab = (EditText) findViewById(R.id.laboTipoConsEdt);
        editTelefonoLab = (EditText) findViewById(R.id.laboTelConsEdt);

    }
    public void consultarLabo(View v){
        try{
            if(editIdLaboratorio.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de ID Laboratorio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                dbFarmHelper.abrir();
                Laboratorio l = dbFarmHelper.consultarLab(Integer.valueOf(editIdLaboratorio.getText().toString()));
                dbFarmHelper.cerrar();

                if(l == null){
                    Toast.makeText(this, "Laboratorio con ID: " + editIdLaboratorio.getText().toString() + " no encontrado", Toast.LENGTH_SHORT).show();
                }else{
                    editNombre.setText(l.getNombre());
                    editTipoLab.setText(l.getTipo());
                    editTelefonoLab.setText(l.getTelefono());
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Consultar en Laboratorio", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiarCampos(View v){
        editIdLaboratorio.setText("");
        editNombre.setText("");
        editTipoLab.setText("");
        editTelefonoLab.setText("");
    }
}