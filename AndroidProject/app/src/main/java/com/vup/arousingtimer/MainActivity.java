package com.vup.arousingtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements ScreenCallback {
    final private String TAG = "MainActivity";
    final private int REQ_CODE_OVERLAY_PERMISSION = 0;

    private AdView mAdView;
    private Button btnOnTimer;
    private Button btnOffTimer;
    private boolean isServiceActivate = false;

    private ScreenStateReceiver screenStateReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION);
        }
        setContentView(R.layout.activity_main);
        loadAdmob();
        setScreenStateMonitor();

        btnOnTimer = (Button)findViewById(R.id.btn_on_timer);
        btnOffTimer = (Button)findViewById(R.id.btn_off_timer);

        btnOnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service START");
                isServiceActivate = true;
                startService(new Intent(getApplicationContext(), OverlayTimerService.class));
            }
        });

        btnOffTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service END");
                isServiceActivate = false;
                stopService(new Intent(getApplicationContext(), OverlayTimerService.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenStateReceiver);
    }

    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_OVERLAY_PERMISSION) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    return;
                } else {
                    finish();
                }
            }
        }
    }

    public void startOverlayTimerService() {
        Log.i(TAG, "Service START");
        startService(new Intent(getApplicationContext(), OverlayTimerService.class));
    }

    public void stopOverlayTimerService() {
        Log.i(TAG, "Service END");
        stopService(new Intent(getApplicationContext(), OverlayTimerService.class));
    }
    private void setScreenStateMonitor() {
        screenStateReceiver = new ScreenStateReceiver();
        screenStateReceiver.setScreenCallback(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenStateReceiver, filter);
    }

    private void loadAdmob() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdFailedToLoad " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {

                // Code to be executed when the user is about to return

                // to the app after tapping on an ad.

            }

        });
    }

    @Override
    public void screenOn() {
        Log.i(TAG, "screenOn");
        if(isServiceActivate)
            startService(new Intent(getApplicationContext(), OverlayTimerService.class));
    }

    @Override
    public void screenOff() {
        Log.i(TAG, "screenOff");
        if(isServiceActivate)
            stopService(new Intent(getApplicationContext(), OverlayTimerService.class));
    }
}
