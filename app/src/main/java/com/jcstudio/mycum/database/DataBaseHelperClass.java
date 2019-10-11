package com.jcstudio.mycum.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelperClass extends SQLiteOpenHelper {

    //Ruta del sistema predeterminada de Android de la base de datos de la aplicación.
    private static String DB_PATH = "/data/data/cumdatabase/databases/";
    // Nombre de la BD.
    private static final String DATABASE_NAME = "myCumCalculation.db";
    // Version de la BD
    private static final int DATABASE_VERSION = 1;
    // Nombres de tabla de la BD
    static final String TABLE_Name = "tableName";

    public Context context;
    private static SQLiteDatabase sqliteDataBase;

    /**
     * Constructor
     * Toma y mantiene una referencia del contexto pasado para acceder a los recursos y recursos de la aplicación.
     * @param context
     * Los parametros de super() son    1. Context
     *                                  2. Data Base Name.
     *                                  3. Cursor Factory.
     *                                  4. Data Base Version.
     */
    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
    }

    public void createDataBase() throws IOException {
        //Comprueba si la bd existe
        boolean databaseExist = checkDataBase();

        if(databaseExist){
            // No hacer nada.
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Comprueba si la base de datos ya existe para evitar volver a copiar el archivo cada vez que abra la aplicación.
     * @return true si existe, false si no existe
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copia su base de datos desde su assets-folder local  a la base de datos vacía recién creada en la
     * carpeta del sistema, desde donde se puede acceder y manejar.
     * */
    private void copyDataBase() throws IOException{
        // Abre tu bd local como flujo de entrada
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        // Ruta a la base de datos vacía recién creada
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfiere bytes del archivo de entrada al archivo de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Este metodo abre la conexion de la bd
     * Primero crea la ruta hasta la base de datos del dispositivo.
     * Luego crea la conexion con la bd.
     */
    public void openDataBase() throws SQLException {
        //Abrir la base de datos
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Metodo usado para cerrar la conexion de la bd
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

