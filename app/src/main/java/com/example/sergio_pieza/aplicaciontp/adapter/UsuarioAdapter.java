package com.example.sergio_pieza.aplicaciontp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Contacto;
import com.example.sergio_pieza.aplicaciontp.sql.ContactoDao;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergio-pieza on 18/11/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> implements View.OnClickListener{

    private List<Usuario> usuarios;

    private Context mContext;

    @Override
    public void onClick(View v) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nombre,email,zona;
    public FloatingActionButton boton;
    public ViewHolder(View vista){
        super(vista);
        nombre=(TextView)vista.findViewById(R.id.nombreContacto);
        email=(TextView)vista.findViewById(R.id.emailContacto);
        zona=(TextView) vista.findViewById(R.id.zonaContacto);
        boton=(FloatingActionButton)vista.findViewById(R.id.botonListaContacto);
        boton.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
                if(v instanceof FloatingActionButton){
                    int pos =getAdapterPosition();
                    int idSeguido=usuarios.get(pos).getId();
                    FloatingActionButton b=(FloatingActionButton)v;
                    Drawable imagenB =b.getDrawable();
                    if (esAgregar(imagenB)){
                        agregarContacto(idSeguido);
                        b.setImageResource(R.drawable.borrarcontacto);
                       }else{
                        alertaBorrarContacto(idSeguido,b);

                       }

                }
            }
        }


    public UsuarioAdapter(Context context, List<Usuario>lista){
       usuarios=lista;

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


    public boolean esAgregar(Drawable imagen){
        Drawable.ConstantState cImagen = imagen.getConstantState();
        Drawable.ConstantState cAgregar =mContext.getResources().getDrawable(R.drawable.agregarcontacto).getConstantState();
        if(cImagen.equals(cAgregar)){
            return true;
        }else{
            return false;
        }

    }
    public void agregarContacto(int idSeguido){
    Log.d("agregado user #",String.valueOf(idSeguido));
    int idSeguidor= SharedPrefHelper.getInstance(mContext).getUser().getId();
    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try{
                            //convierta json a la respuesta
                            JSONObject obj = new JSONObject(response);
                            //si hay error muestra el mensaje
                            JSONObject contacto = obj.getJSONObject("contacto");
                            int id = contacto.getInt("contacto_id");
                            int u1=contacto.getInt("seguidor_id");
                            int u2=contacto.getInt("seguido_id");
                            Contacto nuevoContacto=new Contacto(id,u1,u2);
                            ContactoDao cDao=new ContactoDao(mContext);
                            cDao.insertar(nuevoContacto);
                            Toast.makeText(mContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response",error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("funcion", "agregarContacto");
                String u1=String.valueOf(idSeguidor);
                String u2 =String.valueOf(idSeguido);
               params.put("usuario1",u1);
               params.put("usuario2",u2);
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(postRequest);



    }

    public void eliminarContacto(int idSeguido){
        Log.d("eliminado user #",String.valueOf(idSeguido));
        int idSeguidor= SharedPrefHelper.getInstance(mContext).getUser().getId();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try{
                            //convierta json a la respuesta
                            JSONObject obj = new JSONObject(response);
                            //si hay error muestra el mensaje
                            ContactoDao cDao =new ContactoDao(mContext);
                            cDao.borrar(idSeguidor,idSeguido);
                            Toast.makeText(mContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response",error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("funcion", "borrarContacto");
                String u1=String.valueOf(idSeguidor);
                String u2 =String.valueOf(idSeguido);
                params.put("usuario1",u1);
                params.put("usuario2",u2);
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(postRequest);



    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UsuarioAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Usuario u = usuarios.get(position);

        // Set item views based on your views and data model
        int miId=SharedPrefHelper.getInstance(mContext).getUser().getId();
        int otroId=u.getId();
        viewHolder.nombre.setText(u.getNombre());
        viewHolder.email.setText(u.getEmail());
        if(otroId==miId){
            viewHolder.boton.hide();
        }
        ContactoDao cDao =new ContactoDao(mContext);
        if(!cDao.esContacto(miId,otroId)){
            viewHolder.boton.setImageResource(R.drawable.agregarcontacto);
        }





    }
    @Override
    public int getItemCount() {
        return usuarios!=null ? usuarios.size():0;
    }

    protected void alertaBorrarContacto(int idSeguido,FloatingActionButton b) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(mContext.getResources().getString(R.string.enciendaGps))
                .setCancelable(false)
                .setPositiveButton(mContext.getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                       eliminarContacto(idSeguido);
                       b.setImageResource(R.drawable.agregarcontacto);
                    }
                })
                .setNegativeButton(mContext.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}

