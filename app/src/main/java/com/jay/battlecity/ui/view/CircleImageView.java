package com.jay.battlecity.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.jay.battlecity.R;
import com.jay.battlecity.utils.CalcUtils;

/**
 * 圆形头像
 * Created by Jay on 2017/1/13.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint = new Paint();
    private int mCircleColor;
    private int mCircleSize;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            mCircleColor = typedArray.getColor(R.styleable.CircleImageView_circle_color, Color.BLACK);
            mCircleSize = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_circle_size, dp2px(2));
            typedArray.recycle();
        } else {
            mCircleColor = Color.BLACK;
            mCircleSize = dp2px(2);
        }
    }

    private Bitmap scaleBitmap(Bitmap bitmap) {
        float sw = (float) (getWidth() - 2 * mCircleSize) / bitmap.getWidth();
        float sh = (float) (getHeight() - 2 * mCircleSize) / bitmap.getHeight();
        float scale = Math.max(sw, sh);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap rawBitmap = ((BitmapDrawable) drawable).getBitmap();

            Bitmap newBitmap = scaleBitmap(rawBitmap);
            Bitmap circleBitmap = getCircleBitmap(newBitmap);

            mPaint.reset();
            canvas.drawBitmap(circleBitmap, mCircleSize, mCircleSize, mPaint);
            mPaint.setColor(mCircleColor);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(mCircleSize);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getPivotX(), getPivotY(), circleBitmap.getWidth() / 2, mPaint);
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(circleBitmap);

        mPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int radius = bitmap.getWidth() / 2;
        canvas.drawCircle(radius, radius, radius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        return circleBitmap;
    }

    private int dp2px(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }
}
