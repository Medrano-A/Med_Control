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

public class DistribuidorEliminarActivity extends AppCompatActivity {

    private EditText editIdDis;
    private Button buttonEliminar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_distribuidor_eliminar);

        editIdDis = findViewById(R.id.editIdDis);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = editIdDis.getText().toString().trim();

                if (idStr.isEmpty()) {
                    Toast.makeText(DistribuidorEliminarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                int idDistribuidor = Integer.parseInt(idStr);
                boolean eliminado = dbFarmacia.eliminarDistribuidor(idDistribuidor);

                AlertDialog.Builder builder = new AlertDialog.Builder(DistribuidorEliminarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_eliminar));

                if (eliminado) {
                    builder.setMessage(getString(R.string.dialog_msg_distribuidor_eliminado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_eliminar_distribuidor));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                builder.create().show();
            }
        });
    }
}
