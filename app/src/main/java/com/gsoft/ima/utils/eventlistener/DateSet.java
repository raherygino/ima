package com.gsoft.ima.utils.eventlistener;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.databinding.ObservableField;

public class DateSet implements DatePickerDialog.OnDateSetListener{
    ObservableField<String> value;

    public DateSet(ObservableField<String> mValue) {
        this.value = mValue;
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        String date = formatted(dayOfMonth)+"-"+formatted(month)+"-"+year;
        value.set(date);
    }

    private String formatted(int value) {
        String formatted = String.valueOf(value);
        if (value < 10) {
            formatted = "0"+value;
        }
        return formatted;
    }
}
