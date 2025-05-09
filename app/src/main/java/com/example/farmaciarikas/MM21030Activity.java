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

public class MM21030Activity extends AppCompatActivity {

    ListView listViewOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        listViewOpciones = findViewById(R.id.listViewOpciones);

        List<Opcion> opciones = new ArrayList<>();
        opciones.add(new Opcion("Ubicaciones", R.drawable.ic_location));
        opciones.add(new Opcion("Stocks", R.drawable.ic_logostock));
        opciones.add(new Opcion("Articulo", R.drawable.ic_articulo));


        OpcionAdapter adapter = new OpcionAdapter(this, opciones);
        listViewOpciones.setAdapter(adapter);

        listViewOpciones.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, UbicacionActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, StockActivity.class));
                    break;

                case 2:
                    startActivity(new Intent(this, ArticuloActivity.class));
                    break;

            }
        });
    }
}