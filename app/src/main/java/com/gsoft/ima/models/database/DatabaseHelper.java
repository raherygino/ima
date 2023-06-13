package com.gsoft.ima.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gsoft.ima.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "ima_db.db";
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_NUMBER = "number";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE "+ TABLE_USER +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
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
        return db.insert(TABLE_USER, null, values);
    }
}
