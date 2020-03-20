package com.vup.arousingtimer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AreatimerFragment extends Fragment {
    final private String TAG = "AreatimerFragment";

    private Button btnOnTimer;
    private Button btnOffTimer;
    private boolean isServiceActivate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_areatimer, container, false);


        btnOnTimer = (Button)v.findViewById(R.id.btn_on_timer);
        btnOffTimer = (Button)v.findViewById(R.id.btn_off_timer);

        btnOnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service START");
                isServiceActivate = true;
                getActivity().startService(new Intent(getActivity(), OverlayTimerService.class));
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

        return v;
    }
}
