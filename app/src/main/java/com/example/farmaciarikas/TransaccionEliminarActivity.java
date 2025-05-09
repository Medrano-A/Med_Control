package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TransaccionEliminarActivity extends AppCompatActivity {

    private EditText editIdEliminar;
    private Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_eliminar);

        editIdEliminar = findViewById(R.id.editTransaccionIdEliminar);
        btnEliminar    = findViewById(R.id.btnTransaccionEliminarConfirmar);

        btnEliminar.setOnClickListener(v -> confirmarEliminacion());
    }

    private void confirmarEliminacion() {
        String idStr = editIdEliminar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de Transacción.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar transacción con ID " + idStr + "?")
                .setPositiveButton("Eliminar", (dlg, which) -> eliminarTransaccion(idStr))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void eliminarTransaccion(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            Transaccion t = Transaccion.getByPk(id);
            if (t == null) {
                Toast.makeText(this,
                        "No se encontró Transacción con ID " + id,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            t.delete();
            Toast.makeText(this,
                    "Transacción eliminada.",
                    Toast.LENGTH_LONG).show();
            editIdEliminar.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "ID inválido.",
                    Toast.LENGTH_SHORT).show();
        } catch (android.database.SQLException e) {
            Toast.makeText(this,
                    "Error al eliminar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error inesperado: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
