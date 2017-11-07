package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 05/11/2017.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(indices={@Index(value={"momento_id"}),@Index(value={"tag_id"})},foreignKeys = {
        @ForeignKey(entity =Momento.class,
                parentColumns = "id_m",
                childColumns = "momento_id"),

        @ForeignKey(entity = Tag.class,
                parentColumns = "id_t",
                childColumns = "tag_id")},tableName = "momentotag")
public class MomentoTag {
    public @PrimaryKey (autoGenerate = true) int id_mt;
    @ColumnInfo(name="momento_id")
    public String momento_id;
    @ColumnInfo(name="tag_id")
    public String tag_Id;

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
