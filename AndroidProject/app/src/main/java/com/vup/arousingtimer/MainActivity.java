package com.vup.arousingtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final private String TAG = "MainActivity";

    final private int REQ_CODE_OVERLAY_PERMISSION = 0;
    Button btnOnTimer;
    Button btnOffTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION);
        }

        setContentView(R.layout.activity_main);

        btnOnTimer = (Button)findViewById(R.id.btn_on_timer);
        btnOffTimer = (Button)findViewById(R.id.btn_off_timer);

        btnOnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service START");
                startService(new Intent(getApplicationContext(), OverlayTimerService.class));
            }
        });

        btnOffTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service END");
                stopService(new Intent(getApplicationContext(), OverlayTimerService.class));
            }
        });
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
}
