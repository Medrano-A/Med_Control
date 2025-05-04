package com.example.farmaciarikas;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class MainActivity extends ListActivity  {
    String[] menu={"Menu GA21090","Menu MM21030","Menu MM22108","Menu GD21001", "Menu PG22010"};
            String[]
    activities={"GA21090Activity","MM21030Activity","MM22108Activity","GD21001Activity","PG22010Activity"};
    ControlDBFarmacia helper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
        helper = new ControlDBFarmacia(this);
        helper.permisosUsuarios();
    }
    @Override
    protected void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l, v, position, id);
        if(position!=3){
            String nombreValue=activities[position];
            try{
                Class<?>
                        clase=Class.forName("com.example.farmaciarikas."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }else{
        }
    }
}