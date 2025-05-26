package com.example.farmaciarikas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View; // Import for View.VISIBLE/GONE

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class TransaccionActualizarActivity extends AppCompatActivity {

    private EditText editIdBuscar;
    private Button btnBuscar;
    private LinearLayout layoutDatos;

    private EditText editDui;
    private EditText editIdUsuario;
    private EditText editIdLocal;
    private EditText editFecha;
    private EditText editTipo;

    private Button btnConfirmar;
    private Button btnGestionarDetalles;

    private Transaccion currentTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaccion_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // Bindings
        editIdBuscar       = findViewById(R.id.editTransaccionIdBuscarActualizar);
        btnBuscar          = findViewById(R.id.btnTransaccionBuscarActualizar);
        layoutDatos        = findViewById(R.id.layoutDatosTransaccionActualizar);

        editDui            = findViewById(R.id.editTransaccionDuiActualizar);
        editIdUsuario      = findViewById(R.id.editTransaccionIdUsuarioActualizar);
        editIdLocal        = findViewById(R.id.editTransaccionIdLocalActualizar);
        editFecha          = findViewById(R.id.editTransaccionFechaActualizar);
        editTipo           = findViewById(R.id.editTransaccionTipoActualizar);

        btnConfirmar       = findViewById(R.id.btnTransaccionActualizarConfirmar);
        btnGestionarDetalles = findViewById(R.id.btnTransaccionGestionarDetallesActualizar);

        editFecha.setFocusable(false);
        editFecha.setOnClickListener(v -> mostrarDatePicker());

        btnBuscar.setOnClickListener(v -> buscarTransaccion());
        btnConfirmar.setOnClickListener(v -> actualizarTransaccion());
        btnGestionarDetalles.setOnClickListener(v -> abrirDetalles());
    }

    private void mostrarDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this,
                (dp, y, m, d) -> {
                    String f = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", y, m + 1, d);
                    editFecha.setText(f);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void buscarTransaccion() {
        String idStr = editIdBuscar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de Transacción.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Transaccion t = Transaccion.getByPk(id);
            if (t == null) {
                Toast.makeText(this,
                        "No se encontró Transacción con ID " + id,
                        Toast.LENGTH_SHORT).show();
                layoutDatos.setVisibility(View.GONE);
                btnGestionarDetalles.setVisibility(View.GONE);
                currentTransaccion = null;
                return;
            }

            currentTransaccion = t;
            layoutDatos.setVisibility(View.VISIBLE);
            btnConfirmar.setEnabled(true); // Habilitar botón de confirmar al encontrar
            btnGestionarDetalles.setVisibility(View.VISIBLE);


            editDui.setText(t.getDui() != null ? t.getDui() : "");
            editIdUsuario.setText(t.getIdUsuario() != null ? t.getIdUsuario() : ""); // idUsuario es String
            editIdLocal.setText(t.getIdLocal() != null ? String.valueOf(t.getIdLocal()) : ""); // idLocal es Integer
            editFecha.setText(t.getFecha());
            editTipo.setText(t.getTipo());

            editIdBuscar.setEnabled(false);
            btnBuscar.setEnabled(false);

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Formato inválido de ID.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarTransaccion() {
        if (currentTransaccion == null) return;

        String duiStr     = editDui.getText().toString().trim();
        String usrStr     = editIdUsuario.getText().toString().trim();
        String locStr     = editIdLocal.getText().toString().trim();
        String fechaStr   = editFecha.getText().toString().trim();
        String tipoStr    = editTipo.getText().toString().trim();

        // Validar campos que sí son estrictamente obligatorios
        if (fechaStr.isEmpty() || tipoStr.isEmpty()) {
            Toast.makeText(this,
                    "La Fecha y el Tipo de transacción son obligatorios.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            currentTransaccion.setDui(duiStr.isEmpty() ? null : duiStr);
            currentTransaccion.setIdUsuario(usrStr.isEmpty() ? null : usrStr); // idUsuario es String

            Integer idLocalVal = null;
            if (!locStr.isEmpty()) {
                try {
                    idLocalVal = Integer.parseInt(locStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "ID Local debe ser un número válido o estar vacío.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            currentTransaccion.setIdLocal(idLocalVal); // idLocal es Integer

            currentTransaccion.setFecha(fechaStr);
            currentTransaccion.setTipo(tipoStr);

            currentTransaccion.update();
            Toast.makeText(this,
                    "Transacción actualizada correctamente.",
                    Toast.LENGTH_LONG).show();

            // Opcional: deshabilitar botón de confirmar para evitar doble actualización
            // btnConfirmar.setEnabled(false);
            // btnGestionarDetalles.setVisibility(Button.VISIBLE); // Ya debería ser visible

        } catch (android.database.SQLException e) {
            Toast.makeText(this,
                    "Error al actualizar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) { // Captura general para otros errores inesperados
            Toast.makeText(this,
                    "Error inesperado al actualizar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void abrirDetalles() {
        if (currentTransaccion == null) {
            Toast.makeText(this, "Primero busque una transacción.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(this, DetalleTransaccionMenuActivity.class);
        i.putExtra("IDTRANSACCION", currentTransaccion.getIdTransaccion());
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Permitir nueva búsqueda al volver
        editIdBuscar.setEnabled(true);
        btnBuscar.setEnabled(true);
        // Opcional: ocultar datos y botón de detalles si no hay transacción actual
        if (currentTransaccion == null) {
            layoutDatos.setVisibility(View.GONE);
            btnGestionarDetalles.setVisibility(View.GONE);
        }
    }
}