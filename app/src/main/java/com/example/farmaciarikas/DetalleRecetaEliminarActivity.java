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

public class DetalleRecetaEliminarActivity extends AppCompatActivity {

    private EditText editTextId;
    private Button buttonEliminar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_receta_eliminar);

        editTextId = findViewById(R.id.editTextId);
        buttonEliminar = findViewById(R.id.buttonEliminarDetalleReceta);
        dbFarmacia = new ControlDBFarmacia(this);

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDetalleReceta();
            }
        });
    }

    private void eliminarDetalleReceta() {
        String idTexto = editTextId.getText().toString().trim();

        if (idTexto.isEmpty()) {
            Toast.makeText(DetalleRecetaEliminarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idTexto);
        boolean eliminado = dbFarmacia.eliminarDetalleReceta(id);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.EliminarDetalleReceta_titulo));

        if (eliminado) {
            builder.setMessage(getString(R.string.dialog_msg_detalleReceta_eliminado));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            editTextId.setText("");
        } else {
            builder.setMessage(getString(R.string.dialog_msg_error_eliminar_detalleReceta));
            builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
        }

        builder.create().show();
    }
}
