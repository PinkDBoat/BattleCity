package com.helloworld.socketlib.server;


import com.helloworld.socketlib.bean.Action;
import com.helloworld.socketlib.bean.StatusCode;
import com.helloworld.socketlib.game.GameCore;
import com.helloworld.socketlib.utils.StreamWrapper;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketHandler {

    private StreamWrapper streamWrapper;
    private long lastSyncTime;
    private int syncDuration;

    public ClientSocketHandler() {
        syncDuration = 50;
    }

    public ClientSocketHandler(int syncDuration) {
        this.syncDuration = syncDuration;
    }

    public void onAccept(Socket socket, GameCore gameCore) {
        ClientPool.getClientPool().put(socket.getInetAddress(), this);
        try {
            streamWrapper = new StreamWrapper(socket);
            while (true) {
                byte action = streamWrapper.getAction();
                if (Action.hasData(action)) {
                    streamWrapper.sendStatus(gameCore.handle(streamWrapper.getData()));
                    long now = System.currentTimeMillis();
                    if (now - lastSyncTime > syncDuration) {
                        System.out.println("pool size: " + ClientPool.getClientPool().size());

                        for (ClientSocketHandler clientSocketHandler : ClientPool.getClientPool().values()) {
                            //sync data
                            try {
                                clientSocketHandler.streamWrapper.sendStatus(StatusCode.STATUS_SYNC);
                                clientSocketHandler.streamWrapper.sendSync(gameCore.getData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        lastSyncTime = now;
                    }
                } else {
                    streamWrapper.sendStatus(gameCore.handle(action));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("invalid data");
            e.printStackTrace();
        } finally {
            ClientPool.getClientPool().remove(socket.getInetAddress());
        }
    }

}
