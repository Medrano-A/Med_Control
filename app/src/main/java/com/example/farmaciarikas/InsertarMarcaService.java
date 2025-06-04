package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertarMarcaService extends AppCompatActivity {
    ControlDBFarmacia dbFarmHelper;

    EditText editIdMarca, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_marca_service);
        //helperBd y asignacion de editTxt
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMarca = (EditText) findViewById(R.id.marcaIdInsertarEdit);
        editNombre = (EditText) findViewById(R.id.marcaNombreInsertarEdit);
    }
    public void insertarServidor(View v){
        String idMarcaText = editIdMarca.getText().toString().trim();
        String nombreText = editNombre.getText().toString().trim();

        if (idMarcaText.isEmpty() || nombreText.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.0.7/ws1/insertar_marca.php";

        // Crear la solicitud POST
        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> Toast.makeText(this, "Respuesta: " + response, Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idMarca", idMarcaText);
                params.put("nombre", nombreText);
                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(postRequest);
    }
}