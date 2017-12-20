package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sergio_pieza.aplicaciontp.adapter.MyPagerAdapter;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SesionHelper;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.helper.TextoHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class PerfilF extends Fragment implements View.OnClickListener {
    private EditText nombreNuevo,contrasenaVieja,contrasenaNueva1,contrasenaNueva2;
    private Button botonN,botonC,botonE,botonI,botonR;
    private Context mContext;
    private static final String LOCALE_KEY = "localekey";
    private static final String ESPANOL = "es";
    private static final String INGLES = "en";
    private static final String RUSO = "ru";
    private static final String LOCALE_PREF_KEY = "localePref";
    private Locale locale;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.perfil_f, container, false);
        setHasOptionsMenu(true);
        nombreNuevo=(EditText)view.findViewById(R.id.nombreP);
        contrasenaVieja=(EditText)view.findViewById(R.id.contrasenaVieja);
        contrasenaNueva1=(EditText)view.findViewById(R.id.contrasenaNueva1);
        contrasenaNueva2=(EditText)view.findViewById(R.id.contrasenaNueva2);
        botonC=(Button)view.findViewById(R.id.botonContrasena);
        botonN=(Button)view.findViewById(R.id.botonNombre);
        botonE=(Button)view.findViewById(R.id.botonEspanol);
        botonI=(Button)view.findViewById(R.id.botonIngles);
        botonR=(Button)view.findViewById(R.id.botonRuso);
        mContext=this.getActivity();
        botonN.setOnClickListener(this);
        botonC.setOnClickListener(this);
        botonI.setOnClickListener(this);
        botonE.setOnClickListener(this);
        botonR.setOnClickListener(this);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.perfil, menu);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int boton =item.getItemId();
        if (boton ==R.id.salir){
            SesionHelper.cerrarSesion(mContext);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonNombre:
               cambiarNombre();
                break;
            case R.id.botonContrasena:
                cambiarContrasena();
                break;
            case R.id.botonRuso:
                cambiarIdioma(RUSO);
                break;
            case R.id.botonEspanol:
                cambiarIdioma(ESPANOL);
                break;
            case R.id.botonIngles:
                cambiarIdioma(INGLES);
                break;

        }

    }
    public void cambiarIdioma(String idioma){
       SharedPrefHelper.getInstance(mContext).setLocale(idioma);

        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }
    public void cambiarNombre(){
        String errorNombre = mContext.getResources().getString(R.string.no_nombre);
        if (TextoHelper.textVacio(nombreNuevo,errorNombre)){
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(cambiarNombreRequest);
        }
    }
    public void cambiarContrasena(){
        String errorPass=mContext.getResources().getString(R.string.no_pass);
        String errorPass2=mContext.getResources().getString(R.string.no_pass2);
        String errorPass3=mContext.getResources().getString(R.string.no_pass3);
        String repetir=mContext.getResources().getString(R.string.pass_distinto);
        if(TextoHelper.textVacio(contrasenaVieja,errorPass) && TextoHelper.textVacio(contrasenaNueva1,errorPass3) && TextoHelper.textVacio(contrasenaNueva2,errorPass2)
         && TextoHelper.textIguales(contrasenaNueva1,contrasenaNueva2,errorPass2)){
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(cambiarPassRequest);
        }

    }

    StringRequest cambiarNombreRequest = new StringRequest(Request.Method.POST, Api.URL,

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
            String nuevoNombre =nombreNuevo.getText().toString().trim();
            String usuario=String.valueOf(SharedPrefHelper.getInstance(mContext).getUser().getId());
            params.put("funcion", "cambiarNombre");
            params.put("nombre",nuevoNombre);
            params.put("usuario_id",usuario);
            return params;
        }


    };
    StringRequest cambiarPassRequest = new StringRequest(Request.Method.POST, Api.URL,

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
            String cVieja =contrasenaVieja.getText().toString().trim();
            String cNueva =contrasenaNueva1.getText().toString().trim();
            String usuario=String.valueOf(SharedPrefHelper.getInstance(mContext).getUser().getId());
            params.put("funcion", "cambiarContrasena");
            params.put("contrasena",cVieja);
            params.put("contrasena_nueva",cNueva);
            params.put("usuario_id",usuario);
            return params;
        }


    };
}
