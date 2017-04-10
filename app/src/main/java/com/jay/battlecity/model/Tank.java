package com.jay.battlecity.model;

import com.jay.battlecity.utils.CalcUtils;

/**
 * 坦克模型类
 */

public class Tank extends Entity {
    public static final int TOTAL_BULLET_OF_ONE_TANK = 3;

    //单位：dp
    private static final int WIDTH = 50;
    private static final int HEIGHT = 80;

    private int mRestBullet = TOTAL_BULLET_OF_ONE_TANK;

    public Tank(Location location) {
        super(location);
        location.width = CalcUtils.dp2px(WIDTH);
        location.height = CalcUtils.dp2px(HEIGHT);
    }

    public void fire() {
        // TODO implement
    }

    @Override
    public void move() {
        Location location = getLocation();
        int[] point = CalcUtils.vectorDecomposition(getSpeed(), location.angle);
        location.cx += point[0];
        location.cy += point[1];
        setLocation(location);
    }
}
