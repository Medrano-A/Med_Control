package com.example.farmaciarikas;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class DetalleTransaccionInsertarActivity extends AppCompatActivity {

    private EditText editIdDetalle;
    private EditText editIdTransaccion;
    private EditText editCantidad;
    private EditText editPrecioUnitario;
    private EditText editSubtotal;
    private Button btnDetalleGuardar;
    private Button btnDetalleVerDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_transaccion_insertar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                });

        // Enlace de vistas
        editIdDetalle        = findViewById(R.id.editDetalleId);
        editIdTransaccion    = findViewById(R.id.editDetalleIdTransaccion);
        editCantidad         = findViewById(R.id.editDetalleCantidad);
        editPrecioUnitario   = findViewById(R.id.editDetallePrecioUnitario);
        editSubtotal         = findViewById(R.id.editDetalleSubtotal);
        btnDetalleGuardar    = findViewById(R.id.btnDetalleGuardar);
        btnDetalleVerDetalles = findViewById(R.id.btnDetalleVerDetalles);

        btnDetalleVerDetalles.setVisibility(Button.GONE);

        // TextWatcher para recálculo automático
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                actualizarSubtotal();
            }
        };
        editCantidad.addTextChangedListener(watcher);
        editPrecioUnitario.addTextChangedListener(watcher);

        // Acciones de botones
        btnDetalleGuardar.setOnClickListener(v -> guardarDetalle());
        btnDetalleVerDetalles.setOnClickListener(v -> {
            try {
                int idTrans = Integer.parseInt(
                        editIdTransaccion.getText().toString().trim()
                );
                Intent i = new Intent(
                        DetalleTransaccionInsertarActivity.this,
                        DetalleTransaccionMenuActivity.class
                );
                i.putExtra("IDTRANSACCION", idTrans);
                startActivity(i);
            } catch (NumberFormatException ignored) { }
        });
    }

    // Método que recalcula y muestra el subtotal
    private void actualizarSubtotal() {
        String cantStr   = editCantidad.getText().toString().trim();
        String precioStr = editPrecioUnitario.getText().toString().trim();
        if (!cantStr.isEmpty() && !precioStr.isEmpty()) {
            try {
                int cantidad       = Integer.parseInt(cantStr);
                double precioUnit  = Double.parseDouble(precioStr);
                double subtotalCalc = cantidad * precioUnit;
                editSubtotal.setText(
                        String.format(Locale.getDefault(), "%.2f", subtotalCalc)
                );
            } catch (NumberFormatException e) {
                editSubtotal.setText("");
            }
        } else {
            editSubtotal.setText("");
        }
    }

    // Lógica de inserción, leyendo el subtotal ya calculado
    private void guardarDetalle() {
        String idDetStr    = editIdDetalle.getText().toString().trim();
        String idTransStr  = editIdTransaccion.getText().toString().trim();
        String cantStr     = editCantidad.getText().toString().trim();
        String precioStr   = editPrecioUnitario.getText().toString().trim();
        String subStr      = editSubtotal.getText().toString().trim();

        if (idDetStr.isEmpty() || idTransStr.isEmpty()
                || cantStr.isEmpty() || precioStr.isEmpty() || subStr.isEmpty()) {
            Toast.makeText(this,
                    "Complete todos los campos.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            int    idDetalle     = Integer.parseInt(idDetStr);
            int    idTransaccion = Integer.parseInt(idTransStr);
            int    cantidad      = Integer.parseInt(cantStr);
            double precioUnit    = Double.parseDouble(precioStr);
            double subtotal      = Double.parseDouble(subStr);

            DetalleTransaccion detalle = new DetalleTransaccion(
                    idDetalle, idTransaccion, cantidad, subtotal, precioUnit
            );
            detalle.insert();

            Toast.makeText(this,
                    "Detalle insertado correctamente.",
                    Toast.LENGTH_SHORT
            ).show();

            editIdDetalle.setEnabled(false);
            btnDetalleGuardar.setEnabled(false);
            btnDetalleVerDetalles.setVisibility(Button.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Formato numérico inválido.",
                    Toast.LENGTH_SHORT
            ).show();
        } catch (SQLException e) {
            Toast.makeText(this,
                    "Error al insertar: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
