package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 07/11/2017.
 */
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.List;

public class IniciarDb {

    private static final String TAG =IniciarDb.class.getName();

    public static void llenarAsync (@NonNull final Db db){
        LlenarDbAsync tarea =new LlenarDbAsync(db);
        tarea.execute();
    }
    public static void llenarSync(@NonNull final Db db){
        llenarEjemplo(db);
    }
    public static Zona agregarZona( final Db db,Zona zona){
    db.zonaDao().insertarTodo();
    return zona;
    }
    private static void llenarEjemplo(Db db){
        Zona zona =new Zona();
        zona.setNombre_z("zona sur");
        agregarZona(db,zona);
        List<Zona> zonas = db.zonaDao().todas();
        Log.d(IniciarDb.TAG,"filas:"+zonas.size());

    }

    private static class LlenarDbAsync extends AsyncTask<Void,Void,Void>{
        private final Db mDb;
        LlenarDbAsync(Db db){
            mDb=db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            llenarEjemplo(mDb);
            return null;
        }
    }
}
