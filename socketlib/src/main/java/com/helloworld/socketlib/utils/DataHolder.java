package com.helloworld.socketlib.utils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/26.
 * to save Clients
 */
public class DataHolder {
    public static final Map<InetAddress, String> CLIENTS = new HashMap<>();
}
