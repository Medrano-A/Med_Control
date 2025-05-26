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

public class DistribuidorInsertarActivity extends AppCompatActivity {

    private EditText editIdDis, editIdMarcaDis, editNombreDis, editTelDis, editNitDis, editCorreoDis;
    private Button buttonGuardar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_distribuidor_insertar);

        editIdDis = findViewById(R.id.editIdDis);
        editIdMarcaDis = findViewById(R.id.editIdMarcaDis);
        editNombreDis = findViewById(R.id.editNombreDis);
        editTelDis = findViewById(R.id.editTelDis);
        editNitDis = findViewById(R.id.editNitDis);
        editCorreoDis = findViewById(R.id.editCorreoDis);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = editIdDis.getText().toString().trim();
                String idMarcaStr = editIdMarcaDis.getText().toString().trim();
                String nombre = editNombreDis.getText().toString().trim();
                String telefono = editTelDis.getText().toString().trim();
                String nit = editNitDis.getText().toString().trim();
                String correo = editCorreoDis.getText().toString().trim();

                if (idStr.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || nit.isEmpty()) {
                    Toast.makeText(DistribuidorInsertarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                int idDistribuidor = Integer.parseInt(idStr);
                Integer idMarca = idMarcaStr.isEmpty() ? null : Integer.parseInt(idMarcaStr);

                Distribuidor distribuidor = new Distribuidor(idDistribuidor, idMarca, nombre, telefono, nit, correo);

                if (!distribuidor.esValido()) {
                    Toast.makeText(DistribuidorInsertarActivity.this, getString(R.string.msg_campos_invalidos), Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean insertado = dbFarmacia.insertarDistribuidor(distribuidor);
                AlertDialog.Builder builder = new AlertDialog.Builder(DistribuidorInsertarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_ingreso));

                if (insertado) {
                    builder.setMessage(getString(R.string.dialog_msg_distribuidor_guardado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_guardar_distribuidor));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                builder.create().show();
            }
        });
    }
}
