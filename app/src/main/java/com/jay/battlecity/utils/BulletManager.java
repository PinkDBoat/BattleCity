package com.jay.battlecity.utils;

import com.jay.battlecity.model.Bullet;
import com.jay.battlecity.model.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 对子弹统一管理
 */

public class BulletManager {
    public static final int SUM_OF_BULLET = Tank.TOTAL_BULLET_OF_ONE_TANK * 2;
    private final List<Bullet> mBullets;

    private BulletManager() {
        mBullets = new ArrayList<>();
    }

    public static BulletManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
/*
    private Bullet initBullect(Location lctOfTank) {

    }
*/
    private static class SingletonHolder {
        private static final BulletManager INSTANCE = new BulletManager();
    }
}
