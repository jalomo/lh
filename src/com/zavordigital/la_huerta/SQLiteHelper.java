package com.zavordigital.la_huerta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	
	 
    //Sentencia SQL para crear la tabla de Clientes
    String sqlCreate = "CREATE TABLE favoritos ("+
										 " id INTEGER,"+
										 " receta_text TEXT ,"+
										 " url_ TEXT ,"+
										 " receta_nombre TEXT ,"+
										 " tiempo TEXT,"+
										 " votos TEXT ,"+
										 " recetaid TEXT )";
    String sqlCreateI = "CREATE TABLE ingredientes ("+
			 " id INTEGER,"+
			 " texto TEXT )";
    
    
    
   
 
    public SQLiteHelper(Context contexto, String nombre,
                               CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreateI);
        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, 
                          int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente 
        //      la opci�n de eliminar la tabla anterior y crearla de nuevo 
        //      vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la 
        //      tabla antigua a la nueva, por lo que este m�todo deber�a 
        //      ser m�s elaborado.
 
        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS cat_productos");
 
        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }
}
