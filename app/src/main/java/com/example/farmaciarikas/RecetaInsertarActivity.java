package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.database.SQLException; // Importante

public class RecetaInsertarActivity extends AppCompatActivity {

    EditText editIdReceta, editDui, editIdDoctor, editNombrePaciente, editFecha, editEdad, editObservaciones;
    Button btnGuardarReceta, btnAnadirDetalles;
    // No necesitas ControlDBFarmacia helper aquí si solo usas el ORM Receta
    // y Receta.insert() ya valida Doctor. El DUI no se valida contra tabla Cliente.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_insertar);

        editIdReceta = findViewById(R.id.editRecetaId);
        editDui = findViewById(R.id.editRecetaDui);
        editIdDoctor = findViewById(R.id.editRecetaIdDoctor);
        editNombrePaciente = findViewById(R.id.editRecetaNombrePaciente);
        editFecha = findViewById(R.id.editRecetaFecha);
        editEdad = findViewById(R.id.editRecetaEdad);
        editObservaciones = findViewById(R.id.editRecetaObservaciones);
        btnGuardarReceta = findViewById(R.id.btnRecetaGuardar);
        btnAnadirDetalles = findViewById(R.id.btnRecetaAnadirDetalles);
        btnAnadirDetalles.setVisibility(View.GONE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editFecha.setText(sdf.format(new Date()));
        // Considera poner un ID por defecto o una forma de generarlo si no es autoincremental
        // editIdReceta.setText(String.valueOf(System.currentTimeMillis() % 100000));


        btnGuardarReceta.setOnClickListener(v -> guardarReceta());

        btnAnadirDetalles.setOnClickListener(v -> {
            try {
                int idReceta = Integer.parseInt(editIdReceta.getText().toString());
                Intent i = new Intent(RecetaInsertarActivity.this, DetalleRecetaMenuActivity.class);
                i.putExtra("ID_RECETA", idReceta);
                startActivity(i);
            } catch (NumberFormatException e) {
                Toast.makeText(RecetaInsertarActivity.this, "ID de Receta inválido.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarReceta() {
        String idRecetaStr = editIdReceta.getText().toString().trim();
        String duiStr = editDui.getText().toString().trim(); // Se guarda como texto, no se valida FK
        String idDoctorStr = editIdDoctor.getText().toString().trim();
        String nombrePacienteStr = editNombrePaciente.getText().toString().trim();
        String fechaStr = editFecha.getText().toString().trim();
        String edadStr = editEdad.getText().toString().trim();
        String observacionesStr = editObservaciones.getText().toString().trim();

        if (idRecetaStr.isEmpty() || idDoctorStr.isEmpty() || nombrePacienteStr.isEmpty() || fechaStr.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int idReceta = Integer.parseInt(idRecetaStr);
            int idDoctor = Integer.parseInt(idDoctorStr);
            int edad = Integer.parseInt(edadStr);

            Receta receta = new Receta(idReceta, duiStr, idDoctor, nombrePacienteStr, fechaStr, edad, observacionesStr);
            long resultado = receta.insert(); // USA TU ORM Receta

            if (resultado != -1) { // SQLite insert devuelve -1 en error, o rowID.
                Toast.makeText(this, getString(R.string.dialog_msg_receta_guardado) + " ID: " + idReceta, Toast.LENGTH_LONG).show();
                editIdReceta.setEnabled(false);
                btnGuardarReceta.setEnabled(false);
                btnAnadirDetalles.setVisibility(View.VISIBLE);
            } else {
                // Esto no debería pasar si insertOrThrow se usa y no hay duplicados,
                // o si la SQLException por FK de doctor se maneja.
                Toast.makeText(this, getString(R.string.dialog_msg_error_guardar_receta) + " (Resultado -1)", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en formato de ID Receta, ID Doctor o Edad.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) { // Captura la SQLException del ORM (ej. doctor no existe, o PK duplicada)
            Toast.makeText(this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}