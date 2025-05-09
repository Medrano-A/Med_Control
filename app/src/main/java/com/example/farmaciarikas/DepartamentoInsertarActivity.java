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

public class DepartamentoInsertarActivity extends Activity {

    // Declaración arriba del onCreate
    private Spinner spinnerIds;
    ControlDBFarmacia dbFarmHelper;

    EditText editNombreDpto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_departamento_insertar);

        dbFarmHelper = new ControlDBFarmacia(this);
        editNombreDpto = (EditText) findViewById(R.id.dptoNomInsertarEdit);

        // Inicializar y configurar el Spinner
        spinnerIds = findViewById(R.id.dptoIdInsertarSpinner);
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

    public void insertarDpto(View v){
        String idDpto = spinnerIds.getSelectedItem().toString();
        int id = Integer.parseInt(idDpto);
        String nombreDpto = editNombreDpto.getText().toString();
        String regInserts;
        Departamento d = new Departamento();
        d.setIdDepartamento(id);
        d.setNombre(nombreDpto);
        dbFarmHelper.abrir();
        regInserts = dbFarmHelper.insertar(d);
        dbFarmHelper.cerrar();
        Toast.makeText(this, regInserts, Toast.LENGTH_SHORT).show();
    }

    public void limpiarCampos(View v){
        editNombreDpto.setText("");
    }

}