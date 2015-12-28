package com.shareghadi.database;

/**
 * Created by BVN on 12/24/2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.shareghadi.models.SignUp;

import java.util.ArrayList;
import java.util.List;

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

    public long insertSignUpDetails(SignUp signUp) {
        long isUpdate = 0;
        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelper.FIRST_NAME, signUp.getFirstName());
        cv.put(SQLiteHelper.LAST_NAME, signUp.getLastName());
        cv.put(SQLiteHelper.EMAIL, signUp.getEmail());
        cv.put(SQLiteHelper.PROFILE_IMAGE_URL, signUp.getProfileImageURL());
        cv.put(SQLiteHelper.COVER_IMAGE_URL, signUp.getCoverImageURL());

        isUpdate = database.insert(SQLiteHelper.TABLE_SIGNUP, null, cv);
       /* isUpdate = database.insertWithOnConflict(SQLiteHelper.TABLE_SIGNUP, null,
                cv, SQLiteDatabase.CONFLICT_IGNORE);*/

        return isUpdate;
    }

    public List<SignUp> getSignUpDetails() {

        List<SignUp> signUpList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SQLiteHelper.TABLE_SIGNUP;
        cursor = database.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            SignUp signUp = new SignUp();
            signUp.setFirstName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.FIRST_NAME)));
            signUp.setLastName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.LAST_NAME)));
            signUp.setEmail(cursor.getString(cursor.getColumnIndex(SQLiteHelper.EMAIL)));
            signUp.setProfileImageURL(cursor.getString(cursor.getColumnIndex(SQLiteHelper.PROFILE_IMAGE_URL)));
            signUp.setCoverImageURL(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COVER_IMAGE_URL)));
            signUpList.add(signUp);
        }
        cursor.close();
        return signUpList;
    }
}
