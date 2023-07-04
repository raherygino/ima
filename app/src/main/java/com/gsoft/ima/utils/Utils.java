package com.gsoft.ima.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.gsoft.ima.R;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.components.Label;
import com.gsoft.ima.model.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import static com.gsoft.ima.constants.main.MainConstants.*;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import dmax.dialog.SpotsDialog;

public class Utils {

    public static void setColorBarStatus(Context context) {
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(activity.getColor(R.color.grey_50));
                View decor = activity.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static void SpotLoad(Context context) {

        new SpotsDialog.Builder()
                .setContext(context)
                .build()
                .show();
    }

    public static void setColorBarStatusDefault(Context context) {
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(activity.getColor(R.color.blue_500));
                View decor = activity.getWindow().getDecorView();
                decor.setSystemUiVisibility(0);
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(Context context, String uri) {
        @SuppressLint("DiscouragedApi") int imageResource = context.getResources().getIdentifier("@drawable/"+uri, null, context.getPackageName());
        return context.getResources().getDrawable(imageResource);
    }

    public static void setColor(Context context, View v, int color) {
        if (v instanceof ImageView) {
            ImageView image = (ImageView) v;
            image.setColorFilter(
                    ContextCompat.getColor(context, color),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(v instanceof TextView){
            TextView txt = (TextView) v;
            txt.setTextColor(ContextCompat.getColor(context, color));
        }
    }


    public static void setOnHoverLabel(EditText editText) {
        Label layoutLabel = (Label) editText.getParent();
        TextView label = (TextView) layoutLabel.getChildAt(0);
        label.animate().translationY(-8).setDuration(100).start();
    }

    public static void effectClick(Context context, View view) {
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.btn_click);
        view.startAnimation(anim);
    }

    public static class DateSet implements DatePickerDialog.OnDateSetListener{
        ObservableField<String> value;
        private android.widget.EditText editTextDate;

        public DateSet(ObservableField<String> mValue, android.widget.EditText editText) {
            this.value = mValue;
            this.editTextDate = editText;
        }
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            month += 1;
            String date = formatted(dayOfMonth)+MINUS+formatted(month)+MINUS+year;
            value.set(date);
            editTextDate.setText(date);
        }

        private String formatted(int value) {
            String formatted = String.valueOf(value);
            if (value < 10) {
                formatted = STR_ZERO+value;
            }
            return formatted;
        }
    }

    public static class CountValidation implements TextWatcher {

        private TextView label;
        private Context context;
        private int maxCount;

        public CountValidation(Context context, TextView label, int maxCount) {
            this.context = context;
            this.label = label;
            this.maxCount = maxCount;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int count = charSequence.length();

            label.setText(String.valueOf(count)
                    .concat(SLASH)
                    .concat(String.valueOf(maxCount)));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (count != maxCount) {
                    label.setTextColor(context.getColor(R.color.red_400));
                } else {
                    label.setTextColor(context.getColor(R.color.grey_600));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public static void importDataFromAssets(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        try {
            InputStream stream =  context.getAssets().open("data.txt");
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String Text;
            while ((Text = bufferedReader.readLine()) != null) {
                db.insertDistrict(Text.split(";")[2]);
            }
            streamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String formatNumber(int number) {
        String value = String.valueOf(number);
        if (number < 10) {
            value = "0"+number;
        }
        return value;
    }

    public static  String DateSQLFormatNow() {
        Date currentTime = Calendar.getInstance().getTime();
        String s_currentTime = currentTime.toString();
        String y = s_currentTime.substring(s_currentTime.length()-4, s_currentTime.length());
        String month = formatNumber(currentTime.getMonth()+1);
        String date = formatNumber(currentTime.getDate());
        String hours = formatNumber(currentTime.getHours());
        String minutes = formatNumber(currentTime.getMinutes());
        String seconds = formatNumber(currentTime.getSeconds());
        String dd = "";
        dd += date+"-";
        dd += month+"-";
        dd += y+" ";
        dd += hours+":";
        dd += minutes+":";
        dd += seconds;
        return dd;
    }

    public static String getIpAddress(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    public static void createQrCode(String data, ImageView imageView) {

        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.DKGRAY);
        try {
            Bitmap bitmap = qrgEncoder.getBitmap();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
