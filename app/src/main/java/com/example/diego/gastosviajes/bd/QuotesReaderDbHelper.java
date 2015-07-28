package com.example.diego.gastosviajes.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diego on 25/06/15.
 */
public class QuotesReaderDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Viaje";
    public static final int DATABASE_VERSION = 2;




    public QuotesReaderDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear la tabla Quotes
        db.execSQL(QuotesDataSource.CREATE_TABLA_GASTOS_FIJOS);
        //Insertar registros iniciales
//        db.execSQL(QuotesDataSource.INSERT_QUOTES_SCRIPT);

        /*  Nota: Usamos execSQL() ya que las sentencias son
            para uso interno y no están relacionadas con entradas
            proporcionadas por los usuarios
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*  Añade los cambios que se realizarán en el esquema
                en tu proxima versión
             */

    }

}
