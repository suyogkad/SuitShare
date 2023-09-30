package com.example.suitshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FULLNAME";
    private static final String COL_3 = "USERNAME";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "PHONE";
    private static final String COL_6 = "DOB";
    private static final String COL_7 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT, USERNAME TEXT UNIQUE, EMAIL TEXT, PHONE TEXT, DOB TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String fullname, String username, String email, String phone, String dob, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fullname);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phone);
        contentValues.put(COL_6, dob);
        contentValues.put(COL_7, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;  // if result == -1 data not inserted
    }

    public Cursor checkUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?", new String[]{email, password});
        return res;
    }

    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=?", new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
