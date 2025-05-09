package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EliminarStockActivity extends AppCompatActivity {
    EditText etBuscarId;
    TextView tvResultadoId;
    TextView tvResultadoElemento;
    TextView tvResultadoCantidad;
    TextView tvResultadoIdLocal;
    TextView tvResultadoFechaVencimiento;
    CardView cardResultado;
    ControlDBFarmacia helper;
    Stock stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar_stock);
        helper = new ControlDBFarmacia(this);
        etBuscarId =(EditText) findViewById(R.id.etBuscarId);
        tvResultadoId = (TextView) findViewById(R.id.tvResultadoId);
        tvResultadoElemento = (TextView) findViewById(R.id.tvResultadoIdElemento);
        tvResultadoIdLocal= (TextView) findViewById(R.id.tvResultadoIdLocal);
        tvResultadoFechaVencimiento = (TextView) findViewById(R.id.tvResultadoFechaVencimiento);
        tvResultadoCantidad = (TextView) findViewById(R.id.tvResultadoCantidad);
        cardResultado = findViewById(R.id.cardResultado);
    }
    public void buscarStock(View view) {
        helper.abrir();
        stock = helper.consultarStock(Integer.parseInt(etBuscarId.getText().toString()));
        helper.cerrar();
        if (stock != null) {
            tvResultadoId.setText(String.format("ID Stock: %d", stock.getIdStock()));
            tvResultadoElemento.setText(String.format("ID Elemento: %d", stock.getCodElemento()));
            tvResultadoCantidad.setText(String.format("Cantidad: %d", stock.getCantidad()));
            tvResultadoFechaVencimiento.setText(String.format("Fecha Vencimiento: %s", stock.getFechaVencimiento()));
            tvResultadoIdLocal.setText(String.format("ID Local: %d", stock.getIdLocal()));
            cardResultado.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "El stock con ID " +
                    etBuscarId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        }

    }
    public void borrarStock(View view){
        helper.abrir();
        String regEliminadas=helper.eliminar(stock);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
        limpiar();

    }
    public void limpiar(){
        tvResultadoId.setText("");
        tvResultadoElemento.setText("");
        tvResultadoCantidad.setText("");
        tvResultadoFechaVencimiento.setText("");
        tvResultadoIdLocal.setText("");
        cardResultado.setVisibility(View.INVISIBLE);

    }

}