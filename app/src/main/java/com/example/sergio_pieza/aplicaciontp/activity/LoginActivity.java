package com.example.sergio_pieza.aplicaciontp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                        if(obj.getBoolean("error")==true ){
                            String mensaje= obj.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        }else{
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
                        Log.d("Error.Response",error.getMessage());
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

        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);


    }
    private void aRegistrar(){
        finish();
        startActivity(new Intent(this,RegistrarActivity.class));
        return;


    }
    private void aHome(){
        finish();
        startActivity(new Intent(this,HomeActivity.class));
        return;
    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
