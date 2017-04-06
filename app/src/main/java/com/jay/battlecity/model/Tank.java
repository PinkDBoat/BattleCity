package com.jay.battlecity.model;

import com.jay.battlecity.utils.BulletManager;
import com.jay.battlecity.utils.MathUtils;

/**
 * 坦克模型类
 */

public class Tank extends Entity {
    public static final int TOTAL_BULLET_OF_ONE_TANK = 3;
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    //单位：dp
    private static final int WIDTH = 50;
    private static final int HEIGHT = 80;

    private int mRestBullet = TOTAL_BULLET_OF_ONE_TANK;

    public Tank(Location location) {
        super(location);
        location.width = MathUtils.dp2px(WIDTH);
        location.height = MathUtils.dp2px(HEIGHT);
    }


    public int fire() {
        if (mRestBullet != 0) {
            BulletManager.getInstance().fire(getLocation());
            --mRestBullet;
            return SUCCESS;
        }
        return FAIL;
    }

    @Override
    public void move() {
        Location location = getLocation();
        int[] point = MathUtils.vectorDecomposition(getSpeed(), location.angle);
        location.cx += point[0];
        location.cy += point[1];
        setLocation(location);
    }
}
