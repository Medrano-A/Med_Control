package com.example.farmaciarikas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servicio-cliente que consulta un doctor por ID usando un
 * GET a consultar_doctor.php y muestra los campos recibidos,
 * con logs para depuración.
 */
public class ConsultarDoctorService extends AppCompatActivity {

    private static final String TAG = "ConsultarDoctorService";

    private EditText editId, editNombre, editEspec, editJvpm,
            editTel, editCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_doctor_service);

        editId     = findViewById(R.id.editDoctor);
        editNombre = findViewById(R.id.editNombreDoctor);
        editEspec  = findViewById(R.id.editEspecialidad);
        editJvpm   = findViewById(R.id.editJvpm);
        editTel    = findViewById(R.id.editTelefonoDoctor);
        editCorreo = findViewById(R.id.editCorreoDoctor);

        Log.d(TAG, "onCreate: ConsultarDoctorService iniciado.");
    }

    /** Vinculado con android:onClick="consultarDoctor" */
    public void consultarDoctor(View v) {
        String id = editId.getText().toString().trim();
        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "consultarDoctor: ID vacío");
            return;
        }
        Log.d(TAG, "consultarDoctor: iniciando AsyncTask con ID=" + id);
        new ConsultarDocTask().execute(id);
    }

    /** Limpia los EditText resultado; enlazado con android:onClick="limpiarTexto" */
    public void limpiarTexto(View v) {
        editNombre.setText("");
        editEspec.setText("");
        editJvpm.setText("");
        editTel.setText("");
        editCorreo.setText("");
        Log.d(TAG, "limpiarTexto: campos de resultado limpios");
    }

    /** AsyncTask para la llamada GET */
    private class ConsultarDocTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Aquí podrías mostrar un progress bar (si tuvieras uno)
            Log.d(TAG, "ConsultarDocTask: onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                String urlStr = String.format(
                        "http://%s/ws5/farmacia/consultar_doctor.php?idDoctor=%s",
                        ControlService.BASE_IP, id);
                Log.d(TAG, "ConsultarDocTask: URL = " + urlStr);

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int code = conn.getResponseCode();
                Log.d(TAG, "ConsultarDocTask: código HTTP = " + code);
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
                Log.d(TAG, "ConsultarDocTask: respuesta cruda = " + sb.toString());
                return sb.toString();

            } catch (Exception e) {
                Log.e(TAG, "ConsultarDocTask: excepción en doInBackground", e);
                return String.format(
                        "{\"success\":false,\"message\":\"%s\"}", e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: procesando respuesta JSON");

            try {
                JSONObject json = new JSONObject(s);
                if (json.getBoolean("success")) {
                    editNombre.setText(json.optString("nombreDoctor", ""));
                    editEspec.setText(json.optString("especialidad", ""));
                    editJvpm.setText(json.optString("jvpm", ""));
                    editTel.setText(json.optString("telefonoDoctor", ""));
                    editCorreo.setText(json.optString("correoDoctor", ""));
                    Log.d(TAG, "onPostExecute: doctor encontrado y mostrado");
                } else {
                    String msg = json.optString("message", "No encontrado");
                    Toast.makeText(ConsultarDoctorService.this, msg, Toast.LENGTH_LONG).show();
                    limpiarTexto(null);
                    Log.d(TAG, "onPostExecute: success=false, mensaje = " + msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onPostExecute: error al parsear JSON", e);
                Toast.makeText(ConsultarDoctorService.this,
                        "Error al parsear respuesta", Toast.LENGTH_LONG).show();
            }
        }
    }
}
