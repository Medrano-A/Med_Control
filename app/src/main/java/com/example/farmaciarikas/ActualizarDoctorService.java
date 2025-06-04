package com.example.farmaciarikas;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActualizarDoctorService extends Activity {

    private EditText editIdDoctor, editNombre, editEspecialidad, editJvpm, telefono, correo;
    private static final String TAG = "ActualizarDoctorService";

    // Reemplaza esta IP por la IP de tu servidor si estás en un entorno real
    private final String URL = String.format("http://%s/ws5/farmacia/actualizar_doctor.php", ControlService.BASE_IP);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_doctor_service);

        editIdDoctor = findViewById(R.id.editDoctor);
        editNombre = findViewById(R.id.editNombre);
        editEspecialidad = findViewById(R.id.editEspecialidad);
        editJvpm = findViewById(R.id.editJvpm);
        telefono = findViewById(R.id.editTelefonoDoctor);
        correo = findViewById(R.id.editCorreoDoctor);
    }

    public void actualizarDoctor(View v) {
        String idDoctor = editIdDoctor.getText().toString().trim();
        String nombre = editNombre.getText().toString().trim();
        String especialidad = editEspecialidad.getText().toString().trim();
        String jvpmText = editJvpm.getText().toString().trim();
        String telefonoText = telefono.getText().toString().trim();
        String correoText = correo.getText().toString().trim();

        if (idDoctor.isEmpty() || nombre.isEmpty() || especialidad.isEmpty() ||
                jvpmText.isEmpty() || telefonoText.isEmpty() || correoText.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject datos = new JSONObject();
            datos.put("idDoctor", idDoctor);
            datos.put("nombreDoctor", nombre);
            datos.put("especialidad", especialidad);
            datos.put("jvpm", jvpmText);
            datos.put("telefonoDoctor", telefonoText);
            datos.put("correoDoctor", correoText);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    datos,
                    response -> {
                        boolean resultado = response.optInt("resultado", 0) == 1;
                        String mensaje = response.optString("message", "Respuesta desconocida");
                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Respuesta del servidor: " + mensaje);
                    },
                    error -> {
                        String msg = (error.networkResponse != null)
                                ? "Error HTTP: " + error.networkResponse.statusCode
                                : "Error de red: " + error.getMessage();
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Error Volley: " + msg, error);
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception e) {
            Log.e(TAG, "Error al construir JSON", e);
            Toast.makeText(this, "Error al enviar datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v) {
        editIdDoctor.setText("");
        editNombre.setText("");
        editEspecialidad.setText("");
        editJvpm.setText("");
        telefono.setText("");
        correo.setText("");
        editIdDoctor.setEnabled(true);
    }

    public void consultarDoctor(View v) {
        String id = editIdDoctor.getText().toString().trim();
        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID", Toast.LENGTH_SHORT).show();
            return;
        }
        new ConsultarDocTask().execute(id);
    }

    private class ConsultarDocTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                String urlStr = String.format(
                        "http://%s/ws5/farmacia/consultar_doctor.php?idDoctor=%s",
                        ControlService.BASE_IP, id); // Asegúrate que BASE_IP esté definida
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int code = conn.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) {
                    return String.format(
                            "{\"success\":false,\"message\":\"HTTP error %d\"}", code);
                }

                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();

            } catch (Exception e) {
                return String.format(
                        "{\"success\":false,\"message\":\"%s\"}", e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                if (json.getBoolean("success")) {
                    editNombre.setText(json.optString("nombreDoctor", ""));
                    editEspecialidad.setText(json.optString("especialidad", ""));
                    editJvpm.setText(json.optString("jvpm", ""));
                    telefono.setText(json.optString("telefonoDoctor", ""));
                    correo.setText(json.optString("correoDoctor", ""));
                    editIdDoctor.setEnabled(false);
                } else {
                    String msg = json.optString("message", "No encontrado");
                    Toast.makeText(ActualizarDoctorService.this, msg, Toast.LENGTH_LONG).show();
                    limpiarTexto(null);
                }
            } catch (Exception e) {
                Toast.makeText(ActualizarDoctorService.this,
                        "Error al procesar respuesta del servidor", Toast.LENGTH_LONG).show();
            }
        }
    }


}

