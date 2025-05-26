package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleTransaccionEliminarActivity extends AppCompatActivity {

    private EditText editDetalleIdEliminar;
    private Button btnDetalleEliminarConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_transaccion_eliminar);

        editDetalleIdEliminar      = findViewById(R.id.editDetalleIdEliminar);
        btnDetalleEliminarConfirmar = findViewById(R.id.btnDetalleEliminarConfirmar);

        btnDetalleEliminarConfirmar.setOnClickListener(v -> solicitarConfirmacion());
    }

    private void solicitarConfirmacion() {
        String idStr = editDetalleIdEliminar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de detalle.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar detalle con ID " + idStr + "?")
                .setPositiveButton("Eliminar", (dlg, which) -> eliminarDetalle(idStr))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void eliminarDetalle(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            DetalleTransaccion d = DetalleTransaccion.getByPk(id);
            if (d == null) {
                Toast.makeText(this,
                        "No existe detalle con ID " + id,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            d.delete();
            Toast.makeText(this,
                    "Detalle eliminado correctamente.",
                    Toast.LENGTH_LONG).show();
            editDetalleIdEliminar.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Formato de ID inválido.",
                    Toast.LENGTH_SHORT).show();
        } catch (android.database.SQLException e) {
            Toast.makeText(this,
                    "Error al eliminar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
