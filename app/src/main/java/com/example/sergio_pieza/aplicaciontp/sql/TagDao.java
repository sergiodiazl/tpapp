package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
@TypeConverters(DateConverter.class)
public interface TagDao {
    @Insert(onConflict = IGNORE)
    void insertarTag(Tag tag);

    @Update(onConflict = REPLACE)
    void updateTag(Tag tag);
    @Query("SELECT * FROM tag WHERE id_t = :id ")
    public Tag tagPorId(int id);
    @Query("SELECT * FROM tag WHERE nombre_t = :nombre ")
    public Tag tagPorNombre(String nombre);


}
