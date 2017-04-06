package com.jay.battlecity.game;

import android.util.Log;

import com.helloworld.socketlib.bean.Action;
import com.helloworld.socketlib.bean.StatusCode;
import com.helloworld.socketlib.bean.TransmissionData;
import com.helloworld.socketlib.game.GameCore;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by PinkD on 2017/4/2.
 * GameCore
 */

public class Game implements GameCore {//TODO implement
    private static final String TAG = "Game";

    /**
     * @param data {@link TransmissionData}
     * @return status code {@link StatusCode}
     */
    @Override
    public byte handle(TransmissionData data) {
        Log.d(TAG, "handle: " + data);
        return 0;
    }

    /**
     * @param action without data {@link Action} {@link Action#hasData(byte)}
     *               function --> handle action
     * @return status code {@link StatusCode}
     */
    @Override
    public byte handle(byte action) {
        Log.d(TAG, "handle: " + action);
        return 0;
    }

    /**
     * @return data of clients
     */
    @Override
    public Map<InetAddress, TransmissionData> getData() {
        return null;
    }
}
