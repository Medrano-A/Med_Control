package com.example.farmaciarikas;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class ServiciosActivity extends ListActivity  {
    String[] menu={
            "Servicio Ingresar Departamento",
            "Servicio Consultar Departamento",
            "Servicio Ingresar Doctor",
            "Servicio Consultar Doctor",
            "Servicio Editar Doctor",
            "Servicio Eliminar Doctor","Serivicio Ingresar Marca",
            "Servicio Consultar Marca"};
    String[]
            activities={
            "InsertarDepartamentoService",
            "ConsultarDepartamentoService",
            "InsertarDoctorService",
            "ConsultarDoctorService"
            };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
    }
    @Override
    protected void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l, v, position, id);

            String nombreValue=activities[position];
            try{
                Class<?>
                        clase=Class.forName("com.example.farmaciarikas."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }

    }
}