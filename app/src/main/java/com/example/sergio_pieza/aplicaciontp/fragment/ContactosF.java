package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class ContactosF extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contactos_placeholder, new ListaContactoF());
        ft.commit();
        return inflater.inflate(R.layout.contactos_f, container, false);
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


}
