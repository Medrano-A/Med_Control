package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale; // Asegúrate de importar Locale para el formateo del total

public class TransaccionConsultarActivity extends AppCompatActivity {

    private EditText editIdTransaccionConsulta;
    private Button btnBuscarTransaccionConsulta;
    private TextView textTransaccionDui,
            textTransaccionIdUsuario,
            textTransaccionIdLocal,
            textTransaccionFecha,
            textTransaccionTotal,
            textTransaccionTipo,
            labelDetalles;
    private LinearLayout containerDetalleTransaccion;
    private View layoutResultadosTransaccion; // Referencia al layout que contiene los TextViews de resultados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_consultar);

        editIdTransaccionConsulta   = findViewById(R.id.editTransaccionIdConsulta);
        btnBuscarTransaccionConsulta = findViewById(R.id.btnTransaccionBuscarConsulta);

        // Referencias a los TextViews dentro del layout de resultados
        layoutResultadosTransaccion  = findViewById(R.id.layoutResultadosTransaccion); // ID del LinearLayout contenedor
        textTransaccionDui           = findViewById(R.id.textTransaccionDui);
        textTransaccionIdUsuario     = findViewById(R.id.textTransaccionIdUsuario);
        textTransaccionIdLocal       = findViewById(R.id.textTransaccionIdLocal);
        textTransaccionFecha         = findViewById(R.id.textTransaccionFecha);
        textTransaccionTotal         = findViewById(R.id.textTransaccionTotal);
        textTransaccionTipo          = findViewById(R.id.textTransaccionTipo);

        labelDetalles                = findViewById(R.id.labelDetalles);
        containerDetalleTransaccion  = findViewById(R.id.containerDetalleTransaccion);


        // Inicialmente ocultar resultados
        if (layoutResultadosTransaccion != null) {
            layoutResultadosTransaccion.setVisibility(View.GONE);
        }
        labelDetalles.setVisibility(View.GONE);
        containerDetalleTransaccion.setVisibility(View.GONE);

        btnBuscarTransaccionConsulta.setOnClickListener(v -> buscarTransaccion());
    }

    private void buscarTransaccion() {
        String idStr = editIdTransaccionConsulta.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un ID de Transacción.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ocultar resultados anteriores antes de una nueva búsqueda
        if (layoutResultadosTransaccion != null) {
            layoutResultadosTransaccion.setVisibility(View.GONE);
        }
        labelDetalles.setVisibility(View.GONE);
        containerDetalleTransaccion.removeAllViews();
        containerDetalleTransaccion.setVisibility(View.GONE);


        try {
            int id = Integer.parseInt(idStr);
            // Asegúrate que Model.init(this.getApplicationContext()); se haya llamado antes (ej. en Application class o MainActivity)
            Transaccion t = Transaccion.getByPk(id);
            if (t == null) {
                Toast.makeText(this, "Transacción no encontrada con ID " + id, Toast.LENGTH_SHORT).show();
                return;
            }

            // Mostrar resultados
            if (layoutResultadosTransaccion != null) {
                layoutResultadosTransaccion.setVisibility(View.VISIBLE);
            }

            String dui = t.getDui();
            String idUsuario = t.getIdUsuario();
            Integer idLocal = t.getIdLocal();

            textTransaccionDui.setText("DUI: " + (dui != null && !dui.isEmpty() ? dui : "N/A"));
            textTransaccionIdUsuario.setText("ID Usuario: " + (idUsuario != null && !idUsuario.isEmpty() ? idUsuario : "N/A"));
            textTransaccionIdLocal.setText("ID Local: " + (idLocal != null ? String.valueOf(idLocal) : "N/A"));
            textTransaccionFecha.setText("Fecha: " + (t.getFecha() != null ? t.getFecha() : "N/A"));
            textTransaccionTotal.setText("Total: " + String.format(Locale.getDefault(), "%.2f", t.getTotal()));
            textTransaccionTipo.setText("Tipo: " + (t.getTipo() != null ? t.getTipo() : "N/A"));

            // Cargar todos los detalles
            DetalleTransaccion[] detalles = DetalleTransaccion.getByTransaccion(id);
            if (detalles.length > 0) {
                labelDetalles.setVisibility(View.VISIBLE);
                containerDetalleTransaccion.setVisibility(View.VISIBLE);
                for (DetalleTransaccion d : detalles) {
                    TextView tv = new TextView(this);
                    tv.setPadding(0, 8, 0, 8); // Añadir un poco de padding
                    tv.setTextSize(14); // Ajustar tamaño de texto para detalles
                    tv.setText(
                            "ID Detalle: " + d.getIdDetalle()
                                    + "  | Cant: " + d.getCantidad()
                                    + "  | P.U.: " + String.format(Locale.getDefault(), "%.2f", d.getPrecioUnitario())
                                    + "  | Subt.: " + String.format(Locale.getDefault(), "%.2f", d.getSubtotal())
                    );
                    containerDetalleTransaccion.addView(tv);
                }
            } else {
                // No hay detalles, asegurarse de que esté oculto
                labelDetalles.setVisibility(View.GONE);
                containerDetalleTransaccion.setVisibility(View.GONE);
            }

            // Opcional: Deshabilitar el campo de búsqueda y botón tras una búsqueda exitosa
            // editIdTransaccionConsulta.setEnabled(false);
            // btnBuscarTransaccionConsulta.setEnabled(false);
            Toast.makeText(this, "Transacción encontrada.", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato inválido de ID.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        editIdTransaccionConsulta.setEnabled(true); // Siempre permitir nueva búsqueda al volver
        // Opcional: Limpiar el campo de búsqueda y los resultados al volver a la actividad
        editIdTransaccionConsulta.setText("");
        if (layoutResultadosTransaccion != null) {
            layoutResultadosTransaccion.setVisibility(View.GONE);
        }
        labelDetalles.setVisibility(View.GONE);
        containerDetalleTransaccion.removeAllViews();
        containerDetalleTransaccion.setVisibility(View.GONE);
    }
}
