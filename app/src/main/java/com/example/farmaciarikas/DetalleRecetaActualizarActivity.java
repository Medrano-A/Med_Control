package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleRecetaActualizarActivity extends AppCompatActivity {
    private EditText editTextId, editTextIdReceta, editTextDosis;

    private ControlDBFarmacia dbFarmacia;
    Button buscar;
    Boolean yaBusco = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_receta_actualizar);

        editTextId = findViewById(R.id.editTextId);
        editTextIdReceta = findViewById(R.id.editTextIdReceta);
        editTextDosis = findViewById(R.id.editTextDosis);

        dbFarmacia = new ControlDBFarmacia(this);
        buscar = findViewById(R.id.buttonActualizarDetalleReceta);
        buscar.setText(R.string.ActualizarDetalleReceta_btn_buscar);

        //Logica para cambiar de Buscar a Actualizar.
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!yaBusco) {
                    yaBusco = true;
                    buscarDetalleReceta();
                } else {
                    yaBusco = false;
                    actualizarDetalleReceta();
                }

            }
        });
    }

    private void buscarDetalleReceta() {
        String idStr = editTextId.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(DetalleRecetaActualizarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        int idDetReceta = Integer.parseInt(idStr);
        DetalleReceta detalle = dbFarmacia.consultarDetalleReceta(idDetReceta);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleRecetaActualizarActivity.this);
        builder.setTitle(getString(R.string.dialog_title_info_consultar));

        if (detalle != null) {
            editTextIdReceta.setText(String.valueOf(detalle.getIdReceta()));
            editTextDosis.setText(detalle.getDosis());
            editTextId.setEnabled(false);
            editTextIdReceta.setEnabled(true);
            editTextDosis.setEnabled(true);
            builder.setMessage(getString(R.string.dialog_msg_detalleReceta_encontrado));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);

            buscar.setText(R.string.ActualizarCliente_btn_actualizar);
        } else {
            builder.setMessage(getString(R.string.dialog_msg_error_encontrado_detalleReceta));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
            editTextIdReceta.setText("");
            editTextDosis.setText("");
        }
        builder.create().show();
    }

    private void actualizarDetalleReceta() {
        String idStr = editTextId.getText().toString().trim();
        String idRecetaStr = editTextIdReceta.getText().toString().trim();
        String dosis = editTextDosis.getText().toString().trim();

        int idDetReceta = Integer.parseInt(idStr);
        int idReceta = Integer.parseInt(idRecetaStr);

        DetalleReceta detalle = new DetalleReceta(idDetReceta, idReceta, dosis);

        if (!detalle.esValido()) {
            Toast.makeText(DetalleRecetaActualizarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizada = dbFarmacia.actualizarDetalleReceta(detalle);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleRecetaActualizarActivity.this);
        builder.setTitle(getString(R.string.ActualizarDetalleReceta_titulo));

        if (actualizada) {
            builder.setMessage(getString(R.string.dialog_msg_detalleReceta_actualizado));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), (dialog, which) -> finish());

            editTextId.setEnabled(true);
            editTextId.setText("");
            editTextIdReceta.setEnabled(false);
            editTextIdReceta.setText("");
            editTextDosis.setEnabled(false);
            editTextDosis.setText("");
            buscar.setText(R.string.ActualizarDetalleReceta_btn_buscar);
        } else {
            builder.setMessage(getString(R.string.dialog_msg_error_actualizado_detalleReceta));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}