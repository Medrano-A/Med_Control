package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PG22010Activity extends ListActivity {
    // 1) El texto de cada opción, debe coincidir en orden con activityClasses[]
    private String[] menu = {
            "Recetas",
            "Transacciones",
            "Detalle Recetas",
            "Detalle Transacciones",
            "Poblar datos de prueba"
    };

    // 2) Las Activities a las que navigar, en el mismo orden
    private Class<?>[] activityClasses = {
            RecetaMenuActivity.class,
            TransaccionMenuActivity.class,
            DetalleRecetaMenuActivity.class,
            DetalleTransaccionMenuActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 3) Conecta el arreglo de strings al ListView estándar
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
            // 4) Lanzar la Activity correspondiente
            Intent intent = new Intent(this, activityClasses[position]);
            startActivity(intent);
        } else {
            // 5) Lógica especial para la última opción
            poblarDatosDePrueba();
        }
    }

    /** Inserta datos de ejemplo en la base (llamado desde el último ítem del menú) */
    private void poblarDatosDePrueba() {
        ControlDBFarmacia helper = new ControlDBFarmacia(this);
        helper.abrir();
        // Por ejemplo:
        // helper.insertar(new Receta(1, "12345678-9", 10, "Juan Pérez", "2025-05-08", 45, "Observaciones..."));
        // helper.insertar(new Transaccion(1, "12345678-9", 1, 1, "2025-05-08", "Venta"));
        // helper.insertarDetalleTransaccion(new DetalleTransaccion(1, 1, 2, 50.0, 25.0));
        helper.cerrar();
    }
}
