package com.example.farmaciarikas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View; // Import for View.VISIBLE/GONE

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransaccionInsertarActivity extends AppCompatActivity {

    private EditText editIdTransaccion;
    private EditText editDui;
    private EditText editIdUsuario;
    private EditText editIdLocal;
    private EditText editFecha;
    private EditText editTipo;
    private Button btnGuardarTransaccion;
    private Button btnAnadirDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaccion_insertar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // Bindings
        editIdTransaccion    = findViewById(R.id.editTransaccionId);
        editDui              = findViewById(R.id.editTransaccionDui);
        editIdUsuario        = findViewById(R.id.editTransaccionIdUsuario);
        editIdLocal          = findViewById(R.id.editTransaccionIdLocal);
        editFecha            = findViewById(R.id.editTransaccionFecha);
        editTipo             = findViewById(R.id.editTransaccionTipo);
        btnGuardarTransaccion = findViewById(R.id.btnTransaccionGuardar);
        btnAnadirDetalles     = findViewById(R.id.btnTransaccionAnadirDetalles);
        btnAnadirDetalles.setVisibility(View.GONE);

        // Fecha por defecto
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editFecha.setText(sdf.format(new Date()));
        editFecha.setFocusable(false);
        editFecha.setOnClickListener(v -> mostrarDatePicker());

        btnGuardarTransaccion.setOnClickListener(v -> guardarTransaccion());
        btnAnadirDetalles.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(editIdTransaccion.getText().toString().trim());
                Intent i = new Intent(TransaccionInsertarActivity.this,
                        DetalleTransaccionMenuActivity.class);
                i.putExtra("IDTRANSACCION", id);
                startActivity(i);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID de Transacción no válido para añadir detalles.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, day) -> {
                    String f = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d",
                            year, month + 1, day);
                    editFecha.setText(f);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void guardarTransaccion() {
        String idStr    = editIdTransaccion.getText().toString().trim();
        String duiStr   = editDui.getText().toString().trim();
        String usrStr   = editIdUsuario.getText().toString().trim();
        String locStr   = editIdLocal.getText().toString().trim();
        String fechaStr = editFecha.getText().toString().trim();
        String tipoStr  = editTipo.getText().toString().trim();

        // Validar campos que son estrictamente obligatorios para insertar
        if (idStr.isEmpty() || fechaStr.isEmpty() || tipoStr.isEmpty()) {
            Toast.makeText(this,
                    "ID Transacción, Fecha y Tipo son obligatorios.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int idTrans = Integer.parseInt(idStr);

            String idUsuarioFinal = usrStr.isEmpty() ? null : usrStr; // idUsuario es String

            Integer idLocalFinal = null;
            if (!locStr.isEmpty()) {
                try {
                    idLocalFinal = Integer.parseInt(locStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "ID Local debe ser un número válido o estar vacío.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Transaccion t = new Transaccion(
                    idTrans,
                    duiStr.isEmpty() ? null : duiStr,
                    idUsuarioFinal,
                    idLocalFinal,
                    fechaStr,
                    tipoStr
            );
            long res = t.insert();

            Toast.makeText(this,
                    "Transacción guardada (ID: " + idTrans + ")",
                    Toast.LENGTH_LONG).show();

            editIdTransaccion.setEnabled(false);
            // Opcional: deshabilitar otros campos también
            editDui.setEnabled(false);
            editIdUsuario.setEnabled(false);
            editIdLocal.setEnabled(false);
            editFecha.setEnabled(false);
            editTipo.setEnabled(false);

            btnGuardarTransaccion.setEnabled(false);
            btnAnadirDetalles.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) { // Principalmente para idTrans
            Toast.makeText(this,
                    "Formato numérico inválido para ID Transacción.",
                    Toast.LENGTH_SHORT).show();
        } catch (android.database.SQLException e) {
            Toast.makeText(this,
                    "Error al insertar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error inesperado: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}