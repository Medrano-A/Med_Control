package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TransaccionConsultarActivity extends AppCompatActivity {

    private EditText editIdTransaccionConsulta;
    private Button btnBuscarTransaccionConsulta;
    private TextView textTransaccionDui,
            textTransaccionIdUsuario,
            textTransaccionIdLocal,
            textTransaccionFecha,
            textTransaccionTotal,
            textTransaccionTipo,
            labelDetalles;
    private LinearLayout containerDetalleTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_consultar);

        editIdTransaccionConsulta   = findViewById(R.id.editTransaccionIdConsulta);
        btnBuscarTransaccionConsulta = findViewById(R.id.btnTransaccionBuscarConsulta);

        textTransaccionDui           = findViewById(R.id.textTransaccionDui);
        textTransaccionIdUsuario     = findViewById(R.id.textTransaccionIdUsuario);
        textTransaccionIdLocal       = findViewById(R.id.textTransaccionIdLocal);
        textTransaccionFecha         = findViewById(R.id.textTransaccionFecha);
        textTransaccionTotal         = findViewById(R.id.textTransaccionTotal);
        textTransaccionTipo          = findViewById(R.id.textTransaccionTipo);

        labelDetalles                = findViewById(R.id.labelDetalles);
        containerDetalleTransaccion  = findViewById(R.id.containerDetalleTransaccion);

        labelDetalles.setVisibility(View.GONE);
        containerDetalleTransaccion.setVisibility(View.GONE);

        btnBuscarTransaccionConsulta.setOnClickListener(v -> buscarTransaccion());
    }

    private void buscarTransaccion() {
        String idStr = editIdTransaccionConsulta.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un ID de Transacción.", Toast.LENGTH_SHORT).show();
            return;
        }

        limpiarCampos();

        try {
            int id = Integer.parseInt(idStr);
            Transaccion t = Transaccion.getByPk(id);
            if (t == null) {
                Toast.makeText(this, "Transacción no encontrada.", Toast.LENGTH_SHORT).show();
                return;
            }

            textTransaccionDui.setText("DUI: " + t.getDui());
            textTransaccionIdUsuario.setText("ID Usuario: " + t.getIdUsuario());
            textTransaccionIdLocal.setText("ID Local: " + t.getIdLocal());
            textTransaccionFecha.setText("Fecha: " + t.getFecha());
            textTransaccionTotal.setText("Total: " + t.getTotal());
            textTransaccionTipo.setText("Tipo: " + t.getTipo());

            // Cargar todos los detalles
            DetalleTransaccion[] detalles = DetalleTransaccion.getByTransaccion(id);
            containerDetalleTransaccion.removeAllViews();
            if (detalles.length > 0) {
                labelDetalles.setVisibility(View.VISIBLE);
                containerDetalleTransaccion.setVisibility(View.VISIBLE);
                for (DetalleTransaccion d : detalles) {
                    TextView tv = new TextView(this);
                    tv.setText(
                            "ID Detalle: " + d.getIdDetalle()
                                    + "  Cant: " + d.getCantidad()
                                    + "  PU: " + d.getPrecioUnitario()
                                    + "  Subt: " + d.getSubtotal()
                    );
                    containerDetalleTransaccion.addView(tv);
                }
            }

            editIdTransaccionConsulta.setEnabled(false);
            Toast.makeText(this, "Transacción encontrada.", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato inválido de ID.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        textTransaccionDui.setText("DUI:");
        textTransaccionIdUsuario.setText("ID Usuario:");
        textTransaccionIdLocal.setText("ID Local:");
        textTransaccionFecha.setText("Fecha:");
        textTransaccionTotal.setText("Total:");
        textTransaccionTipo.setText("Tipo:");
        labelDetalles.setVisibility(View.GONE);
        containerDetalleTransaccion.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editIdTransaccionConsulta.setEnabled(true);
    }
}
