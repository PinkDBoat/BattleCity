package com.jay.battlecity.model;

import com.jay.battlecity.utils.MathUtils;

/**
 * 子弹模型类
 */

public class Bullet extends Entity {
    public static final int LIVE_TIME = 1000;   //时间（单位：毫秒）
    private final long mStartTime;

    public Bullet() {
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void move() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mStartTime > LIVE_TIME) {
            setLiving(false);
            return;
        }

        Location location = getLocation();
        int[] point = MathUtils.vectorDecomposition(getSpeed(), location.angle);
        location.cx += point[0];
        location.cy += point[1];
        setLocation(location);
    }
}
