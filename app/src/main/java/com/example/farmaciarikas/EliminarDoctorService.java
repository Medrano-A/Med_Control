package com.example.farmaciarikas;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EliminarDoctorService extends Activity {

    EditText editCarnet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_eliminar);
        editCarnet = findViewById(R.id.editDoctor);
    }

    public void eliminarDoctor(View v) {
        String id = editCarnet.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del doctor", Toast.LENGTH_SHORT).show();
            return;
        }

        new EliminarDoctorTask().execute(id);
    }

    private class EliminarDoctorTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String idDoctor = params[0];
            try {
                String urlStr = String.format(
                        "http://%s/ws5/farmacia/eliminar_doctor.php?idDoctor=%s",
                        ControlService.BASE_IP, idDoctor);
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return "{\"resultado\":0,\"message\":\"Error HTTP " + responseCode + "\"}";
                }

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();

            } catch (Exception e) {
                return "{\"resultado\":0,\"message\":\"" + e.getMessage() + "\"}";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                String mensaje = json.optString("message", "Error desconocido");
                Toast.makeText(EliminarDoctorService.this, mensaje, Toast.LENGTH_LONG).show();

                // Si se eliminó correctamente, limpiar el campo
                if (json.optInt("resultado", 0) == 1) {
                    editCarnet.setText("");
                }

            } catch (Exception e) {
                Toast.makeText(EliminarDoctorService.this,
                        "Error al procesar respuesta", Toast.LENGTH_LONG).show();
            }
        }
    }
}
