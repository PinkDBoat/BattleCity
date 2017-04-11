package com.jay.battlecity.utils;

import android.content.Context;

import com.jay.battlecity.ui.view.TankView;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class GamePad {
    private TankView mTank;
    private Context context;
    private int angel;
    private int velocity = 0;
    private boolean isCollision = false;

    /**
     * 还未添加对 摇杆的注册 和对 子弹发射的管理
     */
    public GamePad (Context context, TankView tankView){
        this.context =context;
        this.mTank = tankView;
    }

    /**
     * dx dy单位为dp
     * 移动前先转向
     * 移动基本思路为将 dx dy 转换为方向后（±1）再乘以velocity然后scrollBy移动View
     *（其实也完全可以由 dx²+dy² 开根得到第三边长度再乘以固定velocity，直接摇杆掌控速度）
     */
    public void move(int dx, int dy) {
        rotate(dx, dy);
        if (!isCollision){
            mTank.scrollBy(- CalcUtils.dp2px(dx%1000)*velocity, - CalcUtils.dp2px(dy%1000)*velocity);
        }
    }

    /*
     * 设置速度使用，初始默认为0
     */
    public void setVelocity(int velocity){
        this.velocity = velocity;
    }

    /*
     * 每次转向之后先判断前方是否碰撞（这里把坦克当成圆形的，所以先转向再判断）
     */
    public void rotate(int dx, int dy) {
        angel = CalcUtils.vectorDegree(dx, dy);
        mTank.setDirection(angel);
        isCollision = isCollision();
    }

    /*
     * 判断碰撞用，待完善
     */
    public boolean isCollision() {
        return true;
    }

    public void destroyed() {

    }
}
