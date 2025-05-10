package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TransaccionMenuActivity extends ListActivity {
    private String[] menu;
    private Class<?>[] activityClasses = {
            TransaccionInsertarActivity.class,
            TransaccionConsultarActivity.class,
            TransaccionActualizarActivity.class,
            TransaccionEliminarActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu = getResources().getStringArray(R.array.menu_items_transaccion);
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
            startActivity(new Intent(this, activityClasses[position]));
        }
    }
}
