package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */
public class Tag {

    public int id_t;


    public String texto;

    public int getId_t() {
        return id_t;
    }

    public Tag(int id_t, String texto) {
        this.id_t = id_t;
        this.texto = texto;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
