package com.gsoft.ima.ui.main.profile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.model.models.UserData;
import com.gsoft.ima.utils.DateSet;

import java.util.Calendar;

public class ProfileViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    public User user;
    private UserData userData;

    public ProfileViewModel(Context context) {
        this.context = context;
        this.user = new DatabaseHelper(context).User();
        this.userData = new UserData();
    }

    public void updateData(User userUpdated) {
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.updateUser(userUpdated) != -1) {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            this.user = db.User();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
        }
    }


    public void setChangeBirthDay(UserData userDataNew, EditText editTextDate) {
        String[] date = userDataNew.birthday.get().split("-");
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]) - 1 ;
        int day = Integer.parseInt(date[0]);

        DatePickerDialog dialog = new DatePickerDialog(context, new DateSet(userDataNew.birthday,editTextDate), year, month, day);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void setChooseGender(UserData userDataNew, EditText editTextGender) {
        PopupMenu popupMenu = new PopupMenu(context, editTextGender);
        popupMenu.getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                editTextGender.setText(menuItem.getTitle());
                if (menuItem.getItemId() == R.id.male) {
                    userDataNew.gender.set("male");
                } else {
                    userDataNew.gender.set("female");
                }
                return true;
            }
        });
        popupMenu.show();
    }


}
