package com.jay.battlecity.utils;

/**
 * 数学计算工具类
 */

public class MathUtils {
    public static final int INNER = 1;
    public static final int ON = 0;
    public static final int OUT = -1;

    private static int sPPI;

    public static void init(int ppi) {
        sPPI = ppi;
    }

    /**
     * 矢量分解
     *
     * @param length 矢量长度
     * @param degree 矢量方向
     * @return 【0】分解到x方向，【1】分解到y方向
     */
    public static int[] vectorDecomposition(int length, int degree) {
        int[] point = new int[2];
        point[0] = (int) (length * Math.cos(Math.toRadians(degree)));
        point[1] = (int) (length * Math.sin(Math.toRadians(degree)));
        return point;
    }

    /**
     * 计算向量的方向
     *
     * @param x1 初位置x坐标
     * @param y1 初位置y坐标
     * @param x2 末位置x坐标
     * @param y2 末位置y坐标
     * @return 向量的方向（单位：°）
     */
    public static int vectorDegree(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return vectorDegree(dx, dy);
    }

    public static int vectorDegree(int dx, int dy) {
        double degree = Math.toDegrees(Math.atan(Math.abs(1.0 * dy / dx)));
        //角度换算
        if (dx == 0 && dy == 0) {
            degree = -1;
        } else if (dx == 0) {
            degree = dy > 0 ? 90 : 270;
        } else if (dy == 0) {
            degree = dx > 0 ? 0 : 180;
        } else if (dy > 0 && dx < 0) {
            degree = 180 - degree;
        } else if (dy < 0 && dx < 0) {
            degree = 180 + degree;
        } else if (dy < 0 && dx > 0) {
            degree = 360 - degree;
        }
        return (int) Math.round(degree);
    }

    /**
     * 计算圆与点的位置关系
     *
     * @param px 点x坐标
     * @param py 点y坐标
     * @param cx 圆心x坐标
     * @param cy 圆心y坐标
     * @param r  圆半径
     * @return 位置关系
     */
    public static int circleAndPointLocation(int px, int py, int cx, int cy, int r) {
        double d = r * r - Math.pow(px - cx, 2) - Math.pow(py - cy, 2);
        return d == 0 ? 0 : (int) (d / Math.abs(d));
    }

    /**
     * 极坐标转直角坐标
     *
     * @param ox     极点x坐标
     * @param oy     极点y坐标
     * @param p      极径
     * @param degree 角度
     * @return 直角坐标
     */
    public static int[] polarToRectangularCoordinate(int ox, int oy, int p, int degree) {
        int[] point = new int[2];
        point[0] = (int) (p * Math.cos(Math.toRadians(degree)) + ox);
        point[1] = (int) (p * Math.sin(Math.toRadians(degree)) + oy);
        return point;
    }

    /**
     * dp转px
     */
    public static int dp2px(int dp) {
        return (int) Math.round(1.0 * dp * sPPI / 160);
    }
}