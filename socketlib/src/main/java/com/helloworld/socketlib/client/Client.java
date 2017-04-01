package com.helloworld.socketlib.client;

import com.helloworld.socketlib.bean.StatusCode;
import com.helloworld.socketlib.bean.TransmissionData;
import com.helloworld.socketlib.utils.StreamWrapper;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by PinkD on 2017/3/26.
 * Client
 */
public class Client {
    private Socket socket;
    private OnDataReceivedListener onDataReceivedListener;
    private StreamWrapper streamWrapper;

    public Client(String host, int port, OnDataReceivedListener onDataReceivedListener) throws IOException {
        socket = new Socket(host, port);
        streamWrapper = new StreamWrapper(socket);
        this.onDataReceivedListener = onDataReceivedListener;
        listenOnBackground();
    }

    private void listenOnBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        byte status = streamWrapper.getStatus();
                        if (StatusCode.hasData(status)) {
                            onDataReceivedListener.onDataReceived(streamWrapper.getSyncData());
                        } else {
                            onDataReceivedListener.onStatus(status);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println("invalid data");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void send(TransmissionData data) throws IOException {
        send(data.action);
        streamWrapper.sendData(data);
    }

    public void send(byte action) throws IOException {
        streamWrapper.sendAction(action);
    }

    public void close() throws IOException {
        socket.close();
    }
}
