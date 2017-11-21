package com.example.sergio_pieza.aplicaciontp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.adapter.ResultadoBusquedaUsuarioAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.BuscarUsuariosF;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import java.util.ArrayList;

/**
 * Created by sergio-pieza on 20/11/2017.
 */

public class BuscarUsuarioActivity   extends AppCompatActivity {

String nombreUsuario;

    ArrayList<Usuario> resultados=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_contactos);
        Intent iBuscar=getIntent();
        nombreUsuario=iBuscar.getStringExtra("nombre");
                Log.d("nombre",nombreUsuario);
                BuscarUsuariosF buf=new BuscarUsuariosF();
                Bundle args=new Bundle();
                args.putString("nombreUsuario",nombreUsuario);
                buf.setArguments(args);
                FragmentManager fm =getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contactos_placeholder,buf );
                ft.commit();

            }
        ;


    }








