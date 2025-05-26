package com.example.farmaciarikas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class DetalleTransaccionActualizarActivity extends AppCompatActivity {

    private EditText editDetalleIdBuscar;
    private Button btnDetalleBuscar;
    private LinearLayout layoutDatos;

    private TextView textDetalleIdTransaccion;
    private EditText editDetalleCantidad;
    private EditText editDetallePrecioUnitario;
    private EditText editDetalleSubtotal;
    private Button btnDetalleActualizarConfirmar;

    private DetalleTransaccion currentDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_transaccion_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                });

        editDetalleIdBuscar           = findViewById(R.id.editDetalleIdBuscar);
        btnDetalleBuscar              = findViewById(R.id.btnDetalleBuscar);
        layoutDatos                   = findViewById(R.id.layoutDatosDetalle);
        textDetalleIdTransaccion      = findViewById(R.id.textDetalleIdTransaccion);
        editDetalleCantidad           = findViewById(R.id.editDetalleCantidadActualizar);
        editDetallePrecioUnitario     = findViewById(R.id.editDetallePrecioUnitarioActualizar);
        editDetalleSubtotal           = findViewById(R.id.editDetalleSubtotalActualizar);
        btnDetalleActualizarConfirmar = findViewById(R.id.btnDetalleActualizarConfirmar);

        btnDetalleBuscar.setOnClickListener(v -> buscarDetalle());
        btnDetalleActualizarConfirmar.setOnClickListener(v -> actualizarDetalle());

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) { }
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) { }
            @Override public void afterTextChanged(Editable s) {
                recalcularSubtotal();
            }
        };
        editDetalleCantidad.addTextChangedListener(watcher);
        editDetallePrecioUnitario.addTextChangedListener(watcher);
    }

    private void buscarDetalle() {
        String idStr = editDetalleIdBuscar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de detalle.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            DetalleTransaccion d = DetalleTransaccion.getByPk(id);
            if (d == null) {
                Toast.makeText(this,
                        "No existe detalle con ID " + id,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            currentDetalle = d;
            layoutDatos.setVisibility(LinearLayout.VISIBLE);
            textDetalleIdTransaccion.setText(String.valueOf(d.getIdTransaccion()));
            editDetalleCantidad.setText(String.valueOf(d.getCantidad()));
            editDetallePrecioUnitario.setText(String.valueOf(d.getPrecioUnitario()));
            editDetalleSubtotal.setText(String.format(
                    Locale.getDefault(), "%.2f", d.getSubtotal()
            ));
            editDetalleIdBuscar.setEnabled(false);
            btnDetalleBuscar.setEnabled(false);
        } catch (NumberFormatException ex) {
            Toast.makeText(this,
                    "Formato de ID inválido.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void recalcularSubtotal() {
        String cant = editDetalleCantidad.getText().toString().trim();
        String pre  = editDetallePrecioUnitario.getText().toString().trim();
        if (!cant.isEmpty() && !pre.isEmpty()) {
            try {
                int cantidad      = Integer.parseInt(cant);
                double precio     = Double.parseDouble(pre);
                double subtotal   = cantidad * precio;
                editDetalleSubtotal.setText(
                        String.format(Locale.getDefault(), "%.2f", subtotal)
                );
            } catch (NumberFormatException e) {
                editDetalleSubtotal.setText("");
            }
        } else {
            editDetalleSubtotal.setText("");
        }
    }

    private void actualizarDetalle() {
        if (currentDetalle == null) return;

        String cantStr  = editDetalleCantidad.getText().toString().trim();
        String precStr  = editDetallePrecioUnitario.getText().toString().trim();
        String subStr   = editDetalleSubtotal.getText().toString().trim();

        if (cantStr.isEmpty() || precStr.isEmpty() || subStr.isEmpty()) {
            Toast.makeText(this,
                    "Todos los campos deben llenarse.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int cantidad    = Integer.parseInt(cantStr);
            double precio   = Double.parseDouble(precStr);
            double subtotal = Double.parseDouble(subStr);
            currentDetalle.setCantidad(cantidad);
            currentDetalle.setPrecioUnitario(precio);
            currentDetalle.setSubtotal(subtotal);
            currentDetalle.update();
            Toast.makeText(this,
                    "Detalle actualizado correctamente.",
                    Toast.LENGTH_LONG).show();
            btnDetalleActualizarConfirmar.setEnabled(false);
        } catch (NumberFormatException ex) {
            Toast.makeText(this,
                    "Formato numérico inválido.",
                    Toast.LENGTH_SHORT).show();
        } catch (android.database.SQLException ex) {
            Toast.makeText(this,
                    "Error al actualizar: " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        editDetalleIdBuscar.setEnabled(true);
        btnDetalleBuscar.setEnabled(true);
    }
}
