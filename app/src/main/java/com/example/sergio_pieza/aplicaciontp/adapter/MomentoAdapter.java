package com.example.sergio_pieza.aplicaciontp.adapter;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMomentoF;

import com.example.sergio_pieza.aplicaciontp.helper.DownloadHelper;
import com.example.sergio_pieza.aplicaciontp.helper.RecyclerViewClickListener;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;
import com.example.sergio_pieza.aplicaciontp.sql.UsuarioDao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio-pieza on 09/11/2017.
 */

public class MomentoAdapter extends
        RecyclerView.Adapter<MomentoAdapter.ViewHolder> implements Filterable{
    private RecyclerViewClickListener mListener;
    private List<Momento>momentos;
    private List<Momento>momentosFiltrados;
    private Context mContext;
    private final int codigo_permiso=2;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView id,descripcion;
        public ImageView imagen;
        public Button botonDetalle,botonDescargar;
        public ViewHolder(View vista,RecyclerViewClickListener listener){
            super(vista);
            id=(TextView)vista.findViewById(R.id.idMomento);
            descripcion=(TextView)vista.findViewById(R.id.descripcionMomento);
            imagen=(ImageView) vista.findViewById(R.id.imagenMomento);
            botonDetalle=(Button)vista.findViewById(R.id.botonVerDetalleMomento);
            botonDescargar=(Button)vista.findViewById(R.id.botonDescargarImagen);
            botonDetalle.setOnClickListener(this);
            botonDescargar.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.botonVerDetalleMomento:
                mListener.onClick(view, getAdapterPosition());
                break;
                case R.id.botonDescargarImagen:
                    Momento mImagen=momentosFiltrados.get(getAdapterPosition());
                    String d=mImagen.getImagen();
                    String n=String.valueOf(mImagen.getUsuario_Id())+"_"+String.valueOf(mImagen.getId_m());

                    DownloadHelper.descargarImagen(d,mContext,n);

                    break;
            }
        }

    }

    public MomentoAdapter(Context context, List<Momento>lista,RecyclerViewClickListener listener){
        momentos=lista;
        momentosFiltrados=lista;//exp 1
        mContext=context;
        mListener=listener;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public MomentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View vistaMomento = inflater.inflate(R.layout.fragment_momento, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(vistaMomento,mListener);
        return viewHolder;
    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MomentoAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
       // Momento m = momentos.get(position);original
        Momento m = momentosFiltrados.get(position);//filtradp ex´3
        // Set item views based on your views and data model

        int idUsuario=m.getUsuario_Id();
        UsuarioDao uDao= new UsuarioDao(mContext);
        String nombre=uDao.nombrePorId(idUsuario);
        viewHolder.id.setText(nombre);
        viewHolder.descripcion.setText(m.getDescripcion());
        Glide.with(mContext).load(m.getImagen()).placeholder(R.drawable.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter()
                .override(500,500).into(viewHolder.imagen);
    }
    @Override
    public int getItemCount() {
        //return momentos!=null ? momentos.size():0;
        return momentosFiltrados!=null ? momentosFiltrados.size():0;//exp2
    }
    @Override//metodo de filtereable exp 4
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence busqueda) {
                String busquedaString =busqueda.toString();
                if(busquedaString.isEmpty()){
                    momentosFiltrados=momentos;//momentos originales sin filtrar
                }else{
                    ArrayList<Momento>listaFiltrados=new ArrayList<>();
                    for(Momento momentoAFiltrar :momentos){
                        if(momentoAFiltrar.getDescripcion().toLowerCase().contains(busquedaString)){
                            listaFiltrados.add(momentoAFiltrar);
                        }
                    }momentosFiltrados=listaFiltrados;
                }
                FilterResults resultadosFiltro = new FilterResults();
                resultadosFiltro.values = momentosFiltrados;
                return resultadosFiltro;
            }

            @Override
            protected void publishResults(CharSequence busqueda, FilterResults resultados) {
                momentosFiltrados=(ArrayList<Momento>)resultados.values;
                notifyDataSetChanged();

            }
        };
    }

}

