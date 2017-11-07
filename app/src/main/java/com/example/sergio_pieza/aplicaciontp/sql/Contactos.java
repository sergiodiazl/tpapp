package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;
@Entity(indices={@Index(value={"usuario_id"},unique=true)},
        foreignKeys = { @ForeignKey(entity = Usuario.class,
                parentColumns = "id",
                childColumns = "usuario_id")
        },tableName = "contactos"
)
public class Contactos {

    public @PrimaryKey (autoGenerate = true)int id_c;
    @Ignore
    public List<Usuario> lista;
    @ColumnInfo(name="usuario_id")
    public int usuario_id;
    public int getId(){
        return id_c;
    }
    public void setId(int id){
        this.id_c =id;
    }
    public List<Usuario>getLista(){
        return lista;
    }
    public void setLista(List<Usuario> lista){
        this.lista=lista;
    }
    public int getUsuarioId(){
        return usuario_id;
    }
    public void setUsuarioId(int id){
        this.id_c =usuario_id;
    }
}
