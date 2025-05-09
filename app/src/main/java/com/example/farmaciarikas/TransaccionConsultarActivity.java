// TransaccionConsultarActivity.java
package com.example.farmaciarikas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransaccionConsultarActivity extends AppCompatActivity {

    private EditText editIdTransaccionConsulta;
    private Button btnBuscarTransaccionConsulta;
    private Button btnTransaccionVerDetalles;
    private TextView textTransaccionDui;
    private TextView textTransaccionIdUsuario;
    private TextView textTransaccionIdLocal;
    private TextView textTransaccionFecha;
    private TextView textTransaccionTotal;
    private TextView textTransaccionTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_consultar);

        // Vinculación de vistas
        editIdTransaccionConsulta   = findViewById(R.id.editTransaccionIdConsulta);
        btnBuscarTransaccionConsulta = findViewById(R.id.btnTransaccionBuscarConsulta);
        btnTransaccionVerDetalles    = findViewById(R.id.btnTransaccionVerDetalles);
        textTransaccionDui           = findViewById(R.id.textTransaccionDui);
        textTransaccionIdUsuario     = findViewById(R.id.textTransaccionIdUsuario);
        textTransaccionIdLocal       = findViewById(R.id.textTransaccionIdLocal);
        textTransaccionFecha         = findViewById(R.id.textTransaccionFecha);
        textTransaccionTotal         = findViewById(R.id.textTransaccionTotal);
        textTransaccionTipo          = findViewById(R.id.textTransaccionTipo);

        btnTransaccionVerDetalles.setVisibility(Button.GONE);

        btnBuscarTransaccionConsulta.setOnClickListener(v -> buscarTransaccion());
        btnTransaccionVerDetalles.setOnClickListener(v -> abrirDetalles());
    }

    private void buscarTransaccion() {
        String idStr = editIdTransaccionConsulta.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this,
                    "Debe ingresar un ID de Transacción.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        limpiarCampos();
        btnTransaccionVerDetalles.setVisibility(Button.GONE);

        try {
            int id = Integer.parseInt(idStr);
            Transaccion t = Transaccion.getByPk(id);
            if (t != null) {
                textTransaccionDui.setText("DUI: " + t.getDui());
                textTransaccionIdUsuario.setText("ID Usuario: " + t.getIdUsuario());
                textTransaccionIdLocal.setText("ID Local: " + t.getIdLocal());
                textTransaccionFecha.setText("Fecha: " + t.getFecha());
                textTransaccionTotal.setText("Total: " + t.getTotal());
                textTransaccionTipo.setText("Tipo: " + t.getTipo());

                btnTransaccionVerDetalles.setVisibility(Button.VISIBLE);
                editIdTransaccionConsulta.setEnabled(false);

                Toast.makeText(this,
                        "Transacción encontrada.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "No se encontró Transacción con ID " + id,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Formato inválido de ID.",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error al consultar: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        textTransaccionDui.setText("DUI:");
        textTransaccionIdUsuario.setText("ID Usuario:");
        textTransaccionIdLocal.setText("ID Local:");
        textTransaccionFecha.setText("Fecha:");
        textTransaccionTotal.setText("Total:");
        textTransaccionTipo.setText("Tipo:");
    }

    private void abrirDetalles() {
        int id = Integer.parseInt(editIdTransaccionConsulta.getText().toString());
        Intent i = new Intent(this, DetalleTransaccionMenuActivity.class);
        i.putExtra("IDTRANSACCION", id);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editIdTransaccionConsulta.setEnabled(true);
    }
}
