package com.example.sergio_pieza.aplicaciontp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sergio_pieza.aplicaciontp.helper.DbHelper;

/**
 * Created by sergio-pieza on 18/11/2017.
 */

public class ZonaDao { protected SQLiteDatabase database;
    private DbHelper dbHelper;
    private Context mContext;
    public final String idIgual="usuario_id=?";
    public ZonaDao(Context context){
        this.mContext = context;
        dbHelper = DbHelper.getInstance(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DbHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public long insertar(Zona zona){
        ContentValues values=new ContentValues();
        values.put("zona_id",zona.getId_z());
        values.put("nombre",zona.getNombre_z());

        return database.insert("zona",null,values);

    }
    public String nombrePorId(int id){
        String query="SELECT nombre FROM zona WHERE zona_id=?";
        Cursor cursor=database.rawQuery(query,new String[] { String.valueOf(id) });
        cursor.moveToFirst();
        String nombre=cursor.getString(0);
        return nombre;
    }
}