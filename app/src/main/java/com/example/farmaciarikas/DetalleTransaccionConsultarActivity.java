package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleTransaccionConsultarActivity extends AppCompatActivity {

    private EditText editDetalleIdConsulta;
    private Button btnDetalleBuscarConsulta;
    private TextView textDetalleIdTransaccion;
    private TextView textDetalleCantidad;
    private TextView textDetallePrecioUnitario;
    private TextView textDetalleSubtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_transaccion_consultar);

        // Vinculación de vistas
        editDetalleIdConsulta        = findViewById(R.id.editDetalleIdConsulta);
        btnDetalleBuscarConsulta     = findViewById(R.id.btnDetalleBuscarConsulta);
        textDetalleIdTransaccion     = findViewById(R.id.textDetalleIdTransaccion);
        textDetalleCantidad          = findViewById(R.id.textDetalleCantidad);
        textDetallePrecioUnitario    = findViewById(R.id.textDetallePrecioUnitario);
        textDetalleSubtotal          = findViewById(R.id.textDetalleSubtotal);

        btnDetalleBuscarConsulta.setOnClickListener(v -> buscarDetalle());
    }

    private void buscarDetalle() {
        String idStr = editDetalleIdConsulta.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de detalle.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        limpiarCampos();

        try {
            int idDetalle = Integer.parseInt(idStr);
            DetalleTransaccion d = DetalleTransaccion.getByPk(idDetalle);
            if (d != null) {
                textDetalleIdTransaccion.setText("ID Transacción: " + d.getIdTransaccion());
                textDetalleCantidad.setText("Cantidad: " + d.getCantidad());
                textDetallePrecioUnitario.setText("Precio Unitario: " + d.getPrecioUnitario());
                textDetalleSubtotal.setText("Subtotal: " + d.getSubtotal());

                Toast.makeText(this,
                        "Detalle encontrado.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "No existe detalle con ID " + idDetalle,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Formato de ID inválido.",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error al consultar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        textDetalleIdTransaccion.setText("ID Transacción:");
        textDetalleCantidad.setText("Cantidad:");
        textDetallePrecioUnitario.setText("Precio Unitario:");
        textDetalleSubtotal.setText("Subtotal:");
    }
}
