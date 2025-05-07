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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClienteInsertarActivity extends AppCompatActivity {

    private EditText editTextDui, editTextNombre, editTextApellido, editTextTelefono, editTextCorreo;
    private Button buttonGuardar;
    private ControlDBFarmacia dbFarmacia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_insertar);

        //Asignación de los editText con los campos.
        editTextDui = findViewById(R.id.editTextDui);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        dbFarmacia = new ControlDBFarmacia(this);


        //Logica del boton Guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dui = editTextDui.getText().toString().trim();
                String nombre = editTextNombre.getText().toString().trim();
                String apellido = editTextApellido.getText().toString().trim();
                String telefono = editTextTelefono.getText().toString().trim();
                String correo = editTextCorreo.getText().toString().trim();

                if (dui.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                    Toast.makeText(ClienteInsertarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean insertada = false;//dbFarmacia.insertarCliente(dui, nombre, apellido, telefono, correo);

                AlertDialog.Builder builder = new AlertDialog.Builder(ClienteInsertarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_ingreso));

                if (insertada) {
                    builder.setMessage(getString(R.string.dialog_msg_cliente_guardado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_error_guardar_cliente));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}