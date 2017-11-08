package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.content.Context;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Usuario.class, Momento.class, MomentoTag.class,Zona.class,Tag.class,Contactos.class}, version = 1)
public abstract class Db extends RoomDatabase {
    private static Db INSTANCE;

    public abstract UsuarioDao usuarioDao();
    public abstract MomentoDao momentoDao();
    public abstract MomentoTagDao momentoTagDao();
    public abstract TagDao tagDao();
    public abstract ZonaDao zonaDao();
    public static Db getDb (Context context){
        if( INSTANCE==null){
            Room.databaseBuilder(context.getApplicationContext(),Db.class,"db").build();

        }
        return INSTANCE;
    }

}
