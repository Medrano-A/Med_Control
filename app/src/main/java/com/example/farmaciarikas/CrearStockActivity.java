package com.example.farmaciarikas;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearStockActivity extends AppCompatActivity {
    EditText etIdStock;
    EditText etCodElemento;
    EditText etIdLocal;
    EditText etCantidad;
    EditText etFechaVencimiento;
    ControlDBFarmacia helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_stock);
        helper = new ControlDBFarmacia(this);
       etIdStock = (EditText) findViewById(R.id.etIdStock);
       etCodElemento = (EditText) findViewById(R.id.etCodElemento);
       etIdLocal = (EditText) findViewById(R.id.etIdLocal);
       etCantidad = (EditText) findViewById(R.id.etCantidad);
       etCantidad.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String resultado = dest.subSequence(0, dstart)
                                + source.toString()
                                + dest.subSequence(dend, dest.length());
                        if (resultado.matches("^0.*") || !resultado.matches("\\d*")) {
                            return "";
                        }
                        return null;
                    }
                }
        });
       etFechaVencimiento = (EditText) findViewById(R.id.etFechaVencimiento);
    }
    public void registrarStock(View view) {

        String regInsertados;
        Stock nuevoStock = recogerDatos();
        if(nuevoStock !=null){
            helper.abrir();
            regInsertados=helper.insertar(nuevoStock);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    public Stock recogerDatos() {

        String idstockT = etIdStock.getText().toString().trim();
        String codElementoT = etCodElemento.getText().toString().trim();
        String idLocalT = etIdLocal.getText().toString().trim();
        String cantidadT = etCantidad.getText().toString().trim();
        String fechaIngresada = etFechaVencimiento.getText().toString();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formato.setLenient(false); // No permitir fechas como 2024-02-30

        try {
            java.util.Date fecha = formato.parse(fechaIngresada);
            java.util.Date fechaActual = new Date();

            if (fecha != null && fecha.after(fechaActual)) {
                int idStock = Integer.parseInt(idstockT);
                int codElemento = Integer.parseInt(codElementoT);
                int idLocal = Integer.parseInt(idLocalT);
                int cantidad = Integer.parseInt(cantidadT);
                String fechaVencimiento = fechaIngresada;
                return new Stock(idStock, codElemento, idLocal, cantidad, fechaVencimiento);

            } else {
                etFechaVencimiento.setError("La fecha debe ser mayor a la actual");
                return null;
            }
        } catch (ParseException e) {
            etFechaVencimiento.setError("Formato inválido. Usa yyyy-MM-dd");
            return null;
        }


    }
    public void limpiar(){
        etIdStock.setText("");
        etCodElemento.setText("");
        etIdLocal.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");

    }

}