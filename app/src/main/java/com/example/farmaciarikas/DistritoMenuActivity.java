package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DistritoMenuActivity extends ListActivity {

    //menu y activities
    String[] menu; // = {"Insertar Laboratorio", "Actualizar Laboratorio", "Consultar Laboratorio", "Eliminar Laboratorio"};
    String[] activities = {"DepartamentoInsertarActivity", "DepartamentoActualizarActivity", "DepartamentoConsultarActivity", "DepartamentoEliminarActivity"};
    //paquete: com.example.farmaciarikas.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setBackgroundColor(ContextCompat.getColor(this, R.color.aquaLight));

        menu = getResources().getStringArray(R.array.menu_items_Distrito);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id){
        super.onListItemClick(l,v,pos,id);

        String nomVal = activities[pos];

        l.getChildAt(pos).setBackgroundColor(ContextCompat.getColor(this, R.color.aquaDark));

        try{
            Class<?> acti = Class.forName("com.example.farmaciarikas." + nomVal);
            Intent i = new Intent(this, acti);
            this.startActivity(i);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}