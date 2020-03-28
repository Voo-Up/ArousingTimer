package com.vup.arousingtimer;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class OverlayTimerService extends Service {
    final private String TAG = "OverlayTimerService";
    private OverlayTimerView overlayTimerView;
    private WindowManager mManager;
    private WindowManager.LayoutParams mParams;

    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        overlayTimerView = new OverlayTimerView(OverlayTimerService.this);

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, //이 타입으로 생성해야 함
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE // OverlayTimerView 이외에 터치 가능한 flag
                        |WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(overlayTimerView, mParams);

        //changeScreenBrightness(10);

        // Param is optional, to run task on UI thread.
        mHandler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Do the task...
                Log.i(TAG, "every 1000");
                mHandler.postDelayed(this, 1000); // Optional, to repeat the task.
                if(overlayTimerView.nextEnlargeStep()) {
                    overlayTimerView.invalidate();
                }
                else{
                    Log.i(TAG, "full screen");
                    mHandler.removeCallbacks(this);
                }
            }
        };
        mHandler.postDelayed(runnable, 1000);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int enlargeSpeed = intent.getIntExtra("enlarge_speed", 1);
        overlayTimerView.setMinute(enlargeSpeed);
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
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void changeScreenBrightness(int brightValue) {
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightValue);
    }
}
