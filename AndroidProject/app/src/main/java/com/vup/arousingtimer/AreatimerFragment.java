package com.vup.arousingtimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import petrov.kristiyan.colorpicker.ColorPicker;

import com.google.android.material.slider.Slider;

import static android.content.Context.MODE_PRIVATE;

public class AreatimerFragment extends Fragment {
    public static final String PREFERENCE = "com.vup.secondTimer";

    private static final String TAG = "AreatimerFragment";
    private static final int NOTIFICATION_ID = 1111;

    private Button btnOnTimer;
    private Button btnOffTimer;
    private Button btnColorPicker;
    private Slider sldEnlargeMinute;

    private ScreenStateReceiver screenStateReceiver;
    private boolean isServiceActivate = false;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private SharedPreferences colorPref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_areatimer, container, false);

        btnOnTimer = (Button)v.findViewById(R.id.btn_on_timer);
        btnOffTimer = (Button)v.findViewById(R.id.btn_off_timer);
        btnColorPicker = (Button)v.findViewById(R.id.btn_color_picker);
        sldEnlargeMinute = (Slider)v.findViewById(R.id.sld_enlarge_minute);

        initNotification();
        colorPref = this.getContext().getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        btnOnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service START");
                Log.i(TAG, "slider value : " + sldEnlargeMinute.getValue());
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                isServiceActivate = true;
                Intent intent = new Intent(getActivity(), OverlayTimerService.class);
                intent.putExtra("enlarge_speed", (int) sldEnlargeMinute.getValue());
                getActivity().startService(intent);
            }
        });

        btnOffTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service END");
                isServiceActivate = false;
                getActivity().stopService(new Intent(getActivity(), OverlayTimerService.class));
                NotificationManagerCompat.from(getContext()).cancel(NOTIFICATION_ID);
            }
        });

        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Time Picker");
                final ColorPicker colorPicker = new ColorPicker(getActivity());

                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        // put code
                        Log.i(TAG, "Color Selected " + color + "position : " + position);
                        SharedPreferences.Editor edit = colorPref.edit();
                        edit.putInt("recColor", color);
                        edit.apply();
                    }
                    @Override
                    public void onCancel(){
                        // put code
                    }}).show();
            }
        });

        setScreenStateMonitor();
        return v;
    }

    private void setScreenStateMonitor() {
        screenStateReceiver = new ScreenStateReceiver();
        screenStateReceiver.setScreenCallback(new ScreenCallback() {
            @Override
            public void screenOn() {
                Log.i(TAG, "screenOn");
                if(isServiceActivate)
                    getActivity().startService(new Intent(getContext(), OverlayTimerService.class));
            }

            @Override
            public void screenOff() {
                Log.i(TAG, "screenOff");
                if(isServiceActivate)
                    getActivity().stopService(new Intent(getContext(), OverlayTimerService.class));
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(screenStateReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(screenStateReceiver);
    }

    private void initNotification() {
        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = new NotificationCompat.Builder(getContext(), "arousing")
                .setSmallIcon(R.drawable.ic_area_timer)
                .setContentTitle("헬창 타이머")
                .setContentText("헬창 타이머 실행 중")
                .setContentIntent(pendingIntent);

        notificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("arousing", "channel", NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
