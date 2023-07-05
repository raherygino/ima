package com.gsoft.ima.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.di.components.BottomNav;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.*;
import static com.gsoft.ima.constants.main.ScanConstaints.*;
import static com.gsoft.ima.constants.main.TransactionConstants.AMOUNT;
import static com.gsoft.ima.constants.main.TransactionConstants.IP_SENDER;
import static com.gsoft.ima.constants.main.TransactionConstants.NAME_RECEIVER;
import static com.gsoft.ima.constants.main.TransactionConstants.NAME_SENDER;
import static com.gsoft.ima.constants.main.TransactionConstants.NUM_RECEIVER;
import static com.gsoft.ima.constants.main.TransactionConstants.NUM_SENDER;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentFragment;
    private BottomNav nav;
    private static String SERVER_IP = EMPTY;
    public Socket socket;

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
                Intent intent = new Intent(MainActivity.this, QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            }
        });
    }

    public void configServer() {
        SERVER_IP = Utils.getIpAddress(MainActivity.this);
        Thread thread1 = new Thread(new Thread1());
        thread1.start();
    }

    class Thread1 implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                runOnUiThread(new Runnable() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        binding.messageNetwork.setText("Not connected IP: "+SERVER_IP);
                    }
                });
                try {
                    socket = serverSocket.accept();
                    // Send data over the socket
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    String data = "Hello";
                    dataOutputStream.writeUTF(data);

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            binding.messageNetwork.setText("Connected: "+data);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    transaction.status = "success";
                    transaction.method = "QR Code";
                    DatabaseHelper db = new DatabaseHelper(this);
                    if (socket != null) {
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            String message = "Hello";
                            dataOutputStream.writeUTF(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                    } else {
                        result = "You are not connected";
                    }
                    if (db.insertTransaction(transaction) != -1) {
                        result = "Transaction successfully";
                    } else {
                        result = "Error";
                    }
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
}
