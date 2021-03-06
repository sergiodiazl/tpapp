package com.example.sergio_pieza.aplicaciontp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sergio_pieza.aplicaciontp.helper.DbHelper;

import java.util.ArrayList;

/**
 * Created by sergio-pieza on 18/11/2017.
 */

public class ContactoDao{
        protected SQLiteDatabase database;
        private DbHelper dbHelper;
        private Context mContext;
        public final String idIgual="seguidor_id=?";
        public ContactoDao(Context context){
            this.mContext = context;
            dbHelper = DbHelper.getInstance(mContext);
            open();

        }

        public void open() throws SQLException {
            if(dbHelper == null)
                dbHelper = DbHelper.getInstance(mContext);
            database = dbHelper.getWritableDatabase();
        }

        public long insertar(Contacto contacto){
            ContentValues values=new ContentValues();
            values.put("contacto_id",contacto.getId_c());
            values.put("seguidor_id",contacto.getSeguidor_id());
            values.put("seguido_id",contacto.getSeguido_id());
            return database.insert("contacto",null,values);

        }
        public void borrar(int id1,int id2){
            String where=" seguidor_id="+String.valueOf(id1) +" and seguido_id = "+String.valueOf(id2);
            database.delete("contacto",where,null);

            }

public ArrayList<Usuario> contactosUsuario(int id) {
    ArrayList<Usuario> cu = new ArrayList<Usuario>();

    String query ="SELECT usuario_id,nombre,contrasena," +
            "email,zona_id FROM `usuario` " +
            "INNER JOIN contacto ON usuario.usuario_id=contacto.seguido_id WHERE contacto.seguidor_id= ?";
    Cursor cursor=database.rawQuery(query,new String[] { String.valueOf(id) });
    while (cursor.moveToNext()){
        int p1=cursor.getInt(0);
        String p2=cursor.getString(1);//
        String p3=cursor.getString(2);//
        String p4=cursor.getString(3);
        int p5 =cursor.getInt(4);
        Usuario usuario=new Usuario(p1,p2,p3,p4,p5);
        cu.add(usuario);}
        return cu;}
public boolean esContacto(int id1,int id2){
            String query="SELECT contacto_id FROM `contacto` WHERE  seguidor_id=? and seguido_id =?";
            Cursor cursor=database.rawQuery(query,new String[]{String.valueOf(id1),String.valueOf(id2)});
            if(cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
}
}

