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
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ConsultarMarcaService extends AppCompatActivity {
    ControlDBFarmacia dbFarmHelper;

    EditText editIdMarca, editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_marca_service);
        dbFarmHelper = new ControlDBFarmacia(this);
        editIdMarca = (EditText) findViewById(R.id.marcaIdConsEdit);
        editNombre = (EditText) findViewById(R.id.marcaNomConsEdit);

    }
    public void consultarMarcaServidor(View v){
        String idMarcaText = editIdMarca.getText().toString();

        if (idMarcaText.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el ID de la Marca", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://192.168.0.7/ws1/consultar_marca.php?idMarca=" + idMarcaText;

        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("resultado") == 1) {
                            JSONObject marca = json.getJSONObject("marca");
                            String nombre = marca.getString("nombre");
                            editNombre.setText(nombre);
                        } else {
                            Toast.makeText(this, json.getString("mensaje"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);


    }
    public void limpiarCampos(View v){
        editIdMarca.setText("");
        editNombre.setText("");
    }
}