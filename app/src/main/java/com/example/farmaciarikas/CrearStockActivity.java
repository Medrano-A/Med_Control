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

import java.sql.Date;

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
       etFechaVencimiento = (EditText) findViewById(R.id.etFechaVencimiento);
    }
    public void registrarStock(View view) {

        String regInsertados;
        Stock nuevoStock = recogerDatos();
        helper.abrir();
        regInsertados=helper.insertar(nuevoStock);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        limpiar();
    }
    public Stock recogerDatos() {

        String idstockT = etIdStock.getText().toString().trim();
        String codElementoT = etCodElemento.getText().toString().trim();
        String idLocalT = etIdLocal.getText().toString().trim();
        String cantidadT = etCantidad.getText().toString().trim();
        String fechaVencimientoT = etFechaVencimiento.getText().toString().trim();
        int idStock = Integer.parseInt(idstockT);
        int codElemento = Integer.parseInt(codElementoT);
        int idLocal = Integer.parseInt(idLocalT);
        int cantidad = Integer.parseInt(cantidadT);
        String fechaVencimiento = fechaVencimientoT;
        return new Stock(idStock, codElemento, idLocal, cantidad, fechaVencimiento);

    }
    public void limpiar(){
        etIdStock.setText("");
        etCodElemento.setText("");
        etIdLocal.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");

    }

}