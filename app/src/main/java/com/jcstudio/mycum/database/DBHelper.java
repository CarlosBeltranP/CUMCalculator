package com.jcstudio.mycum.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {


    String path;

    public static final String TABLE_NAME_CGRADE = "TABLE_GRADE";
    public static final String UID_COURSE = "_id";
    public static final String COLUMN_CGRADE_COURSE_NAME = "COURSE_NAME";
    public static final String COLUMN_CGRADE_COURSE_UV = "COURSE_UV";
    public static final String COLUMN_CGRADE_COURSE_GRADE = "COURSE_GRADE";
    public static final String COLUMN_CGRADE_CYCLE_ID = "CYCLE_ID";
    private static final String TABLE_CREATE_CGRADE = "CREATE TABLE "+TABLE_NAME_CGRADE+" ("
            +UID_COURSE +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_CGRADE_COURSE_NAME+" VARCHAR(255), "
            +COLUMN_CGRADE_COURSE_UV+" REAL NOT NULL, "
            +COLUMN_CGRADE_COURSE_GRADE+" REAL NOT NULL, "
            +COLUMN_CGRADE_CYCLE_ID+" INTEGER NOT NULL "
            +");";
    private static final String DROP_TABLE_CGRADE = "DROP TABLE IF EXISTS "+TABLE_NAME_CGRADE;

    public static final String TABLE_NAME_CYCLE = "TABLE_CYCLE";
    public static final String UID_CYCLE = "_id";
    public static final String COLUMN_CYCLE_NAME = "CYCLE_NAME";
    public static final String COLUMN_CYCLE_GRADE = "CYCLE_GRADE";
    public static final String COLUMN_CYCLE_TOTAL_COURSE = "CYCLE_TOTAL_COURSE";
    public static final String COLUMN_CYCLE_TOTAL_UV = "CYCLE_TOTAL_UV";
    private static final String TABLE_CREATE_CYCLE = "CREATE TABLE "+TABLE_NAME_CYCLE+" ("
            +UID_CYCLE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_CYCLE_NAME+" VARCHAR(255), "
            +COLUMN_CYCLE_GRADE+" REAL NOT NULL, "
            +COLUMN_CYCLE_TOTAL_COURSE+" INTEGER NOT NULL, "
            +COLUMN_CYCLE_TOTAL_UV+" REAL NOT NULL "
            +");";
    private static final String DROP_TABLE_CYCLE = "DROP TABLE IF EXISTS "+TABLE_NAME_CYCLE;


    private static final String DATABASE_NAME = "myCumCalculation.db";
    private static final int DATA_BASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(TABLE_CREATE_CYCLE);
            sqLiteDatabase.execSQL(TABLE_CREATE_CGRADE);
            Log.d("Anik", "Database Created");
        } catch (SQLException e) {
            Log.d("Anik", ""+e);
            e.printStackTrace();
        }
        path = sqLiteDatabase.getPath();
        Log.d("Anik", path);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_CYCLE);
        sqLiteDatabase.execSQL(DROP_TABLE_CGRADE);

        sqLiteDatabase.execSQL(TABLE_CREATE_CYCLE);
        sqLiteDatabase.execSQL(TABLE_CREATE_CGRADE);
    }

}
