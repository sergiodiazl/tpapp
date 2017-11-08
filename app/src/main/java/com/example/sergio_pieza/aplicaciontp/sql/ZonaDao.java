package com.example.sergio_pieza.aplicaciontp.sql;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
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
public interface ZonaDao {

    @Query("SELECT * FROM zona WHERE id_z = :id ")
    public Zona zonaPorId(int id);
    @Query("SELECT * FROM zona ")
    public List<Zona> todas();
    @Query("SELECT * FROM zona WHERE nombre_z = :nombre ")
    public Zona zonaPorNombre(String nombre);

    @Insert(onConflict = IGNORE)
    void insertarZona(Zona zona);
    @Insert(onConflict = IGNORE)
    void insertarTodo(Zona... zonas);

    @Update(onConflict = REPLACE)
    void updateZona(Zona zona);
}
