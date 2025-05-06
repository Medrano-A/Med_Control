package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class UbicacionActivity extends AppCompatActivity {

    ListView listViewOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        listViewOpciones = findViewById(R.id.listViewOpciones);

        List<Opcion> opciones = new ArrayList<>();
        //opciones.add(new Opcion("Crear ubicación", R.drawable.ic_crearubicacion));
        //opciones.add(new Opcion("Consultar ubicaciones", R.drawable.ic_encontrarubicacion));
        //opciones.add(new Opcion("Actualizar ubicación", R.drawable.ic_editarubicacion));
        //opciones.add(new Opcion("Eliminar ubicación", R.drawable.ic_borrarubicacion));

        OpcionAdapter adapter = new OpcionAdapter(this, opciones);
        listViewOpciones.setAdapter(adapter);

        listViewOpciones.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, CrearUbicacionActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, ListarUbicacionesActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, ActualizarUbicacionActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(this, EliminarUbicacionActivity.class));
                    break;
            }
        });
    }
}

