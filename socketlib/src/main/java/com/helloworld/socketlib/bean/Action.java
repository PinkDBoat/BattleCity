package com.helloworld.socketlib.bean;

public class Action {
    public static final byte INIT = 0;
    public static final byte FIRE = 1;
    public static final byte MOVE = 2;
    public static final byte GAME_OVER = 3;
    public static final byte GAME_START = 4;
    public static final byte POSITION = 5;

    public static boolean hasData(byte action) {
        return action == FIRE || action == MOVE;
    }
}
