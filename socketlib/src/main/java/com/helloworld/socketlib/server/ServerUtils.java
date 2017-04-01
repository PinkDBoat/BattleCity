package com.helloworld.socketlib.server;

import com.helloworld.socketlib.bean.TransmissionData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by PinkD on 2017/3/26.
 * ServerUtils
 */
public class ServerUtils {

    public static byte getAction(Socket socket) throws IOException {
        return (byte) socket.getInputStream().read();
    }

    public static TransmissionData getData(Socket socket) throws IOException, ClassNotFoundException {
        return (TransmissionData) new ObjectInputStream(socket.getInputStream()).readObject();
    }
}
