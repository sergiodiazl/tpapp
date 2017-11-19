package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.activity.SubirMomentoActivity;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class HomeF extends Fragment implements ListaMomentoF.OnMomentoSelectedListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);//indica q tiene menu
        FloatingActionButton subir;
        View view= inflater.inflate(R.layout.home_f, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.home_placeholder, new ListaMomentoF());
        ft.commit();


        subir=(FloatingActionButton)view.findViewById(R.id.botonASubirMomento);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSubirMomento();
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.home, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int boton =item.getItemId();
        if (boton ==R.id.salir){
            cerrarSesion();
            return true;
        }
        if(boton==R.id.buscar){
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

    public void aSubirMomento(){

        startActivity(new Intent(this.getActivity(),SubirMomentoActivity.class));
        return;
    }

    @Override
    public void onItemSelected(Momento m) {

    }
}

