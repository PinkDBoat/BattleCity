package com.jay.battlecity.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.jay.battlecity.R;
import com.jay.battlecity.utils.CalcUtils;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class TankView extends View  {
    //触摸移动测试用
    private int currentX ;
    private int currentY ;

    private int dpLength;
    private int dpHigh;
    private Context context;
    private int direction = 0;
    private Bitmap tankBitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.tank_white)).getBitmap();
    private int viewX = 0;
    private int viewY = 0;

    public TankView(Context context, int dpLength, int dpHigh) {
        super(context);
        this.context = context;
        this.dpHigh = dpHigh;
        this.dpLength = dpLength;
    }

    public TankView(Context context, AttributeSet attrs, int dpLength, int dpHigh) {
        super(context, attrs);
        this.context = context;
        this.dpHigh = dpHigh;
        this.dpLength = dpLength;
    }

    public TankView(Context context, AttributeSet attrs, int defStyleAttr, int dpLength, int dpHigh) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.dpHigh = dpHigh;
        this.dpLength = dpLength;
    }

    /*
    * 三个基本方法 设置起始XY（默认为0,0），设置方向(默认为0)，设置坦克样式（默认为tank_white）
     */
    public void setStartXY(int X, int Y){
        this.viewX = X;
        this.viewY = Y;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public void setTankType(int resource){
        tankBitmap = ((BitmapDrawable)getResources().getDrawable(resource)).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(0, 0, CalcUtils.dp2px(dpLength), CalcUtils.dp2px(dpHigh));
        //设置转动中心
        canvas.rotate(direction, viewX+CalcUtils.dp2px(dpLength)/2, viewY+CalcUtils.dp2px(dpHigh)/2);
        //设置起始位置
        canvas.translate(viewX, viewY);
        //设置坦克样式
        canvas.drawBitmap(tankBitmap, null, rect, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*  触摸移动测试用
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                Log.d("TankView","down");
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                direction++;
                int x2 = (int) event.getRawX();
                int y2 = (int) event.getRawY();
                TankView.this.scrollBy(currentX - x2 , currentY - y2);
                currentX = x2;
                currentY = y2;
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                Log.d("TankView","up");
                break;
            }
        }
        return true;
    }*/
}
