package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecetaConsultarActivity extends AppCompatActivity {

    private EditText editIdRecetaConsulta;
    private Button btnBuscarRecetaConsulta;
    private Button btnVerDetalles;
    private TextView textDui, textIdDoctor, textNombrePaciente,
            textFecha, textEdad, textObservaciones;
    private TextView labelDetallesReceta;
    private ListView listDetalleReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_consultar);

        // Vinculación de vistas
        editIdRecetaConsulta      = findViewById(R.id.editRecetaIdConsulta);
        btnBuscarRecetaConsulta   = findViewById(R.id.btnRecetaBuscarConsulta);
        btnVerDetalles            = findViewById(R.id.btnRecetaVerDetallesConsulta);

        textDui                   = findViewById(R.id.textRecetaDui);
        textIdDoctor              = findViewById(R.id.textRecetaIdDoctor);
        textNombrePaciente        = findViewById(R.id.textRecetaNombrePaciente);
        textFecha                 = findViewById(R.id.textRecetaFecha);
        textEdad                  = findViewById(R.id.textRecetaEdad);
        textObservaciones         = findViewById(R.id.textRecetaObservaciones);

        labelDetallesReceta       = findViewById(R.id.labelDetallesReceta);
        listDetalleReceta         = findViewById(R.id.listDetalleReceta);

        // Ocultar hasta que se cargue
        btnVerDetalles.setVisibility(View.GONE);
        labelDetallesReceta.setVisibility(View.GONE);
        listDetalleReceta.setVisibility(View.GONE);

        btnBuscarRecetaConsulta.setOnClickListener(v -> buscarReceta());

        btnVerDetalles.setOnClickListener(v -> {
            // Si existiera otra pantalla de menú de detalles:
            try {
                int id = Integer.parseInt(editIdRecetaConsulta.getText().toString());
                Intent i = new Intent(this, DetalleRecetaMenuActivity.class);
                i.putExtra("ID_RECETA", id);
                startActivity(i);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID de Receta no válido.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarReceta() {
        String idRecetaStr = editIdRecetaConsulta.getText().toString().trim();
        if (idRecetaStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID de Receta", Toast.LENGTH_SHORT).show();
            return;
        }

        limpiarCampos();

        try {
            int idReceta = Integer.parseInt(idRecetaStr);
            Receta receta = Receta.getByPk(idReceta);
            if (receta == null) {
                Toast.makeText(this, "Receta no encontrada.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mostrar datos de la receta
            textDui.setText("DUI: " + receta.getDui());
            textIdDoctor.setText("ID Doctor: " + receta.getIdDoctor());
            textNombrePaciente.setText("Paciente: " + receta.getNombrePaciente());
            textFecha.setText("Fecha: " + receta.getFecha());
            textEdad.setText("Edad: " + receta.getEdad());
            textObservaciones.setText("Observaciones: " + receta.getObservaciones());

            btnVerDetalles.setVisibility(View.VISIBLE);

            // Cargar detalles
            DetalleReceta[] detalles = DetalleReceta.getByReceta(idReceta);
            String[] items = new String[detalles.length];
            for (int i = 0; i < detalles.length; i++) {
                items[i] = "Detalle #" + detalles[i].getIdDetReceta()
                        + " — Dosis: " + detalles[i].getDosis();
            }

            // Adapter y visibilidad
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    items
            );
            listDetalleReceta.setAdapter(adapter);

            labelDetallesReceta.setVisibility(View.VISIBLE);
            listDetalleReceta.setVisibility(View.VISIBLE);

            editIdRecetaConsulta.setEnabled(false);
            Toast.makeText(this, "Receta encontrada.", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID de Receta debe ser numérico.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        textDui.setText("DUI:");
        textIdDoctor.setText("ID Doctor:");
        textNombrePaciente.setText("Paciente:");
        textFecha.setText("Fecha:");
        textEdad.setText("Edad:");
        textObservaciones.setText("Observaciones:");
        btnVerDetalles.setVisibility(View.GONE);
        labelDetallesReceta.setVisibility(View.GONE);
        listDetalleReceta.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editIdRecetaConsulta.setEnabled(true);
    }
}
