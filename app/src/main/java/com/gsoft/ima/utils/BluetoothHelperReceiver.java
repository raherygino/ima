package com.gsoft.ima.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BluetoothHelperReceiver {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private boolean isListening;
    private ReceiveThread receiveThread;
    private final Handler handler;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String SERVICE_NAME = "BluetoothChat";

    public BluetoothHelperReceiver(Handler handler) {
        this.handler = handler;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isBluetoothSupported() {
        return bluetoothAdapter != null;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public boolean startServer() {
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVICE_NAME, MY_UUID);
            bluetoothSocket = serverSocket.accept();
            inputStream = bluetoothSocket.getInputStream();
            startReceiving();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stopServer() {
        isListening = false;
        if (receiveThread != null) {
            receiveThread.interrupt();
            receiveThread = null;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = null;
        }
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothSocket = null;
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverSocket = null;
        }
    }

    private void startReceiving() {
        isListening = true;
        receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (isListening) {
                try {
                    bytes = inputStream.read(buffer);
                    String message = new String(buffer, 0, bytes);
                    handler.obtainMessage(0, message).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    isListening = false;
                }
            }
        }
    }
}

