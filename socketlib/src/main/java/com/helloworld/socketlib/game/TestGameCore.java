package com.helloworld.socketlib.game;

import com.helloworld.socketlib.bean.Action;
import com.helloworld.socketlib.bean.ItemType;
import com.helloworld.socketlib.bean.StatusCode;
import com.helloworld.socketlib.bean.TransmissionData;
import com.helloworld.socketlib.server.ClientPool;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PinkD on 2017/3/26.
 * implement for ActionHandler
 */
public class TestGameCore implements GameCore {
    @Override
    public byte handle(TransmissionData data) {
        System.out.println(data);
        return StatusCode.STATUS_OK;
    }

    @Override
    public byte handle(byte action) {
        System.out.println(action);
        if (action == Action.GAME_OVER) {
            System.out.println("Game Over");
        }
        return StatusCode.STATUS_OK;
    }

    @Override
    public Map<InetAddress, TransmissionData> getData() {
        Map<InetAddress, TransmissionData> data = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        for (InetAddress inetAddress : ClientPool.getClientPool().keySet()) {
            data.put(inetAddress, new TransmissionData(ItemType.TYPE_TANK, Action.MOVE, timestamp, "username", 1, 2, 3));
        }
        return data;
    }
}
