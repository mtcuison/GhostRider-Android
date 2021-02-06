package org.rmj.g3appdriver.etc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper {
    public static final String TAG = SQLiteHandler.class.getSimpleName();

    //database version
    public static final int DATABASE_VERSION = 1;

    //database name
    public static  final String DATABASE_NAME = "GGC_ISysDBF";

    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
