package com.example.sergio_pieza.aplicaciontp.fragment;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.adapter.UsuarioAdapter;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergio-pieza on 20/11/2017.
 */

public class BuscarUsuariosF extends Fragment {
    String nombreUsuario;
    UsuarioAdapter rbuAdapter;
    ArrayList<Usuario> usuarios;
    RecyclerView rv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombreUsuario=getArguments().getString("nombreUsuario");
        Log.d("nombre on create",nombreUsuario);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        usuarios=new ArrayList<Usuario>();
        View view = inflater.inflate(R.layout.buscar_contacto_f, container, false);
        rv = (RecyclerView) view.findViewById(R.id.listaBuscarUsuarios);
        rv.setHasFixedSize(true);
        usuarios=new ArrayList<Usuario>();
        rbuAdapter=new UsuarioAdapter(this.getActivity(),usuarios);
        rv.setAdapter(rbuAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        VolleySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);
        return view;
    }
    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.URL,

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

                            String mensaje = obj.getString("mensaje");
                            Log.d("mensaje", mensaje);
                            JSONArray jsonMomentos = (JSONArray)(obj.getJSONArray("usuarios"));

                            for (int i =  jsonMomentos.length()-1; 0 <=i; i--) {
                                JSONObject m = jsonMomentos.getJSONObject(i);
                                int p1 = m.getInt("usuario_id");
                                String p2 = m.getString("nombre");
                                String p3 = m.getString("contrasena");
                                String p4 = m.getString("email");
                                int p5 = m.getInt("zona_id");
                                Log.d("valores de susuario ",(String.valueOf(p1)+","+p2+","+p3+","+p4+","+(String.valueOf(p1))));
                                Usuario nuevoUsuario = new Usuario(p1,p2,p3,p4,p5);
                                usuarios.add(nuevoUsuario);

                            }

                            rbuAdapter.notifyDataSetChanged();
                            Log.d("aca tendria que notificar","el adapdter");

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

            params.put("funcion", "buscarUsuarios");
            params.put("buscar", nombreUsuario);

            return params;
        }


    };

}
