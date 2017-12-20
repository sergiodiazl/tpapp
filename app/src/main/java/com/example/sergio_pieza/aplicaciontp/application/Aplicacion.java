package com.example.sergio_pieza.aplicaciontp.application;

import android.app.Application;

import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

/**
 * Created by sergio-pieza on 14/12/2017.
 */

public class Aplicacion extends Application {
    @Override
    public void onCreate() {
        SharedPrefHelper.getInstance(this).cargarLocale();
        super.onCreate();
    }
}
