package com.jay.battlecity.model;

/**
 * 描述物体位置的类
 */

public class Location implements Cloneable {
    //中心坐标
    public int cx;
    public int cy;
    public int width;
    public int height;
    //朝向，以x轴正向为0度，顺时针旋转为正方向，范围为[0-360)
    public int angle;

    @Override
    protected Location clone() throws CloneNotSupportedException {
        return (Location) super.clone();
    }
}