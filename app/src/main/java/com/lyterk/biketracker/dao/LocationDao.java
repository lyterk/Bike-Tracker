package com.lyterk.biketracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocationDao implements LocationListener {

    private static final String TAG = "dao.locationdao";

    private Context ctx;

    private SQLiteDatabase db;
    private SQLiteHelper helper;
    private static final String[] all_columns = {
            SQLiteHelper.ID,
            SQLiteHelper.LATITUDE,
            SQLiteHelper.LONGITUDE,
            SQLiteHelper.TIME,
            SQLiteHelper.SPEED,
            SQLiteHelper.ACCURACY
    };

    public LocationDao(Context ctx) {
        this.ctx = ctx;
    }

    public long insertDatapoint(Location location) throws SQLException {
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelper.LATITUDE, location.getLatitude());
        cv.put(SQLiteHelper.LONGITUDE, location.getLongitude());
        cv.put(SQLiteHelper.TIME, location.getLatitude());
        cv.put(SQLiteHelper.ALTITUDE, location.getAltitude());
        cv.put(SQLiteHelper.SPEED, location.getSpeed());
        cv.put(SQLiteHelper.ACCURACY, location.getAccuracy());
        long insertId = db.insert(SQLiteHelper.TABLE_NAME, null, cv);
        db.endTransaction();
        Log.v(TAG, "" + insertId);
        return insertId;
    }

    public List<FetchedLocation> getDatapoints() { // Note: Eventually, I will pass this parameters
        // so it can return datapoints given date, time or proximity to some location.
        List<FetchedLocation> locations = new ArrayList<>();
        Cursor cursor = db.query(
                SQLiteHelper.TABLE_NAME, all_columns, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FetchedLocation fl = new FetchedLocation(cursor.getLong(0), cursor.getDouble(1),
                    cursor.getDouble(2), cursor.getLong(3), cursor.getDouble(4), cursor.getFloat(5),
                    cursor.getFloat(6));
            locations.add(fl);
            cursor.moveToNext();
            Log.v(TAG, fl.toString());
        }
        return locations;
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.endTransaction();
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            insertDatapoint(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
