package com.example.farmaciarikas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Servicio-cliente que envía un JSON al PHP insertar_doctor.php
 * y muestra el mensaje devuelto por el backend, con logs para depuración.
 */
public class InsertarDoctorService extends AppCompatActivity {

    private static final String TAG = "InsertarDoctorService";

    // Controles UI
    private EditText editIdDoc, editNombreDoctor, editEspecialidad,
            editJvpm, editTelefonoDoctor, editCorreoDoctor;

    // URL del servicio PHP
    private final String URL = String.format(
            "http://%s/ws5/farmacia/insertar_doctor.php", ControlService.BASE_IP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_doctor_service);

        // Referencias
        editIdDoc          = findViewById(R.id.editIdDoc);
        editNombreDoctor   = findViewById(R.id.editNombreDoctor);
        editEspecialidad   = findViewById(R.id.editEspecialidad);
        editJvpm           = findViewById(R.id.editJvpm);
        editTelefonoDoctor = findViewById(R.id.editTelefonoDoctor);
        editCorreoDoctor   = findViewById(R.id.editCorreoDoctor);

        Log.d(TAG, "onCreate: InsertarDoctorService iniciado. URL=" + URL);
    }

    /** Vinculado en XML con android:onClick="insertarDoctor" */
    public void insertarDoctor(View v) {
        // Capturar datos
        String idDoc   = editIdDoc.getText().toString().trim();
        String nombre  = editNombreDoctor.getText().toString().trim();
        String espec   = editEspecialidad.getText().toString().trim();
        String jvpm    = editJvpm.getText().toString().trim();
        String tel     = editTelefonoDoctor.getText().toString().trim();
        String correo  = editCorreoDoctor.getText().toString().trim();

        // Validación básica
        if (idDoc.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "ID y Nombre son obligatorios", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertarDoctor: validación fallida. idDoc=" + idDoc + " nombre=" + nombre);
            return;
        }

        try {
            // Construir JSON de envío
            JSONObject json = new JSONObject();
            json.put("idDoctor", idDoc);
            json.put("nombreDoctor", nombre);
            json.put("especialidad", espec);
            json.put("jvpm", jvpm);
            json.put("telefonoDoctor", tel);
            json.put("correoDoctor", correo);

            Log.d(TAG, "insertarDoctor: JSON construido = " + json.toString());

            // Petición Volley
            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    json,
                    response -> {
                        boolean success = response.optBoolean("success", false);
                        String msg = response.optString("message", "Sin mensaje");
                        Log.d(TAG, "Respuesta servidor (success=" + success + "): " + msg);
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    },
                    error -> {
                        String mensaje;
                        if (error.networkResponse != null) {
                            mensaje = "HTTP Err: " + error.networkResponse.statusCode;
                            Log.e(TAG, "Error HTTP código: " + error.networkResponse.statusCode);
                        } else {
                            mensaje = "Error de red: " + error.getMessage();
                            Log.e(TAG, "Error de red: " + error.getMessage(), error);
                        }
                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(req);
            Log.d(TAG, "insertarDoctor: petición añadida a la cola");

        } catch (Exception e) {
            Log.e(TAG, "insertarDoctor: excepción al construir JSON o enviar petición", e);
            Toast.makeText(this, "Error JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /** (opcional) Limpia todas las cajas; enlázalo en XML si lo deseas. */
    public void limpiarCampos(View v) {
        editIdDoc.setText("");
        editNombreDoctor.setText("");
        editEspecialidad.setText("");
        editJvpm.setText("");
        editTelefonoDoctor.setText("");
        editCorreoDoctor.setText("");
        Log.d(TAG, "limpiarCampos: campos reseteados");
    }
}
