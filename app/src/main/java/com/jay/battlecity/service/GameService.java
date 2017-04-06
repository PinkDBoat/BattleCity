package com.jay.battlecity.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.helloworld.socketlib.server.ClientSocketHandler;
import com.helloworld.socketlib.server.OnNewHandlerListener;
import com.helloworld.socketlib.server.Server;
import com.jay.battlecity.game.Game;

import java.io.IOException;

public class GameService extends Service {
    private Server server;

    public GameService() throws IOException {
        server = new Server(2333, new OnNewHandlerListener() {
            @Override
            public ClientSocketHandler newHandler() {
                return new ClientSocketHandler();
            }
        }, new Game());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!server.isRunning()) {
            server.listenOnBackground();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (server.isRunning()) {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
