package com.helloworld.socketlib.bean;

import java.io.Serializable;

/**
 * Created by PinkD on 2017/3/26.
 * default TransmissionData
 */
public class TransmissionData implements Serializable {
    public byte action;
    public long timestamp;
    public String username;
    public int x;
    public int y;
    public float d;

    public TransmissionData(byte action, long timestamp, String username, int x, int y, float d) {
        this.action = action;
        this.username = username;
        this.x = x;
        this.y = y;
        this.d = d;
        this.timestamp = timestamp;
    }

    public TransmissionData(byte action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return username + "@" + timestamp + "-->" + action + "@(" + x + "," + y + "):" + d;
    }
}
