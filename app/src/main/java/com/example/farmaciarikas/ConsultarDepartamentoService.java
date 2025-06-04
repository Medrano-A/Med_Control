package com.example.farmaciarikas;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultarDepartamentoService extends AppCompatActivity {

    Spinner spinnerDepartamentoConsulta;
    EditText dptoNomConsEdit;
    Button dptoConsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_departamento_service);

        spinnerDepartamentoConsulta = findViewById(R.id.spinnerDepartamentoConsulta);
        dptoNomConsEdit = findViewById(R.id.dptoNomConsEdit);
        dptoConsBtn = findViewById(R.id.dptoConsBtn);

        // Simulamos llenar spinner (puedes modificar esto para que venga de base de datos)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (int i = 1; i <= 14; i++) {
            adapter.add(String.valueOf(i));
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamentoConsulta.setAdapter(adapter);


        dptoConsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consDpto();
            }
        });
    }

    private void consDpto() {
        String idSeleccionado = spinnerDepartamentoConsulta.getSelectedItem().toString();
        new ConsultarDepartamentoTask().execute(idSeleccionado);
    }

    class ConsultarDepartamentoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String idDepartamento = params[0];
            String resultado;

            try {
                // URL con la IP local de tu servidor
                URL url = new URL("http://" + ControlService.BASE_IP + "/ws5/farmacia/consultar_departamento.php?idDepartamento=" + idDepartamento);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String linea;
                while ((linea = reader.readLine()) != null) {
                    sb.append(linea);
                }

                resultado = sb.toString();
            } catch (Exception e) {
                resultado = "{\"success\":false,\"message\":\"Error de conexión: " + e.getMessage() + "\"}";
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                Log.d("RESPUESTA_SERVIDOR", s);  // Ver respuesta cruda en Logcat

                JSONObject json = new JSONObject(s);
                if (json.getBoolean("success")) {
                    String nombre = json.getString("nombre");
                    dptoNomConsEdit.setText(nombre);
                } else {
                    String mensaje = json.getString("message");
                    dptoNomConsEdit.setText("No disponible");
                    Toast.makeText(ConsultarDepartamentoService.this, "Error: " + mensaje, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(ConsultarDepartamentoService.this, "Error al parsear respuesta: " + s, Toast.LENGTH_LONG).show();
                Log.e("ERROR_JSON", e.getMessage());
            }
        }
    }

    public void limpiarCampos(View v) {
        dptoNomConsEdit.setText("");
    }
}
