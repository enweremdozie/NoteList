package com.example.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ListNamesHelper extends SQLiteOpenHelper {

    public ListNamesHelper(Context context) {
        super(context, ListNames.DB_NAME, null, ListNames.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + ListNames.ListEntry.TABLE + " ( " +
                ListNames.ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListNames.ListEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Task.TaskEntry.TABLE);
        onCreate(sqLiteDatabase);
    }
}
