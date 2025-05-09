package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout; // Asegúrate de importar LinearLayout
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.SQLException;

public class RecetaActualizarActivity extends AppCompatActivity {

    EditText editIdRecetaBuscar, editDui, editIdDoctor, editNombrePaciente, editFecha, editEdad, editObservaciones;
    Button btnBuscar, btnActualizar, btnGestionarDetalles;
    LinearLayout layoutDatosReceta; // <--- NUEVO: Referencia al LinearLayout contenedor
    private Receta recetaActual = null; // Para guardar la receta encontrada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_actualizar); // Asegúrate de crear este layout

        editIdRecetaBuscar = findViewById(R.id.editRecetaId_buscar_actualizar);
        editDui = findViewById(R.id.editRecetaDui_actualizar);
        editIdDoctor = findViewById(R.id.editRecetaIdDoctor_actualizar);
        editNombrePaciente = findViewById(R.id.editRecetaNombrePaciente_actualizar);
        editFecha = findViewById(R.id.editRecetaFecha_actualizar);
        editEdad = findViewById(R.id.editRecetaEdad_actualizar);
        editObservaciones = findViewById(R.id.editRecetaObservaciones_actualizar);

        btnBuscar = findViewById(R.id.btnRecetaBuscar_actualizar);
        btnActualizar = findViewById(R.id.btnRecetaActualizar_confirmar);
        btnGestionarDetalles = findViewById(R.id.btnRecetaGestionarDetalles_actualizar);

        layoutDatosReceta = findViewById(R.id.layoutDatosReceta_actualizar); // <--- NUEVO: Inicializar el LinearLayout

        // Estado inicial: el layout de datos está GONE por XML,
        // los campos dentro de él están deshabilitados y los botones también.
        habilitarCampos(false);
        btnActualizar.setEnabled(false);
        btnGestionarDetalles.setVisibility(View.GONE); // Este botón está dentro del layout, pero es bueno ser explícito.
        // layoutDatosReceta.setVisibility(View.GONE); // Ya está GONE desde el XML, pero no haría daño

        btnBuscar.setOnClickListener(v -> buscarReceta());
        btnActualizar.setOnClickListener(v -> actualizarReceta());
        btnGestionarDetalles.setOnClickListener(v -> {
            if (recetaActual != null) {
                Intent i = new Intent(RecetaActualizarActivity.this, DetalleRecetaMenuActivity.class);
                i.putExtra("ID_RECETA", recetaActual.getIdReceta());
                startActivity(i);
            }
        });
    }

    private void habilitarCampos(boolean habilitar) {
        editDui.setEnabled(habilitar);
        editIdDoctor.setEnabled(habilitar);
        editNombrePaciente.setEnabled(habilitar);
        editFecha.setEnabled(habilitar);
        editEdad.setEnabled(habilitar);
        editObservaciones.setEnabled(habilitar);
    }

    private void buscarReceta() {
        String idStr = editIdRecetaBuscar.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese ID de Receta a buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Limpiar estado anterior y ocultar formulario de datos
        limpiarCamposDatos();
        habilitarCampos(false);
        btnActualizar.setEnabled(false);
        btnGestionarDetalles.setVisibility(View.GONE);
        layoutDatosReceta.setVisibility(View.GONE);
        recetaActual = null;
        // No desbloquear editIdRecetaBuscar aquí, se hace si no se encuentra o después de actualizar

        try {
            int idReceta = Integer.parseInt(idStr);
            recetaActual = Receta.getByPk(idReceta);

            if (recetaActual != null) {
                editIdRecetaBuscar.setEnabled(false); // Bloquear campo de búsqueda
                editDui.setText(recetaActual.getDui());
                editIdDoctor.setText(String.valueOf(recetaActual.getIdDoctor()));
                editNombrePaciente.setText(recetaActual.getNombrePaciente());
                editFecha.setText(recetaActual.getFecha());
                editEdad.setText(String.valueOf(recetaActual.getEdad()));
                editObservaciones.setText(recetaActual.getObservaciones());

                layoutDatosReceta.setVisibility(View.VISIBLE);
                habilitarCampos(true);
                btnActualizar.setEnabled(true);
                btnGestionarDetalles.setVisibility(View.VISIBLE);
                Toast.makeText(this, getString(R.string.dialog_msg_receta_encontrado), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.dialog_msg_error_receta_no_encontrada), Toast.LENGTH_SHORT).show();
                editIdRecetaBuscar.setEnabled(true); // Permitir nueva búsqueda si no se encontró
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID de Receta debe ser numérico.", Toast.LENGTH_SHORT).show();
            editIdRecetaBuscar.setEnabled(true); // Permitir nueva búsqueda
        } catch (Exception e) {
            Toast.makeText(this, "Error al buscar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            editIdRecetaBuscar.setEnabled(true); // Permitir nueva búsqueda
            e.printStackTrace();
        }
    }

    private void actualizarReceta() {
        if (recetaActual == null) {
            Toast.makeText(this, "Primero busque una receta", Toast.LENGTH_SHORT).show();
            return;
        }

        String duiStr = editDui.getText().toString().trim();
        String idDoctorStr = editIdDoctor.getText().toString().trim();
        String nombrePacienteStr = editNombrePaciente.getText().toString().trim();
        String fechaStr = editFecha.getText().toString().trim();
        String edadStr = editEdad.getText().toString().trim();
        String observacionesStr = editObservaciones.getText().toString().trim();

        if (idDoctorStr.isEmpty() || nombrePacienteStr.isEmpty() || fechaStr.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int idDoctor = Integer.parseInt(idDoctorStr);
            int edad = Integer.parseInt(edadStr);

            recetaActual.setDui(duiStr);
            recetaActual.setIdDoctor(idDoctor);
            recetaActual.setNombrePaciente(nombrePacienteStr);
            recetaActual.setFecha(fechaStr);
            recetaActual.setEdad(edad);
            recetaActual.setObservaciones(observacionesStr);

            long resultado = recetaActual.update();

            if (resultado > 0) {
                Toast.makeText(this, getString(R.string.dialog_msg_receta_actualizado), Toast.LENGTH_LONG).show();
                editIdRecetaBuscar.setEnabled(true);
                limpiarCamposDatos();
                habilitarCampos(false);
                btnActualizar.setEnabled(false);
                btnGestionarDetalles.setVisibility(View.GONE);
                layoutDatosReceta.setVisibility(View.GONE); // <--- MODIFICADO: Ocultar después de actualizar
                recetaActual = null;
            } else {
                Toast.makeText(this, getString(R.string.dialog_msg_error_actualizar_receta) + " (No se afectaron filas)", Toast.LENGTH_LONG).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en formato de ID Doctor o Edad.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCamposDatos(){
        editDui.setText("");
        editIdDoctor.setText("");
        editNombrePaciente.setText("");
        editFecha.setText("");
        editEdad.setText("");
        editObservaciones.setText("");
        // No es necesario limpiar editIdRecetaBuscar aquí, se maneja según el contexto
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recetaActual == null){
            editIdRecetaBuscar.setEnabled(true);
            editIdRecetaBuscar.setText(""); // Limpiar campo de búsqueda si no hay receta cargada
            limpiarCamposDatos();
            habilitarCampos(false);
            btnActualizar.setEnabled(false);
            btnGestionarDetalles.setVisibility(View.GONE);
            layoutDatosReceta.setVisibility(View.GONE); // <--- MODIFICADO: Asegurar que esté oculto
        }
        // Si recetaActual no es null, significa que ya hay una receta cargada y
        // el layoutDatosReceta debería estar visible (si la búsqueda fue exitosa).
        // No es necesario cambiar su visibilidad aquí explícitamente en ese caso.
    }
}