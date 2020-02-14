package com.devnovikov.keepapp;

public class Constants {

    // db title
    public static final String DB_NAME = "KEEP_INFO_DB";
    // db version
    public static final int DB_VERSION = 2;
    // db table
    public static final String TABLE_NAME ="KEEP_INFO_TABLE";
    // table columns
    public static final String C_ID = "ID";
    public static final String C_TITLE = "NAME";
    public static final String C_SUBTITLE = "AGE";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_ADD_TIMESTAMP = "ID_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";
    // create query for table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_TITLE + " TEXT,"
            + C_SUBTITLE + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADD_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP +  " TEXT"
            + ");";
}
