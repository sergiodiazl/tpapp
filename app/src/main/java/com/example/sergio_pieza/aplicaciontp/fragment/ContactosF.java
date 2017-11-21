package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.activity.BuscarUsuarioActivity;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class ContactosF extends Fragment implements View.OnClickListener {
    EditText campoBuscarUsuario;
    Button  botonBuscarUsuarios;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contactos_placeholder, new ListaContactoF());
        ft.commit();
        View vista= inflater.inflate(R.layout.contactos_f, container, false);
        campoBuscarUsuario=(EditText)vista.findViewById(R.id.campoBuscarUsuario);
        botonBuscarUsuarios=(Button)vista.findViewById(R.id.botonBuscarUsuarios);
        botonBuscarUsuarios.setOnClickListener(this);
        return vista;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.contactos, menu);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int boton =item.getItemId();
        if (boton ==R.id.salir){
            cerrarSesion();
            return true;
        }
        if(boton==R.id.buscarContacto){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Queres cerrar sesión");
        alertDialogBuilder.setPositiveButton("si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPrefHelper.getInstance(getActivity()).logout();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

    switch(v.getId()){
        case R.id.botonBuscarUsuarios:
            final String nombre=campoBuscarUsuario.getText().toString().trim();
            if(TextUtils.isEmpty(nombre)){
                campoBuscarUsuario.setError("escriba algo");
                campoBuscarUsuario.requestFocus();
            }else{
                Log.d("nombre",nombre);
                buscarUsuarios(nombre);
            }

            break;
    }
    }
    public void buscarUsuarios(String nUsuario){
        Intent iBuscar=new Intent(getActivity(), BuscarUsuarioActivity.class);
        Bundle args=new Bundle();
        args.putString("nombre",nUsuario);
        iBuscar.putExtras(args);
        startActivity(iBuscar);

    }
}
