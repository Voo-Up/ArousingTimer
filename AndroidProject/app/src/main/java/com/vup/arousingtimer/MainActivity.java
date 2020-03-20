package com.vup.arousingtimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ScreenCallback {
    final private String TAG = "MainActivity";
    final private int REQ_CODE_OVERLAY_PERMISSION = 0;

    private AdView mAdView;

    private boolean isServiceActivate = false;
    private ScreenStateReceiver screenStateReceiver;

    private ViewPager vpContainer;
    private BottomNavigationView botnavMain;
    private MenuItem prevBottomNavigationItem;

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

        vpContainer = (ViewPager)findViewById(R.id.container);
        botnavMain = (BottomNavigationView)findViewById(R.id.botnav_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 3);
        vpContainer.setAdapter(sectionsPagerAdapter);
        vpContainer.setCurrentItem(0);
        prevBottomNavigationItem = botnavMain.getMenu().getItem(0);
        botnavMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itm_areatimer:
                        Log.i(TAG, "Select Item 1");
                        vpContainer.setCurrentItem(0);
                        return true;
                    case R.id.itm_manual:
                        Log.i(TAG, "Select Item 2");
                        vpContainer.setCurrentItem(1);
                        return true;
                    case R.id.itm_another:
                        Log.i(TAG, "Select Item 3");
                        vpContainer.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });

        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevBottomNavigationItem != null) {
                    prevBottomNavigationItem.setChecked(false);
                }
                prevBottomNavigationItem = botnavMain.getMenu().getItem(position);
                prevBottomNavigationItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
