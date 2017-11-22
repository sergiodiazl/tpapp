package com.example.sergio_pieza.aplicaciontp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sergio_pieza.aplicaciontp.helper.DbHelper;

/**
 * Created by sergio-pieza on 15/11/2017.
 */

public class UsuarioDao {
    protected SQLiteDatabase database;
    private DbHelper dbHelper;
    private Context mContext;
    public final String idIgual="usuario_id=?";
    public UsuarioDao(Context context){
        this.mContext = context;
        dbHelper = DbHelper.getInstance(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DbHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public long insertar(Usuario usuario){
        ContentValues values=new ContentValues();
        values.put("usuario_id",usuario.getId());
        values.put("email",usuario.getEmail());
        values.put("nombre",usuario.getNombre());
        values.put("contrasena",usuario.getContrasena());
        values.put ("zona_id",usuario.getZona_id());
        return database.insert("usuario",null,values);

    }
    public long actualizar(Usuario usuario){
        ContentValues values=new ContentValues();
        values.put("usuario_id",usuario.getId());
        values.put("nombre",usuario.getNombre());
        values.put("email",usuario.getEmail());
        values.put("contrasena",usuario.getContrasena());
        values.put ("zona_id",usuario.getZona_id());
        long result =database.update("usuario",values,idIgual,new String[] { String.valueOf(usuario.getId()) });
        return result;
    }
    public String nombrePorId(int id){
        String query="SELECT nombre FROM usuario WHERE usuario_id=?";
        Cursor cursor=database.rawQuery(query,new String[] { String.valueOf(id) });
        cursor.moveToFirst();
        String nombre=cursor.getString(0);
        return nombre;
    }
    }

