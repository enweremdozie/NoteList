package com.example.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListHelper extends SQLiteOpenHelper{

    public ListHelper(Context context){
    super(context, Task.DB_NAME, null, Task.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + Task.TaskEntry.TABLE + " ( " +
                                               Task.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                               Task.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                                               Task.TaskEntry.LIST_POSITION + " INTEGER));";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Task.TaskEntry.TABLE);
        onCreate(sqLiteDatabase);
    }
}
