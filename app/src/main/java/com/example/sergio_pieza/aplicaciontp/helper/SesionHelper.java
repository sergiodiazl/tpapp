package com.example.sergio_pieza.aplicaciontp.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.sergio_pieza.aplicaciontp.R;

/**
 * Created by sergio-pieza on 12/12/2017.
 */

public class SesionHelper {
    public static void cerrarSesion(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        String cerrar =context.getString(R.string.cerrarSesion);
        String si =context.getString(R.string.si);
        String no =context.getString(R.string.no);

        alertDialogBuilder.setMessage(cerrar);
        alertDialogBuilder.setPositiveButton(si,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPrefHelper.getInstance(context).logout();
                    }
                });

        alertDialogBuilder.setNegativeButton(no,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}

