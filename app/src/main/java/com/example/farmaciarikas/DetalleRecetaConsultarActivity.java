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

public class DetalleRecetaConsultarActivity extends AppCompatActivity {

    private EditText editTextId, editTextIdReceta, editTextDosis;
    private Button buttonConsultar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_receta_consultar);

        editTextId = findViewById(R.id.editTextId);
        editTextIdReceta = findViewById(R.id.editTextIdReceta);
        editTextDosis = findViewById(R.id.editTextDosis);
        buttonConsultar = findViewById(R.id.buttonConsultarDetalleReceta);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = editTextId.getText().toString().trim();

                if (idStr.isEmpty()) {
                    Toast.makeText(DetalleRecetaConsultarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                int idDetReceta = Integer.parseInt(idStr);
                DetalleReceta detalle = dbFarmacia.consultarDetalleReceta(idDetReceta);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleRecetaConsultarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_consultar));

                if (detalle != null) {
                    editTextIdReceta.setText(String.valueOf(detalle.getIdReceta()));
                    editTextDosis.setText(detalle.getDosis());
                    builder.setMessage(getString(R.string.dialog_msg_detalleReceta_encontrado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_encontrado_detalleReceta));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                    editTextIdReceta.setText("");
                    editTextDosis.setText("");
                }
                builder.create().show();
            }

        });
    }
}
