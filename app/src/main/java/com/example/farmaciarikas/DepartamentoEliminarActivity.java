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

public class DepartamentoEliminarActivity extends Activity {


    // Declaración arriba del onCreate
    private Spinner spinnerIds;
    ControlDBFarmacia dbFarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_departamento_eliminar);

        dbFarmHelper = new ControlDBFarmacia(this);

        // Inicializar y configurar el Spinner
        spinnerIds = findViewById(R.id.spinIdElim);
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

    public void elimDpto(View v) {
        String regElim;
        String id = spinnerIds.getSelectedItem().toString();
        int idDpto = Integer.parseInt(id);
        Departamento dep = new Departamento();
        dep.setIdDepartamento(idDpto);
        dbFarmHelper.abrir();
        regElim = dbFarmHelper.eliminar(dep);
        dbFarmHelper.cerrar();
        Toast.makeText(this, regElim, Toast.LENGTH_SHORT).show();
    }
}