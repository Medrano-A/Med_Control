package com.example.farmaciarikas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ClienteActualizarActivity extends AppCompatActivity {

    Button buscar;
    Boolean yaBusco = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_actualizar);

        buscar = findViewById(R.id.buttonActualizarCliente);
        buscar.setText(R.string.ActualizarCliente_btn_buscar);

        //Logica para cambiar de Buscar a Actualizar.
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!yaBusco){
                    yaBusco=true;
                    buscar.setText(R.string.ActualizarCliente_btn_actualizar);
                }
                else{
                    yaBusco=false;
                    buscar.setText(R.string.ActualizarCliente_btn_buscar);
                }

            }
        });
    }
}