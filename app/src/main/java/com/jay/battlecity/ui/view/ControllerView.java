package com.jay.battlecity.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jay.battlecity.R;
import com.jay.battlecity.model.Location;
import com.jay.battlecity.utils.CalcUtils;

/**
 * 控制器view
 */

public class ControllerView extends FrameLayout {
    private ImageButton mFireBtn;
    private ImageButton mMoveBtn;

    //现在的移动按钮位置
    private Location mMoveBtnLocation;
    //移动按钮的背景的位置（用于限制移动按钮的移动范围）
    private Location mMoveBackgroundLocation;
    private ControlListener mControlListener;

    //是否处于移动事件
    private boolean mMoving;

    public ControllerView(Context context) {
        super(context);
    }

    public ControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        View container = LayoutInflater.from(context).inflate(R.layout.view_controller, this, true);
        ImageView moveBackground = (ImageView) container.findViewById(R.id.background_move);
        mFireBtn = (ImageButton) container.findViewById(R.id.fire);
        mMoveBtn = (ImageButton) findViewById(R.id.move);
        mMoveBtnLocation = new Location();
        mMoveBackgroundLocation = new Location();
        getViewLocation(mMoveBtn, new Location[]{mMoveBtnLocation});
        getViewLocation(moveBackground, new Location[]{mMoveBackgroundLocation});
        registerListener();
    }

    private void getViewLocation(final View view, final Location[] locations) {
        /*利用数组的浅拷贝特性返回获取到的Location*/
        view.post(new Runnable() {
            @Override
            public void run() {
                int with = view.getWidth();
                int height = view.getHeight();
                int cx = (int) (view.getX() + with / 2);
                int cy = (int) (view.getY() + height / 2);
                Location location = locations[0];
                location.cx = cx;
                location.cy = cy;
                location.width = with;
                location.height = height;
            }
        });
    }

    private void registerListener() {
        mFireBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlListener.fire();
            }
        });

        mMoveBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_UP) {
                    int cx = mMoveBackgroundLocation.cx;
                    int cy = mMoveBackgroundLocation.cy;
                    mMoveBtnLocation.cx = cx;
                    mMoveBtnLocation.cy = cy;
                    mMoveBtn.setX(mMoveBtnLocation.left());
                    mMoveBtn.setY(mMoveBtnLocation.top());
                    if (mMoving) {
                        mControlListener.stopMove();
                        mMoving = false;
                    }
                } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    moveOrRotate(x, y);
                }
                return true;
            }
        });

        //设置默认的监听器，减少null判断
        setControlListener(new ControlListener() {
            @Override
            public void fire() {
                Log.v("ControlListener", "fire");
            }

            @Override
            public void move() {
                Log.v("ControlListener", "move");
            }

            @Override
            public void rotate(int angle) {
                Log.v("ControlListener", "angle:" + angle);
            }

            @Override
            public void stopMove() {
                Log.v("ControlListener", "stop");
            }
        });
    }

    private void moveOrRotate(int cx, int cy) {
        int status = locationWithMoveBackground(cx, cy);
        int angle = CalcUtils.vectorDegree(cx - mMoveBackgroundLocation.cx, cy - mMoveBackgroundLocation.cy);
        if (status == CalcUtils.OUT) {
            //将位置修正到圆上
            int r = mMoveBackgroundLocation.width / 2;
            int ox = mMoveBackgroundLocation.cx;
            int oy = mMoveBackgroundLocation.cy;
            int[] point = CalcUtils.polarToRectangularCoordinate(ox, oy, r, angle);
            cx = point[0];
            cy = point[1];
            status = 0;
        }
        if (status == CalcUtils.ON) {
            mMoveBtn.setX(mMoveBtnLocation.left());
            mMoveBtn.setY(mMoveBtnLocation.top());
            if (angle != mMoveBtnLocation.angle) {
                mControlListener.rotate(angle);
            }
            if (!mMoving) {
                mControlListener.move();
                mMoving = true;
            }
        } else if (status == CalcUtils.INNER) {
            if (angle != mMoveBtnLocation.angle) {
                mMoveBtn.setX(mMoveBtnLocation.left());
                mMoveBtn.setY(mMoveBtnLocation.top());
                mControlListener.rotate(angle);
            }
            if (mMoving) {
                mMoving = false;
                mControlListener.stopMove();
            }
        }
        mMoveBtnLocation.angle = angle;
        mMoveBtnLocation.cx = cx;
        mMoveBtnLocation.cy = cy;
    }

    /**
     * 判断点x，y与移动按钮背景的位置关系
     *
     * @return -1（在圆外）、0（在圆上）、1（在圆内）
     */
    private int locationWithMoveBackground(int x, int y) {
        int x1 = mMoveBackgroundLocation.cx;
        int y1 = mMoveBackgroundLocation.cy;
        int r = mMoveBackgroundLocation.width / 2;
        return CalcUtils.circleAndPointLocation(x, y, x1, y1, r);
    }

    public void setControlListener(ControlListener controlListener) {
        mControlListener = controlListener;
    }

    public interface ControlListener {
        /**
         * 点击开火按钮时调用
         */
        void fire();

        /**
         * 在背景边界上滑动轨迹球时调用
         */
        void move();

        /**
         * 在背景区域内滑动轨迹球时调用
         * 在调用move前如果当前angle不等于轨迹球angle也会先调用此方法
         *
         * @param angle 旋转到
         */
        void rotate(int angle);

        /**
         * 需要停止时调用
         */
        void stopMove();
    }
}