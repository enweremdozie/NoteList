package com.example.android;

import android.provider.BaseColumns;


public class ListNames {
    public static final String DB_NAME = "com.example.android";
    public static final int DB_VERSION = 1;

    public class ListEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";

    }

}
