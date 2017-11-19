package com.example.sergio_pieza.aplicaciontp.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sergio-pieza on 05/11/2017.
 */

public class Momento  implements Serializable{
    public int id_m;
    public String descripcion;
    public String fecha; //cambiartipo a date
    public String imagen;//ruta de imagen
    public double latitud;
    public double longitud;
    public int usuario_Id;

    public int zona_id;
    private static final long serialVersionUID = 8799656478674716638L;
    //!cambiar a date! //
    public Momento(int id_m,  String imagen,  String  fecha,String descripcion,double latitud, double longitud, int usuario_Id, int zona_id) {
        this.id_m = id_m;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.usuario_Id = usuario_Id;
        this.zona_id = zona_id;
    }
    public static ArrayList<Momento> ejemplo(int crear){
        ArrayList<Momento> ejemplo=new ArrayList<Momento>();
        for(int i =1;i<=crear;i++){
          //  ejemplo.add(new Momento(2,"esto es un ejemplo","2017/11/10","https://pbs.twimg.com/media/C6KGhKtWcAAM8vk.jpg"	,-34.775654710652965,-58.26765665433351,1,2));

        }
            return ejemplo;
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



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getUsuario_Id() {
        return usuario_Id;
    }

    public String getFecha() {
        return fecha;
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
