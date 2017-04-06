package com.jay.battlecity.game;

import com.helloworld.socketlib.bean.Action;
import com.helloworld.socketlib.bean.TransmissionData;
import com.helloworld.socketlib.client.Client;

import java.io.IOException;

/**
 * Created by PinkD on 2017/4/2.
 * TestClient
 */

public class TestClient {
    private Client client;
    private String username;

    public TestClient(Client client,String username) {
        this.client = client;
        this.username = username;
    }

    public void test() throws IOException {
        client.send(Action.INIT);
        client.send(new TransmissionData(Action.FIRE, System.currentTimeMillis(), username, 1, 2, 3));
        client.send(new TransmissionData(Action.FIRE, System.currentTimeMillis(), username, 2, 3, 4));
    }
}
