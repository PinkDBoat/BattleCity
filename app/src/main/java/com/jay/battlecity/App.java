package com.jay.battlecity;

import android.app.Application;

import com.jay.battlecity.utils.CalcUtils;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalcUtils.init(getResources().getDisplayMetrics().densityDpi);
    }
}
