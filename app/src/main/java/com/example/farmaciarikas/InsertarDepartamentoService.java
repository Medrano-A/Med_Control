package com.example.farmaciarikas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InsertarDepartamentoService extends AppCompatActivity {

    private Spinner spinnerId;
    private EditText editNombre;
    private Button btnInsertar, btnLimpiar;

    // Cambia la IP si es necesario
    private final String URL = "http://192.168.0.12/ws5/farmacia/insertar_departamento.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_departamento_service);

        // Referencias a los controles
        spinnerId = findViewById(R.id.dptoIdInsertarSpinner);
        editNombre = findViewById(R.id.dptoNomInsertarEdit);
        btnInsertar = findViewById(R.id.dptoInsertarBtn);
        btnLimpiar = findViewById(R.id.dptoInsertarBtnTxt);

        // Llenar spinner con IDs del 1 al 14
        List<String> idList = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            idList.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerId.setAdapter(adapter);


        btnInsertar.setOnClickListener(v -> insertarDpto());


        btnLimpiar.setOnClickListener(v -> limpiarCampos());
    }

    public void insertarDpto() {
        String id = spinnerId.getSelectedItem() != null ? spinnerId.getSelectedItem().toString().trim() : "";
        String nombre = editNombre.getText().toString().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("idDepartamento", id);
            json.put("nombre", nombre);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    json,
                    response -> {
                        boolean success = response.optBoolean("success", false);
                        String message = response.optString("message", "Sin mensaje");

                        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    },
                    error -> {
                        String mensaje = error.getMessage();
                        if (mensaje == null) {
                            if (error.networkResponse != null) {
                                mensaje = "Código HTTP: " + error.networkResponse.statusCode;
                            } else {
                                mensaje = "Error desconocido de red";
                            }
                        }
                        Toast.makeText(this, "Error de red: " + mensaje, Toast.LENGTH_LONG).show();
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void limpiarCampos() {
        editNombre.setText("");
        spinnerId.setSelection(0);
    }
}
