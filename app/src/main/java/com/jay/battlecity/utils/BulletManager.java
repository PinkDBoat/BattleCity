package com.jay.battlecity.utils;

import com.jay.battlecity.model.Bullet;
import com.jay.battlecity.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 对子弹统一管理
 */

public class BulletManager {
    private final List<Bullet> mBullets;

    private BulletManager() {
        mBullets = new ArrayList<>();
    }

    public static BulletManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Location createLocationByTank(Location lctOfTank) {
        Location location = new Location();
        int[] point = MathUtils.vectorDecomposition(lctOfTank.height / 2, lctOfTank.angle);
        location.cx = point[0];
        location.cy = point[1];
        location.angle = lctOfTank.angle;
        return location;
    }

    private Bullet createBullet(Location lctOfTank) {
        Location location = createLocationByTank(lctOfTank);
        return new Bullet(location);
    }

    private void resetBullet(Bullet bullet, Location lctOfTank) {
        bullet.resetLocation(createLocationByTank(lctOfTank));
        bullet.setLiving(true);
    }

    public void fire(Location lctOfTank) {
        //寻找可复用的子弹
        for (Bullet bullet : mBullets) {
            if (!bullet.isLiving()) {
                resetBullet(bullet, lctOfTank);
                return;
            }
        }
        //没有则创建
        Bullet bullet = createBullet(lctOfTank);
        bullet.setLiving(true);
        mBullets.add(bullet);
    }

    private static class SingletonHolder {
        private static final BulletManager INSTANCE = new BulletManager();
    }
}
