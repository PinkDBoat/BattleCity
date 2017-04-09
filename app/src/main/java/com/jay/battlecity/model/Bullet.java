package com.jay.battlecity.model;

import com.jay.battlecity.utils.CalcUtils;

/**
 * 子弹模型类
 */

public class Bullet extends Entity {
    private static final int LIVE_TIME = 1000;   //时间（单位：毫秒）
    private static final int RADIUS = 20;    //子弹半径(单位：dp)

    private long mStartTime;

    public Bullet(Location location) {
        super(location);
        location.width = CalcUtils.dp2px(RADIUS);
        location.height = CalcUtils.dp2px(RADIUS);
    }

    public void show(Location location) {
        location.width = CalcUtils.dp2px(RADIUS);
        location.height = CalcUtils.dp2px(RADIUS);
        setLocation(location);
        setLiving(true);
    }

    @Override
    public void setLiving(boolean living) {
        super.setLiving(living);
        if (living) {
            mStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void move() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mStartTime > LIVE_TIME) {
            setLiving(false);
            return;
        }

        Location location = getLocation();
        int[] point = CalcUtils.vectorDecomposition(getSpeed(), location.angle);
        location.cx += point[0];
        location.cy += point[1];
        setLocation(location);
    }
}
