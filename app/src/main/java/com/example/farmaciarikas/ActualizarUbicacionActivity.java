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

public class ActualizarUbicacionActivity extends AppCompatActivity {
    EditText etBuscarId;
    EditText tvResultadoId;
    EditText tvResultadoNombre;
    EditText tvResultadoMarca;
    EditText tvResultadoDistrito;
    ControlDBFarmacia helper;
    Ubicacion ubicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_ubicacion);
        helper = new ControlDBFarmacia(this);
        etBuscarId =(EditText) findViewById(R.id.etBuscarId);
        tvResultadoId = (EditText) findViewById(R.id.etResultadoId);
        tvResultadoNombre = (EditText) findViewById(R.id.etResultadoNombre);
        tvResultadoMarca = (EditText) findViewById(R.id.etResultadoMarca);
        tvResultadoDistrito = (EditText) findViewById(R.id.etResultadoDistrito);

    }
    public void buscarUbicacion(View view) {
        helper.abrir();
        ubicacion = helper.consultarUbicacion(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (ubicacion != null) {
            tvResultadoId.setText(String.format("%d", ubicacion.getIdUbicacion()));
            tvResultadoNombre.setText(String.format("%s", ubicacion.getDetalle()));
            tvResultadoDistrito.setText(String.format("%d", ubicacion.getIdDistrito()));
            tvResultadoMarca.setText(String.format("%d", ubicacion.getIdMarca()));
        }else{
            Toast.makeText(this, "La Ubicación con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }
    }

    public void actualizarUbicacion(View view) {
        ubicacion.setDetalle(tvResultadoNombre.getText().toString());
        ubicacion.setIdDistrito(Integer.parseInt(tvResultadoDistrito.getText().toString()));
        ubicacion.setIdMarca(Integer.parseInt(tvResultadoMarca.getText().toString()));
        helper.abrir();
        String estado = helper.actualizar(ubicacion);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
}