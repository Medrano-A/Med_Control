package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DistribuidorActualizarActivity extends AppCompatActivity {

    private EditText editIdDis, editIdMarcaDis, editNombreDis, editTelDis, editNitDis, editCorreoDis;
    private Button buttonActualizarDistribuidor;
    private ControlDBFarmacia dbFarmacia;
    private Distribuidor distribuidorActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_distribuidor_actualizar);

        editIdDis = findViewById(R.id.editIdDis);
        editIdMarcaDis = findViewById(R.id.editIdMarcaDis);
        editNombreDis = findViewById(R.id.editNombreDis);
        editTelDis = findViewById(R.id.editTelDis);
        editNitDis = findViewById(R.id.editNitDis);
        editCorreoDis = findViewById(R.id.editCorreoDis);
        buttonActualizarDistribuidor = findViewById(R.id.buttonActualizarDistribuidor);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonActualizarDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonActualizarDistribuidor.getText().toString().equals(getString(R.string.ActualizarDistribuidor_btn_buscar))) {
                    buscarDistribuidor();
                } else {
                    actualizarDistribuidor();
                }
            }
        });
    }

    private void buscarDistribuidor() {
        String idStr = editIdDis.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        int idDistribuidor = Integer.parseInt(idStr);
        distribuidorActual = dbFarmacia.consultarDistribuidor(idDistribuidor);

        if (distribuidorActual != null) {
            editIdMarcaDis.setText(String.valueOf(distribuidorActual.getIdMarca()));
            editNombreDis.setText(distribuidorActual.getNombre());
            editTelDis.setText(distribuidorActual.getTelefono());
            editNitDis.setText(distribuidorActual.getNit());
            editCorreoDis.setText(distribuidorActual.getCorreo());

            editIdDis.setEnabled(false);
            editIdMarcaDis.setEnabled(true);
            editNombreDis.setEnabled(true);
            editTelDis.setEnabled(true);
            editNitDis.setEnabled(true);
            editCorreoDis.setEnabled(true);

            buttonActualizarDistribuidor.setText(getString(R.string.ActualizarDistribuidor_btn_actualizar));
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.dialog_title_info_consultar));
            builder.setMessage(getString(R.string.dialog_msg_error_consultar_distribuidor));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
            builder.show();
        }
    }

    private void actualizarDistribuidor() {
        String idMarcaStr = editIdMarcaDis.getText().toString().trim();
        String nombre = editNombreDis.getText().toString().trim();
        String telefono = editTelDis.getText().toString().trim();
        String nit = editNitDis.getText().toString().trim();
        String correo = editCorreoDis.getText().toString().trim();

        if (idMarcaStr.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || nit.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        distribuidorActual.setIdMarca(Integer.parseInt(idMarcaStr));
        distribuidorActual.setNombre(nombre);
        distribuidorActual.setTelefono(telefono);
        distribuidorActual.setNit(nit);
        distribuidorActual.setCorreo(correo);

        boolean actualizado = dbFarmacia.actualizarDistribuidor(distribuidorActual);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title_info_actualizar));

        if (actualizado) {
            builder.setMessage(getString(R.string.dialog_msg_distribuidor_actualizar));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), (dialog, which) -> finish());
        } else {
            builder.setMessage(getString(R.string.dialog_msg_error_actualizar_distribuidor));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
        }

        builder.create().show();
    }

    private void limpiarCampos() {
        editIdDis.setText("");
        editIdMarcaDis.setText("");
        editNombreDis.setText("");
        editTelDis.setText("");
        editNitDis.setText("");
        editCorreoDis.setText("");

        editIdDis.setEnabled(true);
        editIdMarcaDis.setEnabled(false);
        editNombreDis.setEnabled(false);
        editTelDis.setEnabled(false);
        editNitDis.setEnabled(false);
        editCorreoDis.setEnabled(false);

        buttonActualizarDistribuidor.setText(getString(R.string.ActualizarDistribuidor_btn_buscar));
    }
}
