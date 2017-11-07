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

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
@TypeConverters(ArrayStringConverter.class)
public interface UsuarioDao {
    @Query("select * from usuario where email = :email and contrasena =:pass")
    Usuario login(String email,String pass);

    @Query("select * from usuario where id = :id")
    Usuario usuarioPorId(int id);

    @Query("select * from usuario where nombre = :nombre ")
    List<Usuario> usuarioPorNombre(String nombre);

    @Delete
    void borrarUsuario(Usuario usuario);

    @Query("delete from usuario where nombre like :nombre")
    int borrarUsuarioPorNombre(String nombre);

    @Query("select * from contactos "+
            "where usuario_id = :id")
    Contactos contactos(int id);

    @Insert(onConflict = IGNORE)
    void insertarUsuario(Usuario usuario);
    @Insert
    void insertarTodosUsuarios(Usuario... usuarios);
    @Query("select * from usuario")
    List<Usuario> cargarTodosUsuarios();

}
