package com.shareghadi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BVN on 12/24/2015.
 */
public class SQLiteDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "sharegadi";

    public  SQLiteDB(Context context){

        super(context, DB_NAME,null,DB_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLiteHelper.CREATE_REGISTRATION_TABLE());

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
