package com.gsoft.ima.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gsoft.ima.model.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "ima_db.db";
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_BIRTHPLACE = "birthplace";
    private static final String COLUMN_CIN = "id_card";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE "+ TABLE_USER +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_BIRTHDAY + " TEXT, " +
                COLUMN_BIRTHPLACE + " TEXT, " +
                COLUMN_CIN + " TEXT, " +
                COLUMN_COUNTRY + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                " created_at DATETIME)";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LASTNAME, user.lastname);
        values.put(COLUMN_FIRSTNAME, user.firstname);
        values.put(COLUMN_GENDER, user.gender);
        values.put(COLUMN_BIRTHDAY, user.birthday);
        values.put(COLUMN_BIRTHPLACE, user.birthplace);
        values.put(COLUMN_CIN, user.id_card);
        values.put(COLUMN_COUNTRY, user.country);
        values.put(COLUMN_CITY, user.city);
        values.put(COLUMN_NUMBER, user.phone);
        values.put(COLUMN_EMAIL, user.email);
        values.put(COLUMN_PASSWORD, user.password);
        return db.insert(TABLE_USER, null, values);
    }

    public Cursor selectUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_USER;
        return db.rawQuery(query, null);
    }

    public boolean isLogged() {
        if (selectUser().getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User User() {
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        final User user = new User(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), "");
        return user;
    }

    public double deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "";
        Cursor cursor = selectUser();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getString(0);
        }
        return db.delete(TABLE_USER, COLUMN_ID+" = ?", new String[]{id});
    }
}

