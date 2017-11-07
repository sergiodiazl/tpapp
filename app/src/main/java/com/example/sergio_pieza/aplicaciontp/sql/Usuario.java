package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;



@Entity(indices={@Index(value={"nombre"},unique=true),@Index(value={"email"},unique=true),@Index(value={"contactos_id"}),@Index(value={"zona_id"})},
        foreignKeys = { @ForeignKey(entity = Zona.class,
        parentColumns = "id_z",
        childColumns = "zona_id"),
        @ForeignKey(entity = Contactos.class, parentColumns = "id_c", childColumns = "contactos_id"),
        },tableName = "usuario"
)
@TypeConverters(ArrayStringConverter.class)
public class Usuario {
    public @PrimaryKey (autoGenerate = true)int id;

    @ColumnInfo(name="nombre")
    public String nombre;
    @ColumnInfo(name="contrasena")
    public String contrasena;
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name = "zona_id")
    public int zona_id;
    @ColumnInfo(name = "contactos_id")
    public int contactos_id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getZona_id() {
        return zona_id;
    }

    public void setZona_id(int zona_id) {
        this.zona_id = zona_id;
    }

    public int getContactos_id() {
        return contactos_id;
    }

    public void setContactos_id(int contactos_id) {
        this.contactos_id = contactos_id;
    }
}
