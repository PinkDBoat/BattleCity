package com.helloworld.socketlib.server;

/**
 * Created by PinkD on 2017/3/26.
 * OnServerAcceptListener
 */
public interface OnNewHandlerListener {
    ClientSocketHandler newHandler();
}
