package com.helloworld.socketlib.utils;

import com.helloworld.socketlib.bean.TransmissionData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/26.
 * ServerUtils
 */
public class StreamWrapper {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public StreamWrapper(Socket socket) throws IOException {
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public byte getAction() throws IOException {
        return objectInputStream.readByte();
    }

    public synchronized void sendAction(byte action) throws IOException {
        objectOutputStream.writeByte(action);
    }

    public byte getStatus() throws IOException {
        return objectInputStream.readByte();
    }

    public synchronized void sendStatus(byte status) throws IOException {
        objectOutputStream.writeByte(status);
    }

    public TransmissionData getData() throws IOException, ClassNotFoundException {
        return (TransmissionData) objectInputStream.readObject();
    }

    public synchronized void sendData(TransmissionData data) throws IOException {
        objectOutputStream.writeObject(data);
    }

    public synchronized void sendSync(Map<InetAddress, TransmissionData> data) throws IOException {
        objectOutputStream.writeObject(data);
    }

    @SuppressWarnings("unchecked")
    public Map<InetAddress, TransmissionData> getSyncData() throws IOException, ClassNotFoundException {
        return (Map<InetAddress, TransmissionData>) objectInputStream.readObject();
    }

}
