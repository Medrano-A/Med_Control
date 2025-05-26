package com.example.farmaciarikas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleRecetaInsertarActivity extends AppCompatActivity {

    private EditText editTextId, editTextIdReceta, editTextDosis;
    private Button buttonGuardar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_receta_insertar);

        editTextId = findViewById(R.id.editTextId);
        editTextIdReceta = findViewById(R.id.editTextIdReceta);
        editTextDosis = findViewById(R.id.editTextDosis);
        buttonGuardar = findViewById(R.id.buttonGuardarDetalleReceta);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = editTextId.getText().toString().trim();
                String idRecetaStr = editTextIdReceta.getText().toString().trim();
                String dosis = editTextDosis.getText().toString().trim();

                if (idStr.isEmpty() || idRecetaStr.isEmpty() || dosis.isEmpty()) {
                    Toast.makeText(DetalleRecetaInsertarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                int idDetReceta = Integer.parseInt(idStr);
                int idReceta = Integer.parseInt(idRecetaStr);

                DetalleReceta detalle = new DetalleReceta(idDetReceta, idReceta, dosis);

                if (!detalle.esValido()) {
                    Toast.makeText(DetalleRecetaInsertarActivity.this, getString(R.string.msg_campos_invalidos), Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleRecetaInsertarActivity.this);
                builder.setTitle(getString(R.string.InsertarDetalleReceta_titulo));

                boolean insertado = dbFarmacia.insertarDetalleReceta(detalle);
                if (insertado) {
                    builder.setMessage(getString(R.string.dialog_msg_detalleReceta_guardado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_guardar_detalleReceta));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
