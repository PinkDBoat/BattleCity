package com.jay.battlecity;

import android.app.Application;

import com.jay.battlecity.utils.MathUtils;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MathUtils.init(getResources().getDisplayMetrics().densityDpi);
    }
}
