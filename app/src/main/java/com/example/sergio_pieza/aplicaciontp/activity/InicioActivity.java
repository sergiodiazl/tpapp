package com.example.sergio_pieza.aplicaciontp.activity;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v7.app.AppCompatActivity;

import com.example.sergio_pieza.aplicaciontp.R;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      // logeado();
    }



    //por ahora no hace nada
    public void logeado(){
        SharedPreferences sharedPref =this.getPreferences(Context.MODE_PRIVATE);
        String usuario =sharedPref.getString("usuarioActual",null);
        if( usuario !=null){
            Intent home = new Intent(this, HomeActivity.class);
            //DESCOMENTAR SI NECESITA EXTRAS
            //accountsIntent.putExtra("EMAIL",usuario);
            startActivity(home);
        }
        else {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }
    //termina fun logeado
}
