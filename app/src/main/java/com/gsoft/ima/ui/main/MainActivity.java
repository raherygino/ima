package com.gsoft.ima.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.databinding.BottomSheetSendMoneyBinding;
import com.gsoft.ima.utils.BluetoothHelper;
import com.gsoft.ima.utils.BluetoothHelperReceiver;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    private BluetoothHelperReceiver bluetoothHelperReceiver;

    private final Handler messageHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0) {
                String receivedMessage = (String) message.obj;
                Toast.makeText(getApplicationContext(), "Received message: " + receivedMessage, Toast.LENGTH_SHORT).show();
                // Do something with the received message
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getColor(R.color.grey_50));
                View decor = getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        String deviceAddress = "00:00:00:00:00:00"; // Replace with the Bluetooth device address
        String message = "Hello, this is a test message.";

        BluetoothHelper bluetoothHelper = new BluetoothHelper(this);

        if (bluetoothHelper.isBluetoothSupported() && bluetoothHelper.isBluetoothEnabled()) {
            if (bluetoothHelper.connectToDevice(deviceAddress)) {
                bluetoothHelper.sendMessage(message);
                bluetoothHelper.disconnect();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to connect to device.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is not supported or not enabled.", Toast.LENGTH_SHORT).show();
        }

        bluetoothHelperReceiver = new BluetoothHelperReceiver(messageHandler);

        if (bluetoothHelperReceiver.isBluetoothSupported() && bluetoothHelperReceiver.isBluetoothEnabled()) {
            if (bluetoothHelperReceiver.startServer()) {
                Toast.makeText(getApplicationContext(), "Server started. Waiting for incoming messages...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to start server.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is not supported or not enabled.", Toast.LENGTH_SHORT).show();
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.designBottomSheet);
        binding.sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothHelperReceiver.stopServer();
    }
}