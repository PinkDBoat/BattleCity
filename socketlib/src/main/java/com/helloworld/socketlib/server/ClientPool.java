package com.helloworld.socketlib.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/31.
 * ClientPool to save socket clients
 */
public class ClientPool {
    private static Map<InetAddress, ClientSocketHandler> clientMap = new HashMap<>();

    public static Map<InetAddress, ClientSocketHandler> getClientPool() {
        return clientMap;
    }
}
