package com.example.sergio_pieza.aplicaciontp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sergio_pieza.aplicaciontp.helper.DbHelper;

import java.util.ArrayList;

/**
 * Created by sergio-pieza on 15/11/2017.
 */

public class MomentoDao {
    protected SQLiteDatabase database;
    private DbHelper dbHelper;
    private Context mContext;
    public final String seguidorIgual="seguidor_id=?";
    public MomentoDao(Context context){
        this.mContext = context;
        dbHelper = DbHelper.getInstance(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DbHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public long insertar(Momento momento){
        ContentValues values=new ContentValues();
        values.put("momento_id",momento.getId_m());
        values.put("imagen",momento.getImagen());
        values.put("fecha",momento.getFecha());
        values.put("descripcion",momento.getDescripcion());
        values.put ("latitud",momento.getLatitud());
        values.put ("longitud",momento.getLongitud());
        values.put("momento_zona_id",momento.getZona_id());
        values.put("usuario_id", momento.getUsuario_Id());
        return database.insert("momento",null,values);

    }
    public ArrayList<Momento>momentosContactos(int id){

        ArrayList<Momento>momentos=new ArrayList<Momento>();
        String query ="SELECT momento_id,imagen,fecha,descripcion,latitud,longitud," +
                "momento_zona_id,usuario_id FROM `momento` " +
                "INNER JOIN contacto ON momento.usuario_id=contacto.seguido_id WHERE contacto.seguidor_id= ?";
        Cursor cursor=database.rawQuery(query,new String[] { String.valueOf(id) });
        while (cursor.moveToNext()){
            int p1=cursor.getInt(0);
            String p2=cursor.getString(1);
            String p3=cursor.getString(2);
            String p4=cursor.getString(3);
            Double p5=cursor.getDouble(4);
            Double p6=cursor.getDouble(5);
            int p7 =cursor.getInt(6);
            int p8 =cursor.getInt(7);
            Momento momento=new Momento(p1,p2,p3,p4,p5,p6,p7,p8);
            momentos.add(momento);
        }
        return momentos;
    }
}
