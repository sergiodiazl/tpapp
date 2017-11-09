package com.example.sergio_pieza.aplicaciontp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.helper.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity {
    EditText nombre,email,pass,pass2;

    Spinner spinner;
    String zona_id;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Spinner spinner=(Spinner)findViewById(R.id.spinnerRegistrar);
        //crear spinner de oznas y llenarlo
        List<String> zonas=new ArrayList<String>();
        zonas.add("Zona sur");
        zonas.add("Zona norte");
        zonas.add("Zona este");
        zonas.add("Zona oeste");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zonas);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Spinner click listener

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = String.valueOf( parent.getSelectedItemPosition()+1);
                // Notify the selected item text

                zona_id=selectedItemText;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nombre=(EditText)findViewById(R.id.nombreRegistrar);
        email=(EditText)findViewById(R.id.emailRegistrar);
        pass=(EditText)findViewById(R.id.passwordRegistrar);
        pass2=(EditText)findViewById(R.id.password2Registrar);
        findViewById(R.id.botonRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        findViewById(R.id.botonALogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aLogin();
            }
        });







    }
    private void registrarUsuario(){

        final String nombreU = nombre.getText().toString().trim();
        final String emailU = email.getText().toString().trim();
        final String passU = pass.getText().toString().trim();
        final String pass2U = pass2.getText().toString().trim();

        if (TextUtils.isEmpty(nombreU)) {
            nombre.setError("escriba su nombre");
            nombre.requestFocus();
            return;
        }

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
        if (TextUtils.isEmpty(pass2U)) {
            pass2.setError("Repita la contraseña");
            pass2.requestFocus();
            return;
        }
        if (!passU.equals(pass2U) ){
            pass2.setError("Las contraseñas no coinciden");
            pass2.requestFocus();
            return;
        }


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
                            Toast.makeText(getApplicationContext(), obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
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
                params.put("funcion", "registrarUsuario");
                params.put("contrasena", passU);
                params.put("email", emailU);
                params.put("nombre", nombreU);
                params.put("zona_id", zona_id);
                Log.d("parametros",params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);



    }


    private void aLogin(){
        finish();
        startActivity(new Intent(this,LoginActivity.class));
        return;


    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
