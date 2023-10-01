package com.example.suitshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Users Table
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FULLNAME";
    public static final String COL_3 = "USERNAME";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "PHONE";
    private static final String COL_6 = "DOB";
    private static final String COL_7 = "PASSWORD";
    public static final String COL_8 = "AVATAR";

    // Items Table
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_USER_ID = "user_id";  // This will be linked to COL_1 of the user_table
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    private static final String COLUMN_PURCHASE_STATUS = "purchase_status";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE_ADDED = "date_added";
    private static final String COLUMN_LOCATION_TAG = "location_tag";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT, USERNAME TEXT UNIQUE, EMAIL TEXT UNIQUE, PHONE TEXT, DOB TEXT, PASSWORD TEXT, AVATAR TEXT)");

        // Creating the Items table
        db.execSQL("CREATE TABLE " + TABLE_ITEMS + " ("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_ITEM_NAME + " TEXT, "
                + COLUMN_ITEM_DESCRIPTION + " TEXT, "
                + COLUMN_PURCHASE_STATUS + " INTEGER, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_DATE_ADDED + " TEXT, "
                + COLUMN_LOCATION_TAG + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_NAME + "(" + COL_1 + "))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);  // Dropping the Items table if it exists
        onCreate(db);
    }

    public boolean insertData(String fullname, String username, String email, String phone, String dob, String password, String avatarUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fullname);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phone);
        contentValues.put(COL_6, dob);
        contentValues.put(COL_7, password);
        contentValues.put(COL_8, avatarUri);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?", new String[]{email, password});
//        db.close();
        return res;
    }

    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=?", new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    private String getColumnData(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index != -1) {
            return cursor.getString(index);
        }
        return null;
    }


    public String getUsernameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT USERNAME FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
        String username = null;
        if(cursor.moveToFirst()) {
            username = getColumnData(cursor, COL_3);
        }
        cursor.close();
        db.close();
        return username;
    }

    public String getAvatarUriByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVATAR FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
        String avatarUri = null;
        if(cursor.moveToFirst()) {
            avatarUri = getColumnData(cursor, COL_8);
        }
        cursor.close();
        db.close();
        return avatarUri;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
//        db.close();
        return cursor;
    }
}
