package com.example.farmaciarikas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etCarnet, etClave;
    Button btnLogin;
    ControlDBFarmacia helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper = new ControlDBFarmacia(this);
        helper.permisosUsuarios();
        etCarnet = findViewById(R.id.edtUsuario);
        etClave = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carnet = etCarnet.getText().toString().trim();
                String clave = etClave.getText().toString().trim();

                if (carnet.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Ingrese ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (validarUsuario(carnet, clave)) {
                        Toast.makeText(LoginActivity.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Cierra la actividad de login
                    } else {
                        Toast.makeText(LoginActivity.this, "Carnet o clave incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validarUsuario(String carnet, String clave) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM User WHERE nom_usuario = ? AND clave = ?";
        Cursor cursor = db.rawQuery(query, new String[]{carnet, clave});

        boolean valido = cursor.moveToFirst();
        cursor.close();
        return valido;
    }
}
