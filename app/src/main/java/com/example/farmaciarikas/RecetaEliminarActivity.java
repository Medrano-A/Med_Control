package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.SQLException;

public class RecetaEliminarActivity extends AppCompatActivity {

    EditText editIdRecetaEliminar;
    Button btnEliminarReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_eliminar); // Asegúrate de crear este layout

        editIdRecetaEliminar = findViewById(R.id.editRecetaId_eliminar); // ID del layout
        btnEliminarReceta = findViewById(R.id.btnRecetaEliminar_confirmar); // ID del layout

        btnEliminarReceta.setOnClickListener(v -> confirmarEliminacion());
    }

    private void confirmarEliminacion() {
        String idStr = editIdRecetaEliminar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese ID de Receta a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar Eliminación")
                .setMessage("¿Está seguro de que desea eliminar la receta con ID " + idStr + "? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarReceta(idStr))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void eliminarReceta(String idRecetaStr) {
        try {
            int idReceta = Integer.parseInt(idRecetaStr);
            Receta receta = new Receta(idReceta, "", 0, "", "", 0, ""); // Se necesita un objeto para llamar a delete, los otros campos no importan para delete si se usa SEL_PK
            // O, de forma más segura, buscarla primero para asegurar que existe:
            // Receta receta = Receta.getByPk(idReceta);
            // if (receta == null) {
            //     Toast.makeText(this, getString(R.string.dialog_msg_error_receta_no_encontrada), Toast.LENGTH_SHORT).show();
            //     return;
            // }
            // Pero el método delete() de tu ORM ya tiene un check de `exists()`.

            long resultado = receta.delete(); // USA TU ORM

            if (resultado > 0) { // delete devuelve el número de filas afectadas
                Toast.makeText(this, getString(R.string.dialog_msg_receta_eliminado), Toast.LENGTH_LONG).show();
                editIdRecetaEliminar.setText("");
            } else {
                // Esto podría pasar si la receta no existía y delete() no lanzó excepción por ello.
                Toast.makeText(this, getString(R.string.dialog_msg_error_eliminar_receta) + " (No se encontró o no se afectaron filas)", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID de Receta debe ser numérico.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) { // Captura la SQLException (ej. tiene detalles asociados)
            Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}