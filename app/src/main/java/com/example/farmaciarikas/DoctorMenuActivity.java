package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class DoctorMenuActivity extends ListActivity  {
    String[] menu={"Insertar Registro","Eliminar Registro","Consultar Registro",
            "Actualizar Registro"};
    String[]
            activities={"DoctorInsertarActivity","DoctorEliminarActivity","DoctorConsultarActivity",
            "DoctorActualizarActivity"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setBackgroundColor(Color.rgb(0, 0, 0));

        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l, v, position, id);

        String nombreValue=activities[position];

        l.getChildAt(position).setBackgroundColor(Color.rgb(255, 255, 255));

        try{
            Class<?> clase=Class.forName("com.example.farmaciarikas."+nombreValue);
            Intent inte = new Intent(this,clase);
            this.startActivity(inte);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}