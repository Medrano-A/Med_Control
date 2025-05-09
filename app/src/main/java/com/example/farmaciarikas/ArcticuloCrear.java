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

public class ArcticuloCrear extends AppCompatActivity {
    EditText etIdArticulo;
    EditText etIdDistribuidor;
    EditText etnombreArticulo;
    EditText etClasificacion;

    ControlDBFarmacia helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_arcticulo_crear);
        helper = new ControlDBFarmacia(this);
        etIdArticulo = (EditText) findViewById(R.id.etIdArticulo);
        etIdDistribuidor = (EditText) findViewById(R.id.etIdDistribuidor);
        etnombreArticulo = (EditText) findViewById(R.id.etnombreArticulo);
        etClasificacion = (EditText) findViewById(R.id.etClasificacion);
    }
    public void registrarArticulo(View view) {

        String regInsertados;
        Articulo nuevoArticulo = recogerDatos();
        helper.abrir();
        regInsertados=helper.insertar(nuevoArticulo);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        limpiar();
    }
    public Articulo recogerDatos() {

        String idArticuloT = etIdArticulo.getText().toString().trim();
        String idDistribuidorT = etIdDistribuidor.getText().toString().trim();
        String nombreArticuloT = etnombreArticulo.getText().toString().trim();
        String clasificacionT = etClasificacion.getText().toString().trim();

        int idArticulo = Integer.parseInt(idArticuloT);
        int idDistribuidor = Integer.parseInt(idDistribuidorT);
        String nombreArticulo = nombreArticuloT;
        String clasificacion = clasificacionT;

        return new Articulo(idArticulo, idDistribuidor, nombreArticulo, clasificacion);

    }
    public void limpiar(){
       etIdArticulo.setText("");
       etIdDistribuidor.setText("");
       etnombreArticulo.setText("");
       etClasificacion.setText("");

    }
}