package com.gsoft.ima.utils;

import android.content.Context;

import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;

public class UserLogged {
    public static User data(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.User();
    }
}
