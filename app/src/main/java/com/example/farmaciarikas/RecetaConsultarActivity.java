package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View; // Importar View

public class RecetaConsultarActivity extends AppCompatActivity {

    EditText editIdRecetaConsulta;
    Button btnBuscarRecetaConsulta, btnVerDetalles;
    TextView textDui, textIdDoctor, textNombrePaciente, textFecha, textEdad, textObservaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_consultar);

        editIdRecetaConsulta = findViewById(R.id.editRecetaIdConsulta);
        btnBuscarRecetaConsulta = findViewById(R.id.btnRecetaBuscarConsulta);
        btnVerDetalles = findViewById(R.id.btnRecetaVerDetallesConsulta);

        textDui = findViewById(R.id.textRecetaDui);
        textIdDoctor = findViewById(R.id.textRecetaIdDoctor);
        textNombrePaciente = findViewById(R.id.textRecetaNombrePaciente);
        textFecha = findViewById(R.id.textRecetaFecha);
        textEdad = findViewById(R.id.textRecetaEdad);
        textObservaciones = findViewById(R.id.textRecetaObservaciones);

        btnVerDetalles.setVisibility(View.GONE);

        btnBuscarRecetaConsulta.setOnClickListener(v -> buscarReceta());

        btnVerDetalles.setOnClickListener(v -> {
            try {
                int idReceta = Integer.parseInt(editIdRecetaConsulta.getText().toString());
                Intent i = new Intent(RecetaConsultarActivity.this, DetalleRecetaMenuActivity.class);
                i.putExtra("ID_RECETA", idReceta);
                startActivity(i);
            } catch (NumberFormatException e) {
                Toast.makeText(RecetaConsultarActivity.this, "ID de Receta no válido para ver detalles.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarReceta() {
        String idRecetaStr = editIdRecetaConsulta.getText().toString().trim();
        if (idRecetaStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID de Receta", Toast.LENGTH_SHORT).show();
            return;
        }
        limpiarCamposResultado();
        btnVerDetalles.setVisibility(View.GONE);

        try {
            int idReceta = Integer.parseInt(idRecetaStr);
            Receta receta = Receta.getByPk(idReceta); // USA TU ORM

            if (receta != null) {
                textDui.setText("DUI: " + receta.getDui());
                textIdDoctor.setText("ID Doctor: " + receta.getIdDoctor());
                textNombrePaciente.setText("Paciente: " + receta.getNombrePaciente());
                textFecha.setText("Fecha: " + receta.getFecha());
                textEdad.setText("Edad: " + receta.getEdad());
                textObservaciones.setText("Observaciones: " + receta.getObservaciones());
                btnVerDetalles.setVisibility(View.VISIBLE);
                editIdRecetaConsulta.setEnabled(false); // Opcional: bloquear después de búsqueda exitosa
                Toast.makeText(this, getString(R.string.dialog_msg_receta_encontrado), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.dialog_msg_error_receta_no_encontrada), Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID de Receta debe ser numérico.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCamposResultado(){
        textDui.setText("DUI:");
        textIdDoctor.setText("ID Doctor:");
        textNombrePaciente.setText("Paciente:");
        textFecha.setText("Fecha:");
        textEdad.setText("Edad:");
        textObservaciones.setText("Observaciones:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Para permitir nueva búsqueda si el usuario vuelve
        editIdRecetaConsulta.setEnabled(true);
    }
}