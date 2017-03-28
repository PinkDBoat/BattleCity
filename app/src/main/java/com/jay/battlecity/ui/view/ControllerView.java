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
        listenAction();
        //测试
        setControlListener(new ControlListener() {
            @Override
            public void fire() {
                Log.v("tag", "fire");
            }

            @Override
            public void move() {
                Log.v("tag", "move");
            }

            @Override
            public void rotate(int angle) {
                Log.v("tag", "angle:" + angle);
            }
        });
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

    private void listenAction() {
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
                    mMoveBtn.setX(cx - mMoveBtnLocation.width / 2);
                    mMoveBtn.setY(cy - mMoveBtnLocation.height / 2);
                } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    moveAndRotate(x, y);
                }
                return true;
            }
        });
    }

    private void moveAndRotate(int cx, int cy) {
        int status = locationWithMoveBackground(cx, cy);
        int angle = calculateAngle(cx - mMoveBackgroundLocation.cx, cy - mMoveBackgroundLocation.cy);
        if (status == -1) {
            //位置修正
            int r = mMoveBackgroundLocation.width / 2;
            cx = (int) (r * Math.cos(Math.toRadians(angle)) + mMoveBackgroundLocation.cx);
            cy = (int) (r * Math.sin(Math.toRadians(angle)) + mMoveBackgroundLocation.cy);
            status = 0;
        }
        if (status == 0) {
            mMoveBtn.setX(cx - mMoveBtnLocation.width / 2);
            mMoveBtn.setY(cy - mMoveBtnLocation.height / 2);
            if (angle != mMoveBtnLocation.angle) {
                mControlListener.rotate(angle);
            }
            mControlListener.move();
        } else if (status == 1) {
            mMoveBtn.setX(cx - mMoveBtnLocation.width / 2);
            mMoveBtn.setY(cy - mMoveBtnLocation.height / 2);
            mControlListener.rotate(angle);
        }
        mMoveBtnLocation.angle = angle;
        mMoveBtnLocation.cx = cx;
        mMoveBtnLocation.cy = cy;
    }

    /**
     * 计算轨迹球的当前角度
     *
     * @param dx 现在位置 - 中心位置
     * @param dy 同上
     * @return 在中心位置返回-1，在其他位置返回轨迹球当前的角度
     */
    private int calculateAngle(int dx, int dy) {
        int angle;
        if (dx == 0 && dy == 0) {
            angle = -1;
        } else if (dx == 0) {
            angle = dy > 0 ? 90 : 270;
        } else if (dy == 0) {
            angle = dx > 0 ? 0 : 180;
        } else if (dy > 0 && dx > 0) {
            double degrees = Math.toDegrees(Math.atan(Math.abs(1.0 * dy / dx)));
            angle = (int) Math.round(degrees);
        } else if (dy > 0 && dx < 0) {
            double degrees = Math.toDegrees(Math.atan(Math.abs(1.0 * dy / dx)));
            angle = 180 - (int) Math.round(degrees);
        } else if (dy < 0 && dx < 0) {
            double degrees = Math.toDegrees(Math.atan(Math.abs(1.0 * dy / dx)));
            angle = 180 + (int) Math.round(degrees);
        } else {
            double degrees = Math.toDegrees(Math.atan(Math.abs(1.0 * dy / dx)));
            angle = 360 - (int) Math.round(degrees);
        }
        return angle;
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
        double distance = Math.pow(x - x1, 2) + Math.pow(y - y1, 2) - r * r;
        return distance == 0 ? 0 : -(int) (distance / Math.abs(distance));
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
    }
}