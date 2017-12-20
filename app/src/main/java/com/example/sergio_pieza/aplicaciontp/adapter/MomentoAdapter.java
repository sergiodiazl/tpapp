package com.example.sergio_pieza.aplicaciontp.adapter;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMomentoF;

import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.DownloadHelper;
import com.example.sergio_pieza.aplicaciontp.helper.RecyclerViewClickListener;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.helper.TextoHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;
import com.example.sergio_pieza.aplicaciontp.sql.UsuarioDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        public TextView id,descripcion;
        public ImageView imagen;
        public Button botonDetalle,botonDescargar;
        public FloatingActionButton botonEditar,botonBorrar;
        public ViewHolder(View vista,RecyclerViewClickListener listener){
            super(vista);
            id=(TextView)vista.findViewById(R.id.idMomento);
            descripcion=(TextView)vista.findViewById(R.id.descripcionMomento);
            imagen=(ImageView) vista.findViewById(R.id.imagenMomento);
            botonDetalle=(Button)vista.findViewById(R.id.botonVerDetalleMomento);
            botonDescargar=(Button)vista.findViewById(R.id.botonDescargarImagen);
            botonEditar=(FloatingActionButton)vista.findViewById((R.id.botonEditarMomento));
            botonBorrar=(FloatingActionButton)vista.findViewById((R.id.botonBorrarMomento));
            botonDetalle.setOnClickListener(this);
            botonDescargar.setOnClickListener(this);
            imagen.setOnLongClickListener(this);
            botonBorrar.setOnClickListener(this);
            botonEditar.setOnClickListener(this);

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
                case R.id.botonEditarMomento:
                    editarDescripcion(getAdapterPosition());
                    break;
                case  R.id.botonBorrarMomento:
                    borrarMomento(getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {

                case R.id.imagenMomento:
                    Log.d("click largo",String.valueOf(getAdapterPosition()));
                    Momento mImagen = momentosFiltrados.get(getAdapterPosition());
                    String d = mImagen.getImagen();
                    String n = String.valueOf(mImagen.getUsuario_Id()) + "_" + String.valueOf(mImagen.getId_m());

                    DownloadHelper.descargarImagen(d, mContext, n);

                    break;
            }return false;
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
        int miId= SharedPrefHelper.getInstance(mContext).getUser().getId();
        Log.d("mi id",String.valueOf(miId));
        Log.d("otro id",String.valueOf(idUsuario));
        if( miId!=idUsuario){
            viewHolder.botonBorrar.hide();
            viewHolder.botonEditar.hide();
        }


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
                        if(momentoAFiltrar.getDescripcion().toLowerCase().contains("#"+busquedaString)){
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
    private void editarDescripcion(int posicion){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        String mensaje =mContext.getString(R.string.editarDescripcion);
        String si =mContext.getString(R.string.si);
        String no =mContext.getString(R.string.no);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView = layoutInflater.inflate(R.layout.editar_descripcion, null);
        alertDialogBuilder.setView(rootView);

        final EditText nuevaDescripcion= (EditText) rootView .findViewById(R.id.nuevaDescripcion);
        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get the `EditText` value
                        String nDes=nuevaDescripcion.getText().toString().trim();
                        if(!TextoHelper.editVacio(nuevaDescripcion,"escriba")){
                            Toast.makeText(mContext,"vacio",Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(mContext,"EditText value" +nDes,Toast.LENGTH_LONG).show();
                            String d=nuevaDescripcion.getText().toString().trim();

                            int idM =momentosFiltrados.get(posicion).getId_m();
                            editarDescripcionRequest(idM,d,posicion);

                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton(no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void borrarMomento(int posicion){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        String mensaje =mContext.getString(R.string.borrarMomento);
        String si =mContext.getString(R.string.si);
        String no =mContext.getString(R.string.no);

        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setPositiveButton(si,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int idM=momentos.get(posicion).getId_m();
                        borrarMomentoRequest(idM,posicion);

                    }
                });

        alertDialogBuilder.setNegativeButton(no,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
private void editarDescripcionRequest(int idM,String d,int pos){
    StringRequest descripcionRequest = new StringRequest(Request.Method.POST, Api.URL,

            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response
                    try {
                        //convierta json a la respuesta
                        JSONObject obj = new JSONObject(response);
                        //si hay error muestra el mensaje
                        if (obj.getBoolean("error") == true) {
                            String mensaje = obj.getString("mensaje");
                            Log.d("mensaje", mensaje);

                        } else {
                            momentosFiltrados.get(pos).setDescripcion(d);
                            notifyDataSetChanged();
                            String mensaje = obj.getString("mensaje");
                            Log.d("mensaje", mensaje);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_timeout_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_auth),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_server),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_parse),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            String usuario=String.valueOf(SharedPrefHelper.getInstance(mContext).getUser().getId());
            params.put("funcion", "cambiarDescripcion");
            params.put("descripcion",d);
            params.put("momento_id",String.valueOf(idM));
            return params;
        }


    };
    VolleySingleton.getInstance(mContext).addToRequestQueue(descripcionRequest);
}
private void borrarMomentoRequest(int idM,int pos){
    StringRequest borrarRequest = new StringRequest(Request.Method.POST, Api.URL,

            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response
                    try {
                        //convierta json a la respuesta
                        JSONObject obj = new JSONObject(response);
                        //si hay error muestra el mensaje
                        if (obj.getBoolean("error") == true) {
                            String mensaje = obj.getString("mensaje");
                            Log.d("mensaje", mensaje);

                        } else {
                            momentos.remove(pos);
                            notifyDataSetChanged();
                            String mensaje = obj.getString("mensaje");
                            Log.d("mensaje", mensaje);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_timeout_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_auth),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_server),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getContext(),
                                getContext().getString(R.string.error_parse),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            String usuario=String.valueOf(SharedPrefHelper.getInstance(mContext).getUser().getId());
            params.put("funcion", "borrarMomento");
            params.put("momento_id",String.valueOf(idM));
            return params;
        }


    };
    VolleySingleton.getInstance(mContext).addToRequestQueue(borrarRequest);
}
}

