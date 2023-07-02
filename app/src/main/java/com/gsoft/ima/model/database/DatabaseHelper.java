package com.gsoft.ima.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.gsoft.ima.model.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.gsoft.ima.constants.main.FormConstants.BIRTHDAY;
import static com.gsoft.ima.constants.main.FormConstants.BIRTHPLACE;
import static com.gsoft.ima.constants.main.FormConstants.CITY;
import static com.gsoft.ima.constants.main.FormConstants.COUNTRY;
import static com.gsoft.ima.constants.main.FormConstants.EMAIL;
import static com.gsoft.ima.constants.main.FormConstants.FIRSTNAME;
import static com.gsoft.ima.constants.main.FormConstants.GENDER;
import static com.gsoft.ima.constants.main.FormConstants.ID_CARD;
import static com.gsoft.ima.constants.main.FormConstants.LASTNAME;
import static com.gsoft.ima.constants.main.FormConstants.PASSWORD;
import static com.gsoft.ima.constants.main.FormConstants.PHONE;

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

    private static final String TABLE_DIS = "district";
    private static final String COLUMN_DIS_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER = "CREATE TABLE "+ TABLE_USER +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
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

        String SQL_CREATE_DISC = "CREATE TABLE "+ TABLE_DIS +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_DIS_NAME + " TEXT, " +
                " created_at DATETIME)";

        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_DISC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private ContentValues contentValuesUser(User user) {
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
        return values;
    }

    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_USER, null, contentValuesUser(user));
    }

    public long createUserByObject(JSONObject object) {
        long isCreated = -1;
        try {
            User user = new User(
                    object.getString(FIRSTNAME),
                    object.getString(LASTNAME),
                    object.getString(GENDER),
                    object.getString(BIRTHDAY),
                    object.getString(BIRTHPLACE),
                    object.getString(ID_CARD),
                    object.getString(COUNTRY),
                    object.getString(CITY),
                    object.getString(PHONE),
                    object.getString(EMAIL),
                    object.getString(PASSWORD),
                    object.getString(PASSWORD)
            );
            isCreated = this.createUser(user);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return isCreated;
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

    public long updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        return db.update(TABLE_USER, contentValuesUser(user), COLUMN_ID+" = ?", new String[]{cursor.getString(0)});
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

    public long insertDistrict(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIS_NAME, name);
        return db.insert(TABLE_DIS, null, values);
    }

    public Cursor getAllDistrict() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_DIS;
        return db.rawQuery(query, null);
    }

}

