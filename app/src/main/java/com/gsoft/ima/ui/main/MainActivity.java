package com.gsoft.ima.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.di.components.BottomNav;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.utils.UserLogged;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.*;
import static com.gsoft.ima.constants.main.ScanConstaints.*;
import static com.gsoft.ima.constants.main.TransactionConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentFragment;
    public BottomNav nav;
    public String SERVER_IP = EMPTY;
    public Socket socket;
    public String convert = "MGA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new HomeFragment());
        configBottomNav();
        configServer();
        hideLayoutNetwork();
    }

    private void configBottomNav() {
        nav = new BottomNav(this);
        nav.setConfig();
        nav.setItemActivate(0);
        binding.fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
    }

    public void configServer() {
        SERVER_IP = Utils.getIpAddress(MainActivity.this);
        MainServer server = new MainServer(this, binding);
        Thread thread1 = new Thread(server);
        thread1.start();
    }

    public void setFragment(Fragment fragment) {
        currentFragment =  fragment.getClass().toString().replace(CLASS, EMPTY);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fragment);
        fragmentTransaction.commit();
    }

    public void hideLayoutNetwork() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutInfoNetwork.setVisibility(View.GONE);
            }
        }, 6000);
    }

    @Override
    public void onBackPressed() {
        if (!currentFragment.contains(FRG_HOME)) {
            setFragment(new HomeFragment());
            nav.setItemActivate(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            if (data == null)
                return;
            String result = data.getStringExtra(QR_SCAN_ERROR_IMAGE);
            if (result != null) {
                AlertDialog dialog = new AlertDialog(MainActivity.this, getString(R.string.error_scan), getString(R.string.error_scan_details));
                dialog.show();
            }
            return;

        }

        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;

            String result = data.getStringExtra(QR_SCAN_RESULT);

            if (result.contains(NAME_SENDER)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Transaction transaction = new Transaction(jsonObject.getInt(AMOUNT));
                    transaction.nameSender = jsonObject.getString(NAME_SENDER);
                    transaction.numSender = jsonObject.getString(NUM_SENDER);
                    transaction.nameReceiver = jsonObject.getString(NAME_RECEIVER);
                    transaction.numReceiver = jsonObject.getString(NUM_RECEIVER);
                    transaction.ipAddress = jsonObject.getString(IP_SENDER);
                    SERVER_IP = transaction.ipAddress;
                    transaction.status = SUCCESS;
                    transaction.method = getString(R.string.qr_code);
/*
                    Thread server2 = new Thread((Thread) new MainServer(MainActivity.this, binding).MainServer2);
                    server2.start();*/

                    DatabaseHelper db = new DatabaseHelper(this);/*
                    if (socket != null) {
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            String message = "received";
                            dataOutputStream.writeUTF(message);
*/
                            if (transaction.numReceiver.equals(UserLogged.data(MainActivity.this).phone)) {
                                if (!db.checkTransJsonIfExist(result)) {
                                    if (db.insertTransaction(transaction) != -1) {
                                        db.updateBalance(transaction.amount, ADD);
                                        db.insertTransJson(result);
                                        User user = UserLogged.data(MainActivity.this);
                                        FetchData.updateBalance(MainActivity.this, user.phone, user.balance);
                                        transaction.status = RECEIVED;
                                        FetchData.createTransaction(MainActivity.this, transaction);
                                        result = getString(R.string.message_transaction_success);
                                    } else {
                                        result = getString(R.string.error);
                                    }
                                } else {
                                    result = getString(R.string.message_transaction_already_saved);
                                }
                            } else {
                                result = getString(R.string.message_not_recipient);
                            }/*
                        } catch (Exception e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                    } else {
                        result = "You are not connected";
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
            }
            AlertDialog dialog = new AlertDialog(MainActivity.this, EMPTY, result);
            dialog.show();
        }
    }

    public void getSocket() {
        if (socket != null) {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                Toast.makeText(MainActivity.this, dataInputStream.readUTF(), Toast.LENGTH_SHORT).show();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.server_off), Toast.LENGTH_SHORT).show();
        }
    }


    // Check for camera permission
    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Check if the user has been asked about the permission before
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        } else {
            Intent intent = new Intent(MainActivity.this, QrCodeActivity.class);
            startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            return true;
        }
    }
    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // Check if the permission is granted
            // Check if all permissions are granted
            boolean allPermissionsGranted = true;

            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                // Permissions granted
                Intent intent = new Intent(MainActivity.this, QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            } else {
                // Permissions denied
                Toast.makeText(this, getString(R.string.permission_strg_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
