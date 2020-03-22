package com.vup.arousingtimer;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;

public class AreatimerFragment extends Fragment {
    final private String TAG = "AreatimerFragment";

    private Button btnOnTimer;
    private Button btnOffTimer;
    private Slider sldEnlargeMinute;

    private ScreenStateReceiver screenStateReceiver;
    private boolean isServiceActivate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_areatimer, container, false);


        btnOnTimer = (Button)v.findViewById(R.id.btn_on_timer);
        btnOffTimer = (Button)v.findViewById(R.id.btn_off_timer);
        sldEnlargeMinute = (Slider)v.findViewById(R.id.sld_enlarge_minute);

        btnOnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service START");
                Log.i(TAG, "slider value : " + sldEnlargeMinute.getValue());
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
}
