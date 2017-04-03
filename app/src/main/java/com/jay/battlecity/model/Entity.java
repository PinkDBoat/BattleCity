package com.jay.battlecity.model;

/**
 * 可移动物体的基类
 */

public abstract class Entity {
    private Location mLocation;
    private int mSpeed;
    private boolean mLiving;

    public Location getLocation() {
        try {
            return mLocation.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return mLocation;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public boolean isLiving() {
        return mLiving;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    private void setSpeed(int speed) {
        if (speed >= 360) {
            speed -= 360;
        } else if (speed < 0) {
            speed += 360;
        }
        mSpeed = speed;
    }

    public void setLiving(boolean living) {
        mLiving = living;
    }

    /**
     * 旋转方法
     * @param dAngle 旋转的度数，顺时针为正，逆时针为负
     */
    public void rotate(int dAngle) {
        setSpeed(mSpeed + dAngle);
    }

    public abstract void move();
}
