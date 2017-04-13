package com.jay.battlecity.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * 可展开菜单项的按钮
 * Created by Jay on 2017/4/12.
 */

public class MenuButton extends RelativeLayout {
    private ImageButton mImageButton;

    public MenuButton(@NonNull Context context) {
        super(context);
    }

    public MenuButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
