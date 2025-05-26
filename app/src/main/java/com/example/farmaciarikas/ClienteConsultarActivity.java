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

public class ClienteConsultarActivity extends AppCompatActivity {

    private EditText editTextDui, editTextNombre, editTextApellido, editTextTelefono, editTextCorreo;
    private Button buttonConsultar;
    private ControlDBFarmacia dbFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_consultar);

        editTextDui = findViewById(R.id.editTextDui);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        buttonConsultar = findViewById(R.id.buttonConsultarCliente);
        dbFarmacia = new ControlDBFarmacia(this);

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dui = editTextDui.getText().toString().trim();

                if (dui.isEmpty() || dui.length() > 10 || dui == null) {
                    Toast.makeText(ClienteConsultarActivity.this, getString(R.string.msg_campos_obligatorios), Toast.LENGTH_SHORT).show();
                    return;
                }

                Cliente cliente = dbFarmacia.consultarCliente(dui);

                AlertDialog.Builder builder = new AlertDialog.Builder(ClienteConsultarActivity.this);
                builder.setTitle(getString(R.string.dialog_title_info_consultar));

                if (cliente != null) {
                    editTextNombre.setText(cliente.getNombre());
                    editTextApellido.setText(cliente.getApellido());
                    editTextTelefono.setText(cliente.getTelefono());
                    editTextCorreo.setText(cliente.getCorreo());

                    builder.setMessage(getString(R.string.dialog_msg_cliente_encontrado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                } else {
                    builder.setMessage(getString(R.string.dialog_msg_cliente_no_encontrado));
                    builder.setPositiveButton(getString(R.string.dialog_btn_aceptar), null);
                }

                builder.create().show();
            }
        });
    }
}
