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

public class MunicipioActualizarActivity extends Activity {

    ControlDBFarmacia dbFarmHelper;

    EditText editIdMunicipio, editIdDepartamentoMun, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_actualizar);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMunicipio = (EditText) findViewById(R.id.muniIdActuEdit);
        editIdDepartamentoMun = (EditText) findViewById(R.id.depMuniIdActuEdit);
        editNombre = (EditText) findViewById(R.id.muniNomActuEdit);
    }

    public void consMuni(View v) {
        try{
            if(editIdMunicipio.getText().toString().isEmpty() || editIdDepartamentoMun.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de ID Municipio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                int idMuni, idDepto;
                idMuni = Integer.parseInt(editIdMunicipio.getText().toString());
                idDepto = Integer.parseInt(editIdDepartamentoMun.getText().toString());
                dbFarmHelper.abrir();
                Municipio muni = dbFarmHelper.consultarMuni(idMuni, idDepto);
                dbFarmHelper.cerrar();
                if(muni == null){
                    Toast.makeText(this, "Municipio no registrado", Toast.LENGTH_SHORT).show();
                }else{
                    editNombre.setText(muni.getNombre());
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "A ocurrido un error durante la ejecucion en Consultar en Municipio", Toast.LENGTH_SHORT).show();
        }
    }

    public void actuCampos(View v){
        try{
            if(editIdMunicipio.getText().toString().isEmpty() || editIdDepartamentoMun.getText().toString().isEmpty()){
                Toast.makeText(this, "Los Campos de Municipio se esta enviando vacio, por favor completar", Toast.LENGTH_SHORT).show();
            }else{
                Municipio muni = new Municipio();
                muni.setIdMunicipio(Integer.parseInt(editIdMunicipio.getText().toString()));
                muni.setIdDepartamento(Integer.parseInt(editIdDepartamentoMun.getText().toString()));
                muni.setNombre(editNombre.getText().toString());
                dbFarmHelper.abrir();
                String estado = dbFarmHelper.actualizar(muni);
                dbFarmHelper.cerrar();
                Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error en Actualizar Municipio", Toast.LENGTH_SHORT).show();
            Log.e("LAB_ERROR", "Error al Actualizar Municipio", e);
        }
    }

    public void limpiarCampos(View v){
        editIdMunicipio.setText("");
        editIdDepartamentoMun.setText("");
        editNombre.setText("");
    }
}