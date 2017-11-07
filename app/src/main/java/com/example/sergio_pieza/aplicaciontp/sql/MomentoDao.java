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
public interface MomentoDao {

    @Insert(onConflict = IGNORE)
    void insertarMomento(Momento momento);

    @Update(onConflict = REPLACE)
    void updateMomento(Momento momento);
    @Query("SELECT * FROM momento WHERE usuario_id = :id ORDER BY fecha DESC")
    public LiveData<List<Momento>> momentosDeUsuario(int id);

    @Delete
    void borrarMomento(Momento momento);


    @Query("SELECT * FROM momento "+
            "INNER JOIN momentotag ON momentotag.momento_id LIKE momento.id_m"+
            " WHERE momentotag.tag_id = :tag" +
            " ORDER BY fecha DESC")
    public LiveData<List<Momento>> momentosPorTag(int tag);

    @Query("SELECT * FROM momento WHERE zona_id = :id ORDER BY fecha DESC")
    public LiveData<List<Momento>> momentosDeZona(int id);
}
