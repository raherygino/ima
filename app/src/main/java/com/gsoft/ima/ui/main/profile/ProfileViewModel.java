package com.gsoft.ima.ui.main.profile;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;

public class ProfileViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    public User user;

    public ProfileViewModel(Context context) {
        this.context = context;
        this.user = new DatabaseHelper(context).User();
    }

}
