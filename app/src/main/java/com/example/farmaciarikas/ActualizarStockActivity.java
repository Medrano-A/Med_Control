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

public class ActualizarStockActivity extends AppCompatActivity {
    EditText etIdStock;
    EditText etCodElemento;
    EditText etIdLocal;
    EditText etCantidad;
    EditText etFechaVencimiento;
    ControlDBFarmacia helper;
    Stock stock;
    EditText etBuscarId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_stock);
        helper = new ControlDBFarmacia(this);
        etBuscarId =(EditText) findViewById(R.id.etBuscarId);
        etIdStock = (EditText) findViewById(R.id.etIdStock);
        etCodElemento = (EditText) findViewById(R.id.etCodElemento);
        etIdLocal = (EditText) findViewById(R.id.etIdLocal);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        etFechaVencimiento = (EditText) findViewById(R.id.etFechaVencimiento);
    }
    public void buscarStock(View view) {
        limpiar();
        helper.abrir();
        stock = helper.consultarStock(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (stock != null) {
            etIdStock.setText(Integer.toString(stock.getIdStock()));
            etCodElemento.setText(Integer.toString(stock.getCodElemento()));
            etCantidad.setText(Integer.toString(stock.getCantidad()));
            etFechaVencimiento.setText(stock.getFechaVencimiento());
            etIdLocal.setText(Integer.toString(stock.getIdLocal()));

        }else{
            Toast.makeText(this, "El stock con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }

    }
    public void limpiar(){
        etIdStock.setText("");
        etCodElemento.setText("");
        etIdLocal.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");

    }
    public void actualizarStock(View view) {
        stock.setCantidad(Integer.parseInt(etCantidad.getText().toString()));
        stock.setCodElemento(Integer.parseInt(etCodElemento.getText().toString()));
        stock.setIdLocal(Integer.parseInt(etIdLocal.getText().toString()));
        stock.setFechaVencimiento(etFechaVencimiento.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(stock);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

}