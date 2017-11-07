package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices={@Index(value={"nombre_z"},unique=true)},tableName = "zona"
)
public class Zona {
    public @PrimaryKey (autoGenerate = true)int id_z;

    @ColumnInfo(name="nombre_z")
    public String nombre_z;

    public int getId_z() {
        return id_z;
    }

    public void setId_z(int id_z) {
        this.id_z = id_z;
    }

    public String getNombre_z() {
        return nombre_z;
    }

    public void setNombre_z(String nombre_z) {
        this.nombre_z = nombre_z;
    }
}
