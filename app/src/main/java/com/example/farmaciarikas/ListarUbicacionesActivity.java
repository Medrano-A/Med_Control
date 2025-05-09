package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListarUbicacionesActivity extends AppCompatActivity {
    EditText etBuscarId;
    TextView tvResultadoId;
    TextView tvResultadoNombre;
    TextView tvResultadoMarca;
    TextView tvResultadoDistrito;
    CardView cardResultado;
    ControlDBFarmacia helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_ubicaciones);
        helper = new ControlDBFarmacia(this);
        etBuscarId =(EditText) findViewById(R.id.etBuscarId);
        tvResultadoId = (TextView) findViewById(R.id.tvResultadoId);
        tvResultadoNombre = (TextView) findViewById(R.id.tvResultadoNombre);
        tvResultadoMarca = (TextView) findViewById(R.id.tvResultadoMarca);
        tvResultadoDistrito = (TextView) findViewById(R.id.tvResultadoDistrito);
        cardResultado = findViewById(R.id.cardResultado);

    }
    public void buscarUbicacion(View view) {
        helper.abrir();
        Ubicacion ubicacion = helper.consultarUbicacion(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (ubicacion != null) {
            tvResultadoId.setText(String.format("Id Ubicación: %d", ubicacion.getIdUbicacion()));
            tvResultadoNombre.setText(String.format("Detalle: %s", ubicacion.getDetalle()));
            tvResultadoDistrito.setText(String.format("ID Distrito: %d", ubicacion.getIdDistrito()));
            tvResultadoMarca.setText(String.format("ID Marca: %d", ubicacion.getIdMarca()));


            cardResultado.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "La Ubicación con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }

    }
}