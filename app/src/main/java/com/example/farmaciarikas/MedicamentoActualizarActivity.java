package com.example.farmaciarikas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MedicamentoActualizarActivity extends Activity {
    ControlDBFarmacia helper;

    EditText editIdMedicamento;
    EditText editIdLab;
    EditText editVia;
    EditText editForma;
    EditText editNombre;
    EditText editCantidad;
    EditText editDescripcion;
    EditText editPrecio;
    EditText editUnidad;

    /** Called when the activity is first created. */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_actualizar);
        helper = new ControlDBFarmacia(this);

        // Inicializando los campos EditText
        editIdMedicamento = findViewById(R.id.editIdMed);
        editIdLab = findViewById(R.id.editIdLab);
        editVia = findViewById(R.id.editVia);
        editForma = findViewById(R.id.editForma);
        editNombre = findViewById(R.id.editNombreMedicamento);
        editCantidad = findViewById(R.id.editCantidad);
        editDescripcion = findViewById(R.id.editDescripcion);
        editPrecio = findViewById(R.id.editPrecio);
        editUnidad = findViewById(R.id.editUnidad);
    }

    public void actualizarMedicamento(View v) {
        // 1. Obtener ID del medicamento ingresado
        String idMedicamento = editIdMedicamento.getText().toString().trim();

        // Validar que se haya ingresado el ID del medicamento
        if (idMedicamento.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el ID del medicamento", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Obtener el codElemento de la base de datos
        helper.abrir();
        int codElem = helper.obtenerCodElementoPorIdMedicamento(idMedicamento);
        helper.cerrar();

        // Si no se encuentra el medicamento, mostrar un mensaje y salir
        if (codElem == -1) {
            Toast.makeText(this, "No se encontró el medicamento con ese ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Construir objeto Medicamento con los valores ingresados
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(idMedicamento);  // El ID del medicamento
        medicamento.setCodElemento(codElem);  // El codElemento relacionado
        medicamento.setIdLaboratorio(Integer.parseInt(editIdLab.getText().toString()));  // Obtener el ID del laboratorio
        medicamento.setViaDeAdministracion(editVia.getText().toString());  // Obtener la vía de administración
        medicamento.setFormaFarmaceutica(editForma.getText().toString());  // Obtener la forma farmacéutica
        medicamento.setNombre(editNombre.getText().toString());  // Obtener el nombre del medicamento
        medicamento.setCantidad(Integer.parseInt(editCantidad.getText().toString()));  // Obtener la cantidad
        medicamento.setDescripcion(editDescripcion.getText().toString());  // Obtener la descripción
        medicamento.setPrecioUni(Double.parseDouble(editPrecio.getText().toString()));  // Obtener el precio unitario
        medicamento.setUnidades(editUnidad.getText().toString());  // Obtener la unidad

        // 4. Actualizar primero el Elemento, luego el Medicamento
        helper.abrir();
        boolean elementoActualizado = helper.actualizarElemento(medicamento);  // Actualizar Elemento
        boolean medicamentoActualizado = helper.actualizarMedicamento(medicamento);  // Actualizar Medicamento
        helper.cerrar();

        if (elementoActualizado && medicamentoActualizado) {
            Toast.makeText(this, "Medicamento y Elemento actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else if (!elementoActualizado && !medicamentoActualizado) {
            Toast.makeText(this, "Error: no se actualizó ni el Elemento ni el Medicamento", Toast.LENGTH_LONG).show();
        } else if (!elementoActualizado) {
            Toast.makeText(this, "Error al actualizar el Elemento", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al actualizar el Medicamento", Toast.LENGTH_LONG).show();
        }

    }

    public void consultarMedicamento(View v) {

        helper.abrir();
        Medicamento medicamento =
                helper.consultarMedicamento(editIdMedicamento.getText().toString());

        helper.cerrar();
        if(medicamento == null)
            Toast.makeText(this, "Medicamento con codigo " +
                    editIdMedicamento.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{

            editIdLab.setText(String.valueOf(medicamento.getIdLaboratorio()));
            editVia.setText(medicamento.getViaDeAdministracion());
            editForma.setText(medicamento.getFormaFarmaceutica());
            editNombre.setText(medicamento.getNombre());
            editCantidad.setText(String.valueOf(medicamento.getCantidad()));
            editDescripcion.setText(medicamento.getDescripcion());
            editPrecio.setText(String.valueOf(medicamento.getPrecioUni()));
            editUnidad.setText(medicamento.getUnidades());
        }
    }

    public void limpiarTexto(View v) {
        editIdMedicamento.setText("");
        editDescripcion.setText("");
        editIdLab.setText("");
        editVia.setText("");
        editForma.setText("");
        editNombre.setText("");
        editPrecio.setText("");
        editCantidad.setText("");

    }
}