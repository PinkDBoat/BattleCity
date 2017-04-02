package com.jay.battlecity.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.helloworld.socketlib.bean.TransmissionData;
import com.helloworld.socketlib.client.Client;
import com.helloworld.socketlib.client.OnDataReceivedListener;
import com.jay.battlecity.R;
import com.jay.battlecity.game.TestClient;
import com.jay.battlecity.service.GameService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        //test code
        startService(new Intent(MainActivity.this, GameService.class));
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(1500);
                        new TestClient(new Client("localhost", 2333, new OnDataReceivedListener() {
                            @Override
                            public void onDataReceived(Map<InetAddress, TransmissionData> data) {
                                Log.d(TAG, "onDataReceived: " + data);
                            }

                            @Override
                            public void onStatus(byte status) {
                                Log.d(TAG, "onStatus: " + status);
                            }
                        }), "PinkD").test();
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}