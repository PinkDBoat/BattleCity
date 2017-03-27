package com.jay.battlecity.model;

/**
 * 可移动物体的基类
 */

public abstract class Entity {
    private Location mLocation;
    private int Speed;
    private boolean mVisible;

    public Location getLocation() {
        return mLocation;
    }

    public int getSpeed() {
        return Speed;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public abstract void move();
}
