package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DistribuidorConsultarActivity extends AppCompatActivity {

    private EditText editIdDis, editIdMarcaDis, editNombreDis, editTelDis, editNitDis, editCorreoDis;
    private Button buttonConsultar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_distribuidor_consultar);

        editIdDis = findViewById(R.id.editIdDis);
        editIdMarcaDis = findViewById(R.id.editIdMarcaDis);
        editNombreDis = findViewById(R.id.editNombreDis);
        editTelDis = findViewById(R.id.editTelDis);
        editNitDis = findViewById(R.id.editNitDis);
        editCorreoDis = findViewById(R.id.editCorreoDis);
        buttonConsultar = findViewById(R.id.buttonGuardar);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = editIdDis.getText().toString().trim();

                if (idStr.isEmpty()) {
                    Toast.makeText(DistribuidorConsultarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                int idDistribuidor = Integer.parseInt(idStr);
                Distribuidor distribuidor = dbFarmacia.consultarDistribuidor(idDistribuidor);

                AlertDialog.Builder builder = new AlertDialog.Builder(DistribuidorConsultarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_consultar));

                if (distribuidor != null) {
                    editIdMarcaDis.setText(String.valueOf(distribuidor.getIdMarca()));
                    editNombreDis.setText(distribuidor.getNombre());
                    editTelDis.setText(distribuidor.getTelefono());
                    editNitDis.setText(distribuidor.getNit());
                    editCorreoDis.setText(distribuidor.getCorreo());

                    builder.setMessage(getString(R.string.dialog_msg_distribuidor_consultar));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_consultar_distribuidor));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);

                    editIdMarcaDis.setText("");
                    editNombreDis.setText("");
                    editTelDis.setText("");
                    editNitDis.setText("");
                    editCorreoDis.setText("");
                }

                builder.create().show();
            }
        });
    }
}
