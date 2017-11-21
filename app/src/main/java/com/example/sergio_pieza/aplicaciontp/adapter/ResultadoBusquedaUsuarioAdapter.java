package com.example.sergio_pieza.aplicaciontp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio-pieza on 20/11/2017.
 */

public class ResultadoBusquedaUsuarioAdapter extends RecyclerView.Adapter<ResultadoBusquedaUsuarioAdapter.ViewHolder> {
public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView nombre,email,zona;

    public ViewHolder(View vista){
        super(vista);
        nombre=(TextView)vista.findViewById(R.id.nombreContactoResultado);
        email=(TextView)vista.findViewById(R.id.emailContactoResultado);
        zona=(TextView) vista.findViewById(R.id.zonaContactoResultado);
    }

}
    private ArrayList<Usuario> usuarios;
    private Context mContext;
    public ResultadoBusquedaUsuarioAdapter(Context context, ArrayList<Usuario>lista){
        usuarios=lista;
        mContext=context;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public ResultadoBusquedaUsuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.resultado_buscar_usuario, parent, false);

        // Return a new holder instance
        ResultadoBusquedaUsuarioAdapter.ViewHolder viewHolder = new ResultadoBusquedaUsuarioAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ResultadoBusquedaUsuarioAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Usuario u = usuarios.get(position);

        // Set item views based on your views and data model
Log.d("nombre",u.getNombre()); Log.d("mail",u.getEmail());

Log.d("posicion",String.valueOf(position+1));Log.d("de",String.valueOf(getItemCount()));
        viewHolder.nombre.setText(u.getNombre());
        viewHolder.email.setText(u.getEmail());


    }
    @Override
    public int getItemCount() {
        return usuarios!=null ? usuarios.size():0;
    }

}

