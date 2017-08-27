package com.example.android;

import android.provider.BaseColumns;

/**
 * Created by dozie on 2017-05-28.
 */

public class Coordinates {
    public static final String DB_NAME = "com.example.android";
    public static final int DB_VERSION = 1;

    public class CoordEntry implements BaseColumns {
        public static final String TABLE = "coordinates";
        public static final String COL_COORD = "title";
        public static final String COL_VIEWID = "title";

    }

}
