package com.example.sergio_pieza.aplicaciontp.sql;

import java.util.Date;

/**
 * Created by sergio-pieza on 05/11/2017.
 */

public class Momento {
    public int id_m;
    public String descripcion;
    public Date fecha;
    public String imagen;//ruta de imagen
    public float latitud;
    public float longitud;
    public int usuario_Id;

    public int zona_id;

    public Momento(int id_m, String descripcion, Date fecha, String imagen, float latitud, float longitud, int usuario_Id, int zona_id) {
        this.id_m = id_m;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.usuario_Id = usuario_Id;
        this.zona_id = zona_id;
    }

    public int getId_m() {
        return id_m;
    }

    public void setId_m(int id_m) {
        this.id_m = id_m;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
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
}
