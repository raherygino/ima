package com.gsoft.ima.ui.main;

import static com.gsoft.ima.constants.main.MainConstants.SERVER_PORT;

import android.annotation.SuppressLint;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer implements Runnable{

    public final Object MainServer2;
    private MainActivity activity;
    private ActivityMainBinding binding;

    public  MainServer(MainActivity mainActivity, ActivityMainBinding mainBinding) {
        this.activity = mainActivity;
        this.binding = mainBinding;
        this.MainServer2 = new MainServer2();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            activity.runOnUiThread(new Runnable() {

                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // binding.messageNetwork.setText("Not connected IP: "+SERVER_IP);
                }
            });
            try {
                activity.socket = serverSocket.accept();
                // Send data over the socket
                DataOutputStream dataOutputStream = new DataOutputStream(activity.socket.getOutputStream());
                String data = "sent";
                dataOutputStream.writeUTF(data);

                activity.runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        //  binding.messageNetwork.setText("Connected: "+data);
                        /// RECEIVED
                        /// ADDITION
                        try {
                            serverSocket.close();
                            Thread server = new Thread(MainServer.this);
                            server.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MainServer2 implements Runnable {

        @Override
        public void run() {
            Socket socket;
            try {
                socket = new Socket(activity.SERVER_IP, SERVER_PORT);

                // Received data over the socket
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String data = dataInputStream.readUTF();

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        binding.messageNetwork.setText(activity.getString(R.string.connected));

                        ///SEND
                        ///SUBTRACTION
                        if (socket.isConnected()) {
                            try {
                                socket.close();
                                binding.messageNetwork.setText(activity.getString(R.string.closed));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                new Thread(MainServer2.this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
