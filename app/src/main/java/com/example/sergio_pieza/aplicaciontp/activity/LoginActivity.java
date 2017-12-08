package com.example.sergio_pieza.aplicaciontp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Contacto;
import com.example.sergio_pieza.aplicaciontp.sql.ContactoDao;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;
import com.example.sergio_pieza.aplicaciontp.sql.UsuarioDao;
import com.example.sergio_pieza.aplicaciontp.sql.Zona;
import com.example.sergio_pieza.aplicaciontp.sql.ZonaDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static LoginActivity loginActivity;
    Context context = this;
    public static LoginActivity getInstance(){
    return  loginActivity;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity=this;
        if (SharedPrefHelper.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this,HomeActivity.class));
            return;
        }
        findViewById(R.id.botonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.botonARegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aRegistrar();
            }
        });
        email=(EditText)findViewById(R.id.emailLogin);
        pass= (EditText)findViewById(R.id.passLogin);
    }
    private void login(){
        final String emailU = email.getText().toString().trim();
        final String passU = pass.getText().toString().trim();
        if (!checkEmail(emailU)){
            email.setError("enail no valido");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passU)) {
            pass.setError("Escriba la contraseña");
            pass.requestFocus();
            return;
        }
        Zona z1=new Zona(1,"zona sur");
        Zona z2=new Zona(2,"zona norte");
        Zona z3=new Zona(3,"zona oeste");
        Zona z4=new Zona(4,"zona este");
        ZonaDao zDao=new ZonaDao(context);
        zDao.insertar(z1);
        zDao.insertar(z2);
        zDao.insertar(z3);
        zDao.insertar(z4);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, Api.URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try{
                            //convierta json a la respuesta
                            Log.d("primera request",response);
                            JSONObject obj = new JSONObject(response);
                            //si hay error muestra el mensaje
                        if(obj.getBoolean("error")==true ){
                            String mensaje= obj.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        }else{
                       JSONObject u =obj.getJSONObject("usuario");
                       int i = u.getInt("usuario_id");
                       int z=u.getInt("zona_id");
                       String n=u.getString("nombre");
                       String e=u.getString("email");
                       String c =u.getString("contrasena");
                            Usuario login=new Usuario(i,n,c,e,z);
                            SharedPrefHelper.getInstance(getApplicationContext()).userLogin(login);
                            UsuarioDao uDao=new UsuarioDao(context);
                            uDao.insertar(login);
                            VolleySingleton.getInstance(context).addToRequestQueue(usuariosRequest);

                        }


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
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_timeout_red),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_auth),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_server),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_red),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_parse),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("funcion", "login");
                params.put("contrasena", passU);
                params.put("email", emailU);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(loginRequest);


    }
    private void aRegistrar(){
        finish();
        startActivity(new Intent(this,RegistrarActivity.class));
        return;


    }
    private void aHome(){
        finish();
        Log.d("entro a  aHOme()","si");
        startActivity(new Intent(this,HomeActivity.class));
        return;
    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private StringRequest usuariosRequest =new StringRequest(Request.Method.POST, Api.URL,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    try{
                        //convierta json a la respuesta
                        Log.d("segunda request login ",response);
                        JSONObject obj = new JSONObject(response);
                        //si hay error muestra el mensaje
                        if(obj.getBoolean("error")==true ){
                            String mensaje= obj.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        }else{
                            String mensaje= obj.getString("mensaje");

                            JSONArray jsonUsuarios =obj.getJSONArray("usuarios");
                            UsuarioDao uDao=new UsuarioDao(context);
                           for(int i=0,size=jsonUsuarios.length();i<size;i++){
                               JSONObject u=jsonUsuarios.getJSONObject(i);
                               int id = u.getInt("usuario_id");
                               int z=u.getInt("zona_id");
                               String n=u.getString("nombre");
                               String e=u.getString("email");
                               String c =u.getString("contrasena");

                               Usuario nuevoUsuario=new Usuario(id,n,c,e,z);
                               uDao.insertar(nuevoUsuario);
                           }



                            VolleySingleton.getInstance(context).addToRequestQueue(contactosRequest);
                        }


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
                    // error
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_timeout_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_auth),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_server),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_parse),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();

            Usuario uActual = SharedPrefHelper.getInstance(context).getUser();
            String idU = String.valueOf(uActual.getId());
            params.put("funcion", "usuariosContactados");
            params.put("usuario_id", idU);
            return params;
        }
    };
    private StringRequest contactosRequest =new StringRequest(Request.Method.POST, Api.URL,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    try{
                        //convierta json a la respuesta
                        Log.d("segunda request login ",response);
                        JSONObject obj = new JSONObject(response);
                        //si hay error muestra el mensaje
                        if(obj.getBoolean("error")==true ){
                            String mensaje= obj.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        }else{
                            String mensaje= obj.getString("mensaje");

                            JSONArray jsonUsuarios =obj.getJSONArray("contactos");
                            ContactoDao cDao=new ContactoDao(context);
                            for(int i=0,size=jsonUsuarios.length();i<size;i++){
                                JSONObject u=jsonUsuarios.getJSONObject(i);
                                int id = u.getInt("contacto_id");
                                int u1=u.getInt("seguidor_id");
                                int u2=u.getInt("seguido_id");
                                Contacto nuevoContacto=new Contacto(id,u1,u2);
                                cDao.insertar(nuevoContacto);
                            }

                            aHome();
                        }


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
                    // error
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_timeout_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_auth),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_server),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_red),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_parse),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();

            Usuario uActual = SharedPrefHelper.getInstance(context).getUser();
            String idU = String.valueOf(uActual.getId());
            params.put("funcion", "contactos");
            params.put("usuario_id", idU);
            return params;
        }
    };
}


