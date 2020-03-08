package com.vup.arousingtimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class OverlayTimerView extends View {
    final private String TAG = "OverlayTimerView";
    public OverlayTimerView(Context context) {
        super(context);
    }

    public OverlayTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 페인트 객체 생성 + 안티에일리어싱
        paint.setColor(Color.RED); // 빨간색으로 설정
        paint.setAlpha(50); // 투명도 추가해봄
        canvas.drawRect(100, 100, 400, 400, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "TOUCH DOWN");
                break;
        }
        return super.onTouchEvent(event);
    }
}
