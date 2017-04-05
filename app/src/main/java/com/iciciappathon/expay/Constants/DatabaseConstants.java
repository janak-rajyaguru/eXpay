package com.iciciappathon.expay.Constants;

/**
 * Created by HeeRain on 4/5/2017.
 */

public class DatabaseConstants {

    public static class GroupTable {
        public static final String TABLE_GROUP = "GROUP_TABLE";
        public static final String GROUP_ID = "GROUP_ID";
        public static final String GROUP_NAME = "GROUP_NAME";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_GROUP + "(" + GROUP_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," + GROUP_NAME + " TEXT " + ")";

        public static final String SELECT_ALL = " SELECT * FROM " + TABLE_GROUP;
    }
}
