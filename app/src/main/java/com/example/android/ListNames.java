package com.example.android;

import android.provider.BaseColumns;

/**
 * Created by dozie on 2017-05-23.
 */

public class ListNames {
    public static final String DB_NAME = "com.example.android";
    public static final int DB_VERSION = 1;

    public class ListEntry implements BaseColumns {
        public static final String TABLE = "lists";
        public static final String COL_TASK_TITLE = "title";

    }

}
