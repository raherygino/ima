package com.gsoft.ima.utils;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.databinding.ObservableField;

public class DateSet implements DatePickerDialog.OnDateSetListener{
    ObservableField<String> value;
    private EditText editTextDate;

    public DateSet(ObservableField<String> mValue, EditText editText) {
        this.value = mValue;
        this.editTextDate = editText;
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        String date = formatted(dayOfMonth)+"-"+formatted(month)+"-"+year;
        value.set(date);
        editTextDate.setText(date);
    }

    private String formatted(int value) {
        String formatted = String.valueOf(value);
        if (value < 10) {
            formatted = "0"+value;
        }
        return formatted;
    }
}
