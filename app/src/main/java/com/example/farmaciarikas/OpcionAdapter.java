package com.example.farmaciarikas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class OpcionAdapter extends ArrayAdapter<Opcion> {

    private Context context;
    private List<Opcion> opciones;

    public OpcionAdapter(Context context, List<Opcion> opciones) {
        super(context, 0, opciones);
        this.context = context;
        this.opciones = opciones;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Opcion opcion = opciones.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_opcion, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.imgOpcion);
        TextView txt = convertView.findViewById(R.id.txtOpcion);

        img.setImageResource(opcion.getIconoResId());
        txt.setText(opcion.getNombre());

        return convertView;
    }
}
