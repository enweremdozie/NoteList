package com.example.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dozie on 2017-05-28.
 */

public class CoordinateAssist extends SQLiteOpenHelper {

    public CoordinateAssist(Context context){
        super(context, Coordinates.DB_NAME, null, Coordinates.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + Coordinates.CoordEntry.TABLE + " ( " +
                Coordinates.CoordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Coordinates.CoordEntry.COL_COORD + " TEXT NOT NULL, " +
                Coordinates.CoordEntry.COL_VIEWID + " INTEGER);";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Coordinates.CoordEntry.TABLE);
        onCreate(sqLiteDatabase);
    }
}
