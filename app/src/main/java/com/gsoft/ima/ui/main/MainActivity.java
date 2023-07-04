package com.gsoft.ima.ui.main;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.di.components.BottomNav;
import com.gsoft.ima.ui.main.send.SendDialog;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentFragment;
    private BottomNav nav;


    ServerSocket serverSocket;
    Thread Thread1 = null;
    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 8080;

    private PrintWriter output;
    private BufferedReader input;

    String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new HomeFragment());
        nav = new BottomNav(this);
        nav.setConfig();
        nav.setItemActivate(0);
        binding.fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDialog dialog = new SendDialog(MainActivity.this);
                dialog.show();
            }
        });
      //  hideLayoutNetwork();
        configServer();
    }

    public void configServer() {
        SERVER_IP = Utils.getIpAddress(MainActivity.this);
        Thread1 = new Thread(new Thread1());
        Thread1.start();
    }

    class Thread1 implements Runnable {

        @Override
        public void run() {
            Socket socket;
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                runOnUiThread(new Runnable() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        binding.messageNetwork.setText("Not connected IP: "+SERVER_IP);
                    }
                });
                try {
                    socket = serverSocket.accept();

                    output = new PrintWriter(socket.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // Send data over the socket
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    String data = "Hello, Server!";
                    dataOutputStream.writeUTF(data);

                    runOnUiThread(new Runnable() {
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
}
