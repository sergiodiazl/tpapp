package com.example.sergio_pieza.aplicaciontp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMomentoF;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio-pieza on 09/11/2017.
 */

public class MomentoAdapter extends
        RecyclerView.Adapter<MomentoAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id,imagen,descripcion;
        public ViewHolder(View vista){
            super(vista);
            id=(TextView)vista.findViewById(R.id.idMomento);
            descripcion=(TextView)vista.findViewById(R.id.descripcionMomento);
            imagen=(TextView)vista.findViewById(R.id.imagenMomento);
        }

    }
    private List<Momento>momentos;
    private Context mContext;
    public MomentoAdapter(Context context,List<Momento>lista){
        momentos=lista;
        mContext=context;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public MomentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_momento, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MomentoAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Momento m = momentos.get(position);

        // Set item views based on your views and data model

        int textoId=m.getId_m();
        viewHolder.id.setText(String.valueOf(textoId));
        viewHolder.descripcion.setText(m.getDescripcion());
        viewHolder.imagen.setText(m.getImagen());

    }
    @Override
    public int getItemCount() {
        return momentos.size();
    }

}
