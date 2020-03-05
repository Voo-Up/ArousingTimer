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

    Button btnOnTimer;
    Button btnOffTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
