package com.shareghadi.database;


/**
 * Created by BVN on 12/24/2015.
 */
public class SQLiteHelper {

    //TABLE
    public static final String TABLE_REGISTRATION = "user";
    //Table columns
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static String CREATE_REGISTRATION_TABLE() {
        String query = "CREATE TABLE " + TABLE_REGISTRATION + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIRST_NAME + " TEXT," +
                LAST_NAME + " TEXT," +
                EMAIL + " TEXT," +
                PASSWORD + " TEXT," +
                ")";

        return query;
    }
}
