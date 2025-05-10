package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecetaMenuActivity extends ListActivity {
    // 1) Carga los textos desde el string-array
    private String[] menu;

    // 2) Define las Activities destino en el mismo orden
    private Class<?>[] activityClasses = {
            RecetaInsertarActivity.class,
            RecetaConsultarActivity.class,
            RecetaActualizarActivity.class,
            RecetaEliminarActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 3) Obtén el arreglo desde recursos
        menu = getResources().getStringArray(R.array.menu_items_receta);

        // 4) Conecta el arreglo al ListView
        setListAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menu
        ));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position < activityClasses.length) {
            // 5) Lanza la Activity correspondiente al CRUD
            Intent intent = new Intent(this, activityClasses[position]);
            startActivity(intent);
        }
        // (No hay opción extra por defecto)
    }
}
