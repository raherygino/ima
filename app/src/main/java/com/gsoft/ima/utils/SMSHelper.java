package com.gsoft.ima.utils;

import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSHelper {
    public static void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Failed to send message.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

