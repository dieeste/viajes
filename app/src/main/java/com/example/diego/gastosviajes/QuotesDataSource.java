package com.example.diego.gastosviajes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by diego on 25/06/15.
 */
public class QuotesDataSource {

    //Metainformación de la base de datos
    public static final String TABLA_GASTOS_FIJOS = "Gastos_fijos";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "double";

    //Campos de la tabla Quotes
    public static class ColumnQuotes{
        public static final String ID_GASTO = BaseColumns._ID;
        public static final String CONCEPTO_GASTO = "concepto";
        public static final String CANTIDAD_GASTO = "cantidad";
        public static final String FECHA_GASTO = "fecha";
        public static final String CATEGORIA_GASTO= "categoria";
    }

    //Script de Creación de la tabla Quotes
    public static final String CREATE_TABLA_GASTOS_FIJOS =
            "create table "+TABLA_GASTOS_FIJOS+"(" +
                    ColumnQuotes.ID_GASTO+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.CONCEPTO_GASTO+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.CANTIDAD_GASTO+" "+REAL_TYPE+" not null,"+
                    ColumnQuotes.FECHA_GASTO+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.CATEGORIA_GASTO+" "+STRING_TYPE+" not null)";


    //Variables para manipulación de datos
    private QuotesReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public QuotesDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new QuotesReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String concepto, Double cantidad, String fecha, String categoria){
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(ColumnQuotes.CONCEPTO_GASTO,concepto);
        values.put(ColumnQuotes.CANTIDAD_GASTO,cantidad);
        values.put(ColumnQuotes.FECHA_GASTO,fecha);
        values.put(ColumnQuotes.CATEGORIA_GASTO,categoria);

        return values;
    }
    public void insertar(String concepto, Double cantidad, String fecha, String categoria){

        //Insertando en la base de datos
        database.insert(TABLA_GASTOS_FIJOS, null, generarContentValues(concepto,cantidad,fecha,categoria));
    }

//    public void eliminar (String titulo){
//        database.delete(TABLA_LIBROS, ColumnQuotes.TITULO_LIBRO + "=?", new String[]{titulo});
//    }

//    public void modificarTitulo(String titulo, String autor){
//
//        database.update(QUOTES_TABLE_NAME,generarContentValues(titulo,autor),)
//    }

    public Cursor cargarCursorLibros(){

        String[] columnas = new String[] {ColumnQuotes.ID_GASTO,ColumnQuotes.CONCEPTO_GASTO,ColumnQuotes.CANTIDAD_GASTO,ColumnQuotes.FECHA_GASTO,ColumnQuotes.CATEGORIA_GASTO};

       return database.query(TABLA_GASTOS_FIJOS,columnas,null,null,null,null,null);
    }
//    public Cursor getAllQuotes(){
//        //Seleccionamos todas las filas de la tabla Quotes
//        return database.rawQuery(
//                "select * from " + TABLA_LIBROS, null);
//    }

    public Cursor cargarGastos(String categoria){

        String[] columnas = new String[] {ColumnQuotes.ID_GASTO,ColumnQuotes.CONCEPTO_GASTO,ColumnQuotes.CANTIDAD_GASTO,ColumnQuotes.FECHA_GASTO,ColumnQuotes.CATEGORIA_GASTO};
        return database.query(TABLA_GASTOS_FIJOS,columnas,ColumnQuotes.CATEGORIA_GASTO+ "=?",new String[]{categoria},null,null,null);
    }

    public Cursor suma(String categoria){
        String[] columnas = new String[] {"sum("+ColumnQuotes.CANTIDAD_GASTO+")"};
//        return database.rawQuery("SELECT Sum(cantidad) AS total FROM " + TABLA_GASTOS_FIJOS+" WHERE categoria= "+categoria,null);
        return database.query(TABLA_GASTOS_FIJOS, columnas, ColumnQuotes.CATEGORIA_GASTO + "=?", new String[]{categoria},null,null,null);
    }
}
