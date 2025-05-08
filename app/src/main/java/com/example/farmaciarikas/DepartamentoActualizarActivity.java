package com.example.farmaciarikas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoActualizarActivity extends Activity {

    // Declaración arriba del onCreate
    private Spinner spinnerIds;
    ControlDBFarmacia dbFarmHelper;

    EditText editNombreDpto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_departamento_actualizar);

        dbFarmHelper = new ControlDBFarmacia(this);
        editNombreDpto = (EditText) findViewById(R.id.dptoNomActuEdit);

        // Inicializar y configurar el Spinner
        spinnerIds = findViewById(R.id.spinnerDepartamentoConsultaActu);
        List<String> departamentoIds = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            departamentoIds.add(String.format("%02d", i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                departamentoIds
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIds.setAdapter(adapter);
    }

    public void consDpto(View v){
        String idDpto = spinnerIds.getSelectedItem().toString();
        int id = Integer.parseInt(idDpto);
        dbFarmHelper.abrir();
        Departamento depCons = dbFarmHelper.consultarDpto(id);
        dbFarmHelper.cerrar();
        if(depCons == null){
            Toast.makeText(this, "Departamento con id " + spinnerIds.getSelectedItem().toString() + " no encontrado", Toast.LENGTH_SHORT).show();
        }else{
            editNombreDpto.setText(depCons.getNombre());
        }
    }

    public void actuCampos(View v){
        Departamento actuDepto = new Departamento();
        String idDpto = spinnerIds.getSelectedItem().toString();
        int id = Integer.parseInt(idDpto);
        actuDepto.setIdDepartamento(id);
        actuDepto.setNombre(editNombreDpto.getText().toString());
        dbFarmHelper.abrir();
        String estado = dbFarmHelper.actualizar(actuDepto);
        dbFarmHelper.cerrar();
        Toast.makeText(this, estado , Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(View v){
        editNombreDpto.setText("");
    }

}