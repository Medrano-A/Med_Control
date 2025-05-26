package com.example.farmaciarikas;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity  {
    String[] menu={"Menu GA21090","Menu MM21030","Menu MM22108","Menu GD21001", "Menu PG22010",  "Servicios web","LLenar Base de Datos FarmaciaRikas"};
    String[] activities={"GA21090Activity","MM21030Activity","MM22108Activity","GD21001Activity","PG22010Activity","ServiciosActivity"};
    ControlDBFarmacia helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
        helper = new ControlDBFarmacia(this);
        Model.init(helper.DBHelper);


    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

<<<<<<< HEAD
        if (position != 5) {
=======
        if (position != 6) {
>>>>>>> ga21090
            String nombreValue = activities[position];
            try {
                Class<?> clase = Class.forName("com.example.farmaciarikas." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Usamos SharedPreferences para verificar si el botón ya fue presionado
            SharedPreferences prefs = getSharedPreferences("FarmaciaPrefs", MODE_PRIVATE);
            boolean isButtonDisabled = prefs.getBoolean("isButtonDisabled", false);

            if (isButtonDisabled) {
                // Notificar al usuario que el botón ya está deshabilitado
                Toast.makeText(this, "El llenado de base de datos ya se realizó .", Toast.LENGTH_SHORT).show();
            } else {
                // Llenar la base de datos
                helper = new ControlDBFarmacia(this);

                helper.abrir();
                String tost = helper.llenadoTablas();
                helper.cerrar();
                Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();

                // Deshabilitar el botón permanentemente
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isButtonDisabled", true);
                editor.apply();
            }
        }
    }

}