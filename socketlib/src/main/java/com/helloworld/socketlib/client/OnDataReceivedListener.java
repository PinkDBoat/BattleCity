package com.helloworld.socketlib.client;

import com.helloworld.socketlib.bean.TransmissionData;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/27.
 * OnReceiveDataListener
 */
public interface OnDataReceivedListener {
    void onDataReceived(Map<InetAddress, TransmissionData> data);

    void onStatus(byte status);
}
