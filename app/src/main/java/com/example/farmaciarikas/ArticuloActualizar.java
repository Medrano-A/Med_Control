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

public class ArticuloActualizar extends AppCompatActivity {
    EditText etIdArticulo;
    EditText etIdDistribuidor;
    EditText etnombreArticulo;
    EditText etClasificacion;
    EditText etBuscarId;
    Articulo articulo;
    ControlDBFarmacia helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulo_actualizar);
        helper = new ControlDBFarmacia(this);
        etBuscarId =(EditText) findViewById(R.id.etBuscarId);
        etIdArticulo = (EditText) findViewById(R.id.etIdArticulo);
        etIdDistribuidor = (EditText) findViewById(R.id.etIdDistribuidor);
        etnombreArticulo = (EditText) findViewById(R.id.etnombreArticulo);
        etClasificacion = (EditText) findViewById(R.id.etClasificacion);

    }
    public void buscarArticulo(View view) {
        limpiar();
        helper.abrir();
        articulo = helper.consultarArticulo(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (articulo != null) {
            etIdArticulo.setText(Integer.toString(articulo.getIdArticulo()));
            etIdDistribuidor.setText(Integer.toString(articulo.getIdDistribuidor()));
            etnombreArticulo.setText(articulo.getNombreArticulo());
            etClasificacion.setText(articulo.getClasificacion());
            etBuscarId.setText("");
        } else {
            Toast.makeText(this, "El articulo con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }
    }
    public void limpiar() {
        etIdArticulo.setText("");
        etIdDistribuidor.setText("");
        etnombreArticulo.setText("");
        etClasificacion.setText("");
    }
    public void actualizarArticulo(View view) {
        articulo.setIdDistribuidor(Integer.parseInt(etIdDistribuidor.getText().toString()));
        articulo.setNombreArticulo(etnombreArticulo.getText().toString());
        articulo.setClasificacion(etClasificacion.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(articulo);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

}