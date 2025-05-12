package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ArticuloActivity extends AppCompatActivity {

    ListView listViewOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        listViewOpciones = findViewById(R.id.listViewOpciones);

        List<Opcion> opciones = new ArrayList<>();
        opciones.add(new Opcion("Registrar Articulo", R.drawable.ic_agregararticulo));
        opciones.add(new Opcion("Consultar Articulo", R.drawable.ic_articulobuscar));
        opciones.add(new Opcion("Actualizar Articulo", R.drawable.ic_editararticulo));
        opciones.add(new Opcion("Eliminar Articulo", R.drawable.ic_eliminararticulo));

        OpcionAdapter adapter = new OpcionAdapter(this, opciones);
        listViewOpciones.setAdapter(adapter);

        listViewOpciones.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, ArcticuloCrear.class));
                    break;
                case 1:
                    startActivity(new Intent(this, ArticuloConsultar.class));
                    break;
                case 2:
                    startActivity(new Intent(this, ArticuloActualizar.class));
                    break;
                case 3:
                    startActivity(new Intent(this, ArticuloEliminar.class));
                    break;
            }
        });
    }
}