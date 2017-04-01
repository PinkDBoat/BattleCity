package com.helloworld.socketlib.bean;

/**
 * Created by PinkD on 2017/3/26.
 * StatusCode for response
 */
public class StatusCode {
    public static final byte STATUS_OK = 0x11;
    public static final byte STATUS_SYNC = 0x12;
    public static final byte STATUS_UNKNOWN = 0x13;

    public static boolean hasData(byte action) {
        return action == STATUS_SYNC;
    }
}
