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

    public abstract UsuarioDao usuarioModel();
    public abstract MomentoDao momentoModel();
    public abstract MomentoTagDao momentoTagModel();
    public abstract TagDao tagModel();
    public abstract ZonaDao zonaModel();

}
