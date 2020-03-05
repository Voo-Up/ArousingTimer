package com.vup.arousingtimer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

public class OverlayTimerService extends Service {

    private OverlayTimerView overlayTimerView;
    private WindowManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        overlayTimerView = new OverlayTimerView(OverlayTimerService.this);
        Button btn = new Button(OverlayTimerService.this);
        btn.setText("TLQLKFD");

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(btn, mParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mManager != null) {        //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
            if(overlayTimerView != null) mManager.removeView(overlayTimerView);
        }
    }
}
