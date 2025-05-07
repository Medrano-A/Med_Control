package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleRecetaMenuActivity extends ListActivity {

    //CRUD de la tabla Detalle Receta
    String[] menu;//={"Insertar Detalle Receta","Consultar Detalle Receta", "Actualizar Detalle Receta","Eliminar Detalle Receta"};
    String[] activities={"DetalleRecetaInsertarActivity", "DetalleRecetaConsultarActivity", "DetalleRecetaActualizarActivity", "DetalleRecetaEliminarActivity"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu = getResources().getStringArray(R.array.menu_items_detalle_receta);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        if(position!=4){
            String nombreValue=activities[position];
            try{
                Class<?> clase=Class.forName("com.example.farmaciarikas."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }else{
        }
    }
}