package org.rmj.guanzongroup.ghostrider.imgcapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.rmj.guanzongroup.ghostrider.imgcapture.database.model.Model_PhotoCapture;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GhostRider";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Model_PhotoCapture.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Model_PhotoCapture.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertPhoto(String usage, String directory, double latitude, double longitude) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Model_PhotoCapture.COLUMN_PHOTO_USAGE, usage);
        values.put(Model_PhotoCapture.COLUMN_DIRECTORY, directory);
        values.put(Model_PhotoCapture.COLUMN_LATITUDE, latitude);
        values.put(Model_PhotoCapture.COLUMN_LONGITUDE, longitude);

        // insert row
        long id = db.insert(Model_PhotoCapture.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        //return id;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Model_PhotoCapture.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

}