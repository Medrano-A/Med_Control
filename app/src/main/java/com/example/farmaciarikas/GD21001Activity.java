package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GD21001Activity extends ListActivity {

    //menu y activities
    String[] menu; //= {"Tabla Transacciones","Tabla Marca", "Tabla Laboratorio"};
    String[] activities = {"DepartamentoMenuActivity","MunicipioMenuActivity","DistritoMenuActivity","MarcaMenuActivity", "LaboratorioMenuActivity"};
    //paquete: com.example.farmaciarikas.
    ControlDBFarmacia bdHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueLight));

        menu = menu = getResources().getStringArray(R.array.menu_items_GD21001);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        bdHelper = new ControlDBFarmacia(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id){
        super.onListItemClick(l,v,pos,id);
        l.getChildAt(pos).setBackgroundColor(ContextCompat.getColor(this, R.color.blueDark));
        if(pos!=5){
            String nomVal = activities[pos];
            try{
                Class<?> acti = Class.forName("com.example.farmaciarikas." + nomVal);
                Intent i = new Intent(this, acti);
                this.startActivity(i);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }else{
            bdHelper.abrir();
            String tost = bdHelper.llenadoTablasGD21001();
            bdHelper.cerrar();
            Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
        }
    }
}