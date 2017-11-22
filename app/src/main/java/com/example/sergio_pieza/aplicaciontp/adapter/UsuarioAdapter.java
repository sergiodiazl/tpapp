package com.example.sergio_pieza.aplicaciontp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio-pieza on 18/11/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> implements Filterable{

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private Context mContext;
    public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView nombre,email,zona;

    public ViewHolder(View vista){
        super(vista);
        nombre=(TextView)vista.findViewById(R.id.nombreContacto);
        email=(TextView)vista.findViewById(R.id.emailContacto);
        zona=(TextView) vista.findViewById(R.id.zonaContacto);
    }

}

    public UsuarioAdapter(Context context, List<Usuario>lista){
       usuarios=lista;
        usuariosFiltrados=lista;
        mContext=context;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public UsuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_contacto, parent, false);

        // Return a new holder instance
        UsuarioAdapter.ViewHolder viewHolder = new UsuarioAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UsuarioAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Usuario u = usuariosFiltrados.get(position);

        // Set item views based on your views and data model

        viewHolder.nombre.setText(u.getNombre());
        viewHolder.email.setText(u.getEmail());


    }
    @Override
    public int getItemCount() {
        return usuariosFiltrados!=null ? usuariosFiltrados.size():0;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence busqueda) {
                String busquedaString =busqueda.toString();
                if(busquedaString.isEmpty()){
                    usuariosFiltrados=usuarios;//momentos originales sin filtrar
                }else{
                    ArrayList<Usuario> listaFiltrados=new ArrayList<>();
                    for(Usuario usuarioAFiltrar :usuarios){
                        if(usuarioAFiltrar.getNombre().toLowerCase().contains(busquedaString)
                                ||usuarioAFiltrar.getEmail().toLowerCase().contains(busquedaString)   ){
                            listaFiltrados.add(usuarioAFiltrar);
                        }
                    }usuariosFiltrados=listaFiltrados;
                }
                FilterResults resultadosFiltro = new FilterResults();
                resultadosFiltro.values = usuariosFiltrados;
                return resultadosFiltro;
            }

            @Override
            protected void publishResults(CharSequence busqueda, FilterResults resultados) {
                usuariosFiltrados=(ArrayList<Usuario>)resultados.values;
                notifyDataSetChanged();

            }
        };
    }
}

