package com.vup.arousingtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenStateReceiver extends BroadcastReceiver {
    private final String TAG = "ScreenStateReceiver";
    private ScreenCallback screenCallback = null;

    public void setScreenCallback(ScreenCallback screenCallback) {
        this.screenCallback = screenCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive : " +action);

        if(action.equals(intent.ACTION_SCREEN_ON)) {
            if(screenCallback!=null)
                screenCallback.screenOn();
        } else if(action.equals(intent.ACTION_SCREEN_OFF)) {
            if(screenCallback!=null)
                screenCallback.screenOff();
        }
    }
}
