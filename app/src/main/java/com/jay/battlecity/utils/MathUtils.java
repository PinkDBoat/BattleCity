package com.jay.battlecity.utils;

/**
 * 数学计算工具类
 */

public class MathUtils {
    /**
     * 矢量分解
     *
     * @param length 矢量长度
     * @param angle  矢量方向
     * @return 【0】分解到x方向，【1】分解到y方向
     */
    public static int[] vectorDecomposition(int length, int angle) {
        int[] point = new int[2];
        point[0] = (int) (length * Math.cos(Math.toRadians(angle)));
        point[1] = (int) (length * Math.sin(Math.toRadians(angle)));
        return point;
    }
}
