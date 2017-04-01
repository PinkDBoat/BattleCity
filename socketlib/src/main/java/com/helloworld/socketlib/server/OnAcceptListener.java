package com.helloworld.socketlib.server;

import java.net.Socket;

/**
 * Created by PinkD on 2017/3/26.
 * OnServerAcceptListener
 */
public interface OnAcceptListener {
    void onAccept(Socket socket);
}
