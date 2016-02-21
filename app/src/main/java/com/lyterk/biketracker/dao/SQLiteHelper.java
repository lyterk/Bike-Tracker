package com.lyterk.biketracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "dao.sqlitehelper";

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "locations";
    public static final String ID = "_id";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String TIME = "TIME";
    public static final String ACCURACY = "ACCURACY";
    public static final String ALTITUDE = "ALTITUDE";
    public static final String SPEED = "SPEED";
    public static final String REAL = " REAL, ";
    public static final String INTEGER = " INTEGER, ";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + INTEGER +
            LATITUDE + REAL +
            LONGITUDE + REAL +
            TIME + INTEGER +
            ALTITUDE + REAL +
            SPEED + REAL +
            ACCURACY + " REAL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
