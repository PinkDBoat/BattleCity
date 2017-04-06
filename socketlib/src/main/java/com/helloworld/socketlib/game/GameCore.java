package com.helloworld.socketlib.game;

import com.helloworld.socketlib.bean.TransmissionData;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/26.
 * Interface ActionHandler
 */
public interface GameCore {
    byte handle(TransmissionData data);

    byte handle(byte action);

    Map<InetAddress, TransmissionData> getData();
}
