package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */




public class Zona {
    public int id_z;


    public String nombre_z;

    public int getId_z() {
        return id_z;
    }

    public Zona(int id_z, String nombre_z) {
        this.id_z = id_z;
        this.nombre_z = nombre_z;
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
