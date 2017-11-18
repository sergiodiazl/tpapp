package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 06/11/2017.
 */




public class Contacto {

    public int id_c;
    public int seguidor_id;
    public int seguido_id;

    public Contacto(int id_c, int seguidor_id, int seguido_id) {
        this.id_c = id_c;
        this.seguidor_id = seguidor_id;
        this.seguido_id = seguido_id;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public int getSeguidor_id() {
        return seguidor_id;
    }

    public void setSeguidor_id(int seguidor_id) {
        this.seguidor_id = seguidor_id;
    }

    public int getSeguido_id() {
        return seguido_id;
    }

    public void setSeguido_id(int seguido_id) {
        this.seguido_id = seguido_id;
    }
}
