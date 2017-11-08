package com.example.sergio_pieza.aplicaciontp.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.sergio_pieza.aplicaciontp.activity.LoginActivity;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

/**
 * Created by sergio-pieza on 08/11/2017.
 */

public class SharedPrefHelper {

    //es un singleton
    //the constants
    private static final String miPref = "miPref";
    private static final String  usuario_id= "usuario_id";
    private static final String usuario_email = "email";
    private static final String  usuario_nombre ="nombre";
    private static final String usuario_contrasena = "contrasena";
    private static final String zona_id = "keyid";

    private static SharedPrefHelper mInstance;
    private static Context mCtx;

    private SharedPrefHelper(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefHelper(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Usuario usuario {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(miPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(usuario_id, usuario.getId());
        editor.putString(usuario_nombre,usuario.getNombre());
        editor.putString(usuario_email, usuario.getEmail());
        editor.putString(usuario_contrasena, usuario.getContrasena());
        editor.putInt(zona_id, usuario.getZona_id());
        editor.apply();
    }

    //verifica si el usuario esta logeado
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(miPref, Context.MODE_PRIVATE);
        return sharedPreferences.getString(usuario_nombre, null) != null;
    }

    //retorna el usuario logeador
    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(miPref, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getInt(usuario_id, 1),
                sharedPreferences.getString(usuario_nombre, null),
                sharedPreferences.getString(usuario_contrasena, null),
                sharedPreferences.getString(usuario_email, null),
                sharedPreferences.getInt(zona_id, 1);
        );
    }

    //tborra la info del usuari de shared preference
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(miPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
