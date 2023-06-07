package com.gsoft.ima.viewmodel;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

public class LoginActivityViewModel extends BaseObservable {

    private Context context;
    public ObservableField<String> number = new ObservableField<>();
    public LoginActivityViewModel(Context mContext) {
        this.context = mContext;
    }
}
