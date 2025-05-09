package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearUbicacionActivity extends AppCompatActivity {
    EditText idUbicacion;
    EditText descripcion;
    EditText idMarca;
    EditText idDistrito;
    ControlDBFarmacia helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_ubicacion);
        helper = new ControlDBFarmacia(this);

        idUbicacion = (EditText) findViewById(R.id.etIdUbicacion);
        descripcion = (EditText) findViewById(R.id.etNombreUbicacion);
        idMarca = (EditText) findViewById(R.id.etIdMarca);
        idDistrito = (EditText) findViewById(R.id.etIdDistrito);
    }
    public void crearUbicacion(View v) {
        String regInsertados;
        Ubicacion nuevaUbi = recogerDatos();
        helper.abrir();
        regInsertados=helper.insertar(nuevaUbi);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        limpiar();
    }
    public Ubicacion recogerDatos(){
        String idubiT = idUbicacion.getText().toString().trim();
        int idUbi=Integer.parseInt(idubiT);
        String descrip=descripcion.getText().toString().trim();
        String idmarT = idMarca.getText().toString().trim();
        int idMar=Integer.parseInt(idmarT);
        String iddisT = idDistrito.getText().toString().trim();
        int idDis=Integer.parseInt(iddisT);

        return new Ubicacion(idUbi,idDis,idMar,descrip);
    }

    public void limpiar(){
        descripcion.setText("");
        idUbicacion.setText("");
        idMarca.setText("");
        idDistrito.setText("");
    }
}