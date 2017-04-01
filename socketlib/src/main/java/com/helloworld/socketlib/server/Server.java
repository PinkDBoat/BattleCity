package com.helloworld.socketlib.server;

import com.helloworld.socketlib.game.GameCore;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private boolean running;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private OnNewHandlerListener onNewHandlerListener;
    private GameCore gameCore;

    protected Server(OnNewHandlerListener onNewHandlerListener, GameCore gameCore) {
        executorService = Executors.newCachedThreadPool();
        this.onNewHandlerListener = onNewHandlerListener;
        this.gameCore = gameCore;
    }

    public Server(String ip, int port, OnNewHandlerListener onNewHandlerListener, GameCore gameCore) throws IOException {
        this(onNewHandlerListener, gameCore);
        serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
    }

    public Server(int port, OnNewHandlerListener onNewHandlerListener, GameCore gameCore) throws IOException {
        this(onNewHandlerListener, gameCore);
        serverSocket = new ServerSocket(port, 8);
    }

    public void listen() {
        try {
            running = true;
            while (running) {
                executorService.submit(new DefaultRunnable(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenOnBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        }).start();
    }

    public void stop() throws IOException {
        running = false;
        serverSocket.close();
    }

    private class DefaultRunnable implements Runnable {
        private Socket socket;

        public DefaultRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            onNewHandlerListener.newHandler().onAccept(socket, gameCore);
        }
    }

}
