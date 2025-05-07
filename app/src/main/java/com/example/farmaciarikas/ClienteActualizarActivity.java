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


public class ClienteActualizarActivity extends AppCompatActivity {

    private EditText editTextDui, editTextNombre, editTextApellido, editTextTelefono, editTextCorreo;
    Button buscar;
    Boolean yaBusco = false;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_actualizar);

        buscar = findViewById(R.id.buttonActualizarCliente);
        editTextDui = findViewById(R.id.editDuiCliente);
        editTextNombre = findViewById(R.id.editNombreCliente);
        editTextApellido = findViewById(R.id.editApellidoCliente);
        editTextTelefono = findViewById(R.id.editTelCliente);
        editTextCorreo = findViewById(R.id.editCorreoCliente);
        dbFarmacia = new ControlDBFarmacia(this);

        buscar.setText(R.string.ActualizarCliente_btn_buscar);

        //Logica para cambiar de Buscar a Actualizar.
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!yaBusco){
                    yaBusco=true;
                    String dui = editTextDui.getText().toString().trim();

                    if (dui.isEmpty()) {
                        Toast.makeText(ClienteActualizarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cliente cliente = null; // dbFarmacia.consultarCliente(dui);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ClienteActualizarActivity.this);
                    builder.setTitle(getString(R.string.dialog_title_info_consultar));

                    if (cliente != null) {
                        editTextNombre.setText(cliente.getNombre());
                        editTextApellido.setText(cliente.getApellido());
                        editTextTelefono.setText(cliente.getTelefono());
                        editTextCorreo.setText(cliente.getCorreo());

                        editTextDui.setEnabled(false);
                        editTextNombre.setEnabled(true);
                        editTextNombre.requestFocus();
                        editTextApellido.setEnabled(true);
                        editTextTelefono.setEnabled(true);
                        editTextCorreo.setEnabled(true);


                        builder.setMessage(getString(R.string.dialog_msg_cliente_encontrado));
                        builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);

                        buscar.setText(R.string.ActualizarCliente_btn_actualizar);
                    } else {
                        builder.setMessage(getString(R.string.dialog_msg_cliente_no_encontrado));
                        builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                    }

                    builder.create().show();
                }
                else{
                    yaBusco=false;

                    String dui = editTextDui.getText().toString().trim();
                    String nombre = editTextNombre.getText().toString().trim();
                    String apellido = editTextApellido.getText().toString().trim();
                    String telefono = editTextTelefono.getText().toString().trim();
                    String correo = editTextCorreo.getText().toString().trim();

                    if (dui.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                        Toast.makeText(ClienteActualizarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean insertada = false;//dbFarmacia.actualizarCliente(dui, nombre, apellido, telefono, correo);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ClienteActualizarActivity.this);
                    builder.setTitle(getString(R.string.dialog_title_info_actualizar));

                    if (insertada) {
                        builder.setMessage(getString(R.string.dialog_msg_cliente_guardado));
                        builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        editTextDui.setEnabled(true);
                        editTextDui.setText("");
                        editTextNombre.setEnabled(false);
                        editTextNombre.setText("");
                        editTextApellido.setEnabled(false);
                        editTextApellido.setText("");
                        editTextTelefono.setEnabled(false);
                        editTextTelefono.setText("");
                        editTextCorreo.setEnabled(false);
                        editTextCorreo.setText("");
                        buscar.setText(R.string.ActualizarCliente_btn_buscar);
                    } else {
                        builder.setMessage(getString(R.string.dialog_msg_error_guardar_cliente));
                        builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                    }

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }
}