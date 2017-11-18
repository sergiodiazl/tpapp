package com.example.sergio_pieza.aplicaciontp.helper;

/**
 * Created by sergio-pieza on 15/11/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHelper extends SQLiteOpenHelper{



    private static final String database ="db";
    private static final int version =1;

    public static  String crearTablaZona="CREATE TABLE `zona` ("+
            "`zona_id`	INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT UNIQUE,"+
           " `nombre`	TEXT NOT NULL)";

///consultas p crear las tablas
    public static  String crearTablaUsuario= "CREATE TABLE `usuario` (" +
            "`usuario_id` INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT UNIQUE," +
            "`nombre` TEXT NOT NULL," +
            " `contrasena` TEXT NOT NULL," +
            " `email` TEXT NOT NULL UNIQUE," +
            "`zona_id` INTEGER NOT NULL," +
            "FOREIGN KEY(`zona_id`) REFERENCES `zona`(`zona_id`)" +
            ");";
    public static  String crearTablaMomento="CREATE TABLE `momento` (" +
            " `momento_id` INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT," +
            " `imagen` TEXT NOT NULL, " +
            " `fecha` TEXT NOT NULL, " +
            " `descripcion` INTEGER, " +
            " `latitud` REAL NOT NULL, " +
            " `longitud` REAL NOT NULL, " +
            " `momento_zona_id` INTEGER NOT NULL, " +
            " `usuario_id` INTEGER NOT NULL," +
            " FOREIGN KEY(`usuario_id`) REFERENCES `usuario`(`usuario_id`)," +
            " FOREIGN KEY(`momento_zona_id`) REFERENCES `zona`(`zona_id`)" +
            ");";
    public static  String crearTablaContacto="CREATE TABLE `contacto` (" +
            "`contacto_id` INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT," +
            " `seguidor_id`INTEGER NOT NULL," +
            " `seguido_id`INTEGER NOT NULL," +
            " FOREIGN KEY(`seguidor_id`) REFERENCES `usuario`(`usuario_id`)" +
            "FOREIGN KEY(`seguido_id`) REFERENCES `usuario`(`usuario_id`));";
    public static  String crearTablaTag="CREATE TABLE `tag` (" +
            " `tag_id` INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT," +
            " `texto` TEXT NOT NULL" +
            ");";
    public static  String crearTablaMomentoTag="CREATE TABLE `momentotag` (" +
            " `momentotag_id`INTEGER NOT NULL PRIMARY KEY ON CONFLICT IGNORE AUTOINCREMENT," +
            " `momento_id` INTEGER NOT NULL," +
            " `tag_id` INTEGER NOT NULL," +
            " FOREIGN KEY(`tag_id`) REFERENCES `tag`(`tag_id`));";

private static DbHelper instance;
public static synchronized  DbHelper getInstance(Context context){
    if(instance==null)instance=new DbHelper(context);
    return instance;
}
private DbHelper(Context context){
    super(context,database,null,version);
}



    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(crearTablaZona);
        db.execSQL(crearTablaUsuario);
        db.execSQL(crearTablaContacto);
        db.execSQL(crearTablaMomento);
        db.execSQL(crearTablaTag);
        db.execSQL(crearTablaMomentoTag);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
