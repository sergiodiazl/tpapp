package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class PerfilF extends Fragment implements View.OnClickListener {
    public EditText nombreNuevo,contrasenaVieja,contrasenaNueva1,contrasenaNueva2;
    public Button botonN,botonC;
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

        botonN.setOnClickListener(this);
        botonC.setOnClickListener(this);
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
            cerrarSesion();
            return true;
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
        switch (v.getId()){
            case R.id.botonNombre:
                Log.d("Tocaste cambiar nombre","");
                break;
            case R.id.botonContrasena:
                Log.d("Tocaste cambiar nombre","");
                break;


        }

    }
}
