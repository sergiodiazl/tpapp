package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */

public class MomentoTag {
    public  int id_mt;
    public String momento_id;
    public String tag_Id;

    public MomentoTag(int id_mt, String momento_id, String tag_Id) {
        this.id_mt = id_mt;
        this.momento_id = momento_id;
        this.tag_Id = tag_Id;
    }

    public int getId_mt() {
        return id_mt;
    }

    public void setId_mt(int id_mt) {
        this.id_mt = id_mt;
    }

    public String getMomento_id() {
        return momento_id;
    }

    public void setMomento_id(String momento_id) {
        this.momento_id = momento_id;
    }

    public String getTag_Id() {
        return tag_Id;
    }

    public void setTag_Id(String tag_Id) {
        this.tag_Id = tag_Id;
    }
}
