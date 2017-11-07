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

import java.util.Date;

@Entity(indices = {@Index(value={"usuario_id"}),@Index(value={"zona_id"}),@Index(value={"mt_id"})},foreignKeys ={ @ForeignKey(entity = Usuario.class,
        parentColumns = "id",
        childColumns = "usuario_id"),@ForeignKey(entity = Zona.class,
        parentColumns = "id_z",
        childColumns = "zona_id"),@ForeignKey(entity = MomentoTag.class,
        parentColumns = "id_mt",
        childColumns = "mt_id")},tableName = "momento")
@TypeConverters(DateConverter.class)
public class Momento {
    public @PrimaryKey (autoGenerate = true)int id_m;
    public String descripcion_m;
    public Date fecha;
    public String imagen;//ruta de imagen
    public String lugar;//coordenadas json transformado a string

    @ColumnInfo(name = "usuario_id")
    public int usuario_Id;

    @ColumnInfo(name = "zona_id")
    public int zona_id;
    @ColumnInfo(name = "mt_id")
    public int mt_id;

    public int getId_m() {
        return id_m;
    }

    public String getDescripcion_m() {
        return descripcion_m;
    }

    public void setDescripcion_m(String descripcion_m) {
        this.descripcion_m = descripcion_m;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getUsuario_Id() {
        return usuario_Id;
    }

    public void setUsuario_Id(int usuario_Id) {
        this.usuario_Id = usuario_Id;
    }

    public int getZona_id() {
        return zona_id;
    }

    public void setZona_id(int zona_id) {
        this.zona_id = zona_id;
    }

    public void setId_m(int id_m) {
        this.id_m = id_m;
    }
}
