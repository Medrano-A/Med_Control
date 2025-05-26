package com.example.farmaciarikas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArticuloEliminar extends AppCompatActivity {

    private EditText etBuscarId;
    private CardView cardResultado;
    private TextView tvResultadoId, tvResultadoIdDistribuidor, tvResultadoNombreArticulo, tvResultadoClasificacion;
    ControlDBFarmacia helper;
    Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulo_eliminar);
        helper = new ControlDBFarmacia(this);
        etBuscarId = findViewById(R.id.etBuscarId);
        cardResultado = findViewById(R.id.cardResultado);
        tvResultadoId = findViewById(R.id.tvResultadoId);
        tvResultadoNombreArticulo = findViewById(R.id.tvResultadoNombreArticulo);
        tvResultadoClasificacion = findViewById(R.id.tvResultadoClasificacion);

        tvResultadoIdDistribuidor = findViewById(R.id.tvResultadoIdDistribuidor);
    }

    public void buscarArticulo(View view) {
        limpiar();
        helper.abrir();
        articulo = helper.consultarArticulo(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (articulo != null) {
            tvResultadoId.setText(String.format("ID Articulo: %d", articulo.getIdArticulo()));
            tvResultadoIdDistribuidor.setText(String.format("ID Distribuidor: %d", articulo.getIdDistribuidor()));
            tvResultadoNombreArticulo.setText(String.format("Nombre Articulo: %s", articulo.getNombreArticulo()));
            tvResultadoClasificacion.setText(String.format("Clasificacion: %s", articulo.getClasificacion()));
            cardResultado.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "El stock con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }
    }

    public void limpiar() {
        tvResultadoId.setText("");
        tvResultadoIdDistribuidor.setText("");
        tvResultadoNombreArticulo.setText("");
        tvResultadoClasificacion.setText("");
        cardResultado.setVisibility(View.INVISIBLE);
    }

    public void borrarArticulo(View view) {
        helper.abrir();
        helper.eliminarArticulo(articulo.getIdArticulo());
        helper.cerrar();
        Toast.makeText(this, "Articulo eliminado correctamente", Toast.LENGTH_LONG).show();
    }
}