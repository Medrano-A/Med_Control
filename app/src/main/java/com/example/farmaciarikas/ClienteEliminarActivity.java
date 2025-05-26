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


public class ClienteEliminarActivity extends AppCompatActivity {

    private EditText editTextDui;
    private Button buttonEliminar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_eliminar);

        editTextDui = findViewById(R.id.editTextDui);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        dbFarmacia = new ControlDBFarmacia(this);

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dui = editTextDui.getText().toString().trim();

                if (dui.length() != 10) {
                    Toast.makeText(ClienteEliminarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean eliminado = dbFarmacia.eliminarCliente(dui);

                AlertDialog.Builder builder = new AlertDialog.Builder(ClienteEliminarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_eliminar));

                if (eliminado) {
                    builder.setMessage(getString(R.string.dialog_msg_cliente_eliminado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_eliminar_cliente));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                builder.create().show();
            }
        });
    }
}