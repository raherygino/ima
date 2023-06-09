package com.gsoft.ima.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.gsoft.ima.constants.main.TransactionConstants.*;

import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.gsoft.ima.constants.main.FormConstants.*;

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
    private static final String COLUMN_BALANCE = "balance";
    private static final String COLUMN_PENDING_AMOUNT = "pending_amount";
    private static final String COLUMN_PENDING_COUNT = "pending_count";
    private static final String COLUMN_PENDING_TYPE = "pending_type";


    private static final String TABLE_DIS = "district";
    private static final String COLUMN_DIS_NAME = "name";

    private static final String TABLE_TRANSACTION = "table_transaction";
    private static final String COLUMN_NAME_SENDER = "name_sender";
    private static final String COLUMN_NUM_SENDER = "num_sender";
    private static final String COLUMN_NAME_RECEIVER = "name_receiver";
    private static final String COLUMN_NUM_RECEIVER = "num_receiver";
    private static final String COLUMN_METHOD = "method";
    private static final String COLUMN_TOKEN_SENDER = "token_sender";
    private static final String COLUMN_TOKEN_RECEIVER = "token_receiver";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_ID_SERV = "id_transaction";

    private static final String COLUMN_DATE = "created_at";

    private static final String TABLE_JSON = "transaction_json";
    private static final String COLUMN_VALUE = "value";


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
                COLUMN_BALANCE + " INTEGER, " +
                COLUMN_PENDING_AMOUNT + " INTEGER, " +
                COLUMN_PENDING_COUNT + " INTEGER, " +
                COLUMN_PENDING_TYPE + " TEXT, " +
                " created_at DATETIME)";

        String SQL_CREATE_DISC = "CREATE TABLE "+ TABLE_DIS +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_DIS_NAME + " TEXT, " +
                " created_at DATETIME)";

        String SQL_CREATE_TRANS = "CREATE TABLE "+ TABLE_TRANSACTION +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME_SENDER + " TEXT, " +
                COLUMN_NUM_SENDER + " TEXT, " +
                COLUMN_NAME_RECEIVER + " TEXT, " +
                COLUMN_NUM_RECEIVER + " TEXT, " +
                COLUMN_METHOD + " TEXT, " +
                COLUMN_TOKEN_SENDER + " TEXT, " +
                COLUMN_TOKEN_RECEIVER + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_ID_SERV + " INTEGER, " +
                COLUMN_DATE +" DATETIME)";


        String SQL_CREATE_TRANS_JSON = "CREATE TABLE "+ TABLE_JSON +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_VALUE + " TEXT, " +
                " created_at DATETIME)";

        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_DISC);
        db.execSQL(SQL_CREATE_TRANS);
        db.execSQL(SQL_CREATE_TRANS_JSON);
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
        values.put(COLUMN_BALANCE, user.balance);
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
            user.balance = object.getInt(BALANCE);
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
                cursor.getString(8), cursor.getString(9), cursor.getString(10),
                cursor.getString(11), "");
        user.balance = cursor.getInt(12);
        user.pendingAmount = cursor.getInt(13);
        user.pendingCount = cursor.getInt(14);
        user.pendingType = cursor.getString(15);
        return user;
    }

    public long updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        return db.update(TABLE_USER, contentValuesUser(user), COLUMN_ID+" = ?", new String[]{cursor.getString(0)});
    }

    public long updateBalance(int amount, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        int balance = cursor.getInt(12) - amount;

        if (type.equals(ADD)) {
            balance = cursor.getInt(12) + amount;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BALANCE, balance);
        return db.update(TABLE_USER, contentValues, COLUMN_ID+" = ?", new String[]{cursor.getString(0)});
    }

    public double deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "";
        Cursor cursor = selectUser();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getString(0);
        }
        db.execSQL("DELETE FROM "+TABLE_TRANSACTION);
        db.execSQL("DELETE FROM "+TABLE_JSON);
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


    private ContentValues contentValuesTrans(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SENDER, transaction.nameSender);
        values.put(COLUMN_NUM_SENDER, transaction.numSender);
        values.put(COLUMN_NAME_RECEIVER, transaction.nameReceiver);
        values.put(COLUMN_NUM_RECEIVER, transaction.numReceiver);
        values.put(COLUMN_METHOD, transaction.method);
        values.put(COLUMN_TOKEN_SENDER, transaction.tokenSender);
        values.put(COLUMN_TOKEN_RECEIVER, transaction.tokenReceiver);
        values.put(COLUMN_AMOUNT, transaction.amount);
        values.put(COLUMN_STATUS, transaction.status);
        values.put(COLUMN_DATE, transaction.date);
        values.put(COLUMN_ID_SERV, transaction.id);
        return values;
    }

    public long insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TRANSACTION, null, contentValuesTrans(transaction));
    }

    public long addAmountPending(int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        updateBalance(amount, "REMOVE");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PENDING_AMOUNT, amount);
        contentValues.put(COLUMN_PENDING_TYPE, "SENT");
        return db.update(TABLE_USER, contentValues, COLUMN_ID+" = ?", new String[]{cursor.getString(0)});
    }

    public long addCountPending(int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectUser();
        cursor.moveToFirst();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PENDING_COUNT, count);
        return db.update(TABLE_USER, contentValues, COLUMN_ID+" = ?", new String[]{cursor.getString(0)});
    }

    public ArrayList<Transaction> getAllTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Transaction> allTransaction = new ArrayList<Transaction>();
        String query = "SELECT * FROM "+TABLE_TRANSACTION;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction(cursor.getInt(9));
            transaction.nameSender = cursor.getString(1);
            transaction.numSender = cursor.getString(2);
            transaction.nameReceiver = cursor.getString(3);
            transaction.numReceiver = cursor.getString(4);
            transaction.method = cursor.getString(5);
            transaction.tokenSender = cursor.getString(6);
            transaction.tokenReceiver = cursor.getString(7);
            transaction.status = cursor.getString(8);
            transaction.id = cursor.getInt(10);
            transaction.date = cursor.getString(11);
            allTransaction.add(transaction);
        }

        return allTransaction;
    }

    public long insertTransJson(String jsonString) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE, jsonString);
        return db.insert(TABLE_JSON, null, values);
    }

    public boolean checkTransJsonIfExist(String jsonString) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_JSON+" WHERE "+COLUMN_VALUE+ " = '"+jsonString+"'";
        boolean isExist = true;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            isExist = false;
        }
        return isExist;
    }


    public double deleteTransactionNetwork() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRANSACTION, COLUMN_METHOD+" = ?", new String[]{"Network"});
    }
}

