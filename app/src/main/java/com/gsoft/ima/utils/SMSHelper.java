package com.gsoft.ima.utils;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSHelper {
    private Context context;
    public SMSHelper(Context context) {
        this.context = context;
    }
    public void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(context, "Message sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed to send message.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

