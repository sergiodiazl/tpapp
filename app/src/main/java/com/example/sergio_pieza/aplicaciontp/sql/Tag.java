package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
@Entity(indices={@Index(value={"nombre_t"},unique=true)},tableName = "tag")
public class Tag {

    public @PrimaryKey (autoGenerate = true)int id_t;

    @ColumnInfo(name="nombre_t")
    public String nombre_t;

    public int getId_t() {
        return id_t;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getNombre_t() {
        return nombre_t;
    }

    public void setNombre_t(String nombre_t) {
        this.nombre_t = nombre_t;
    }
}
