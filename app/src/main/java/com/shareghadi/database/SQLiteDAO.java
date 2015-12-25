package com.shareghadi.database;

/**
 * Created by BVN on 12/24/2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDAO {

    private static SQLiteDatabase database;
    private final Context mContext;
    private SQLiteDB sqliteDB;
    Cursor cursor;

    public SQLiteDAO(Context context) {
        mContext = context;
        if (sqliteDB == null) {
            sqliteDB = new SQLiteDB(context);
        }

    }

    public void open() throws SQLException {
        if (database == null ||!database.isOpen()) {
            database = sqliteDB.getWritableDatabase();
        }
    }

    public void close() {
        sqliteDB.close();
    }

   /* public long insertRegistrationDetails(Registration registration) {

        long isUpdate = 0;
        ContentValues cv = new ContentValues();
        cv.put(SQliteHelper.FIRST_NAME, registration.getFirstName());
        cv.put(SQliteHelper.LAST_NAME, registration.getLastName());
        cv.put(SQliteHelper.EMAIL, registration.getEmail());
        cv.put(SQliteHelper.PASSWORD, registration.getPassword());
        cv.put(SQliteHelper.CONFIRM_PASSWORD, registration.getConfirmPassword());


        isUpdate = database.insert(SQliteHelper.TABLE_REGISTRATION, null, cv);

        return isUpdate;
    }*/

  /*  public String login(String email){

        Cursor cursor = database.query(SQLiteHelper.TABLE_REGISTRATION, null,SQLiteHelper.EMAIL+"=?",
                new String[] { email }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(SQLiteHelper.PASSWORD));
        cursor.close();
        return password;

    }*/
}
