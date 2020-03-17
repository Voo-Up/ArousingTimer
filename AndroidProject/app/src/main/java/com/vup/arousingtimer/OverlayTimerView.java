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

public class OverlayTimerView extends View{
    final private String TAG = "OverlayTimerView";
    private int mThickness = 100;
    public OverlayTimerView(Context context) {
        super(context);
    }

    public OverlayTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int getmThickness() {
        return mThickness;
    }

    public void setmThickness(int mThickness) {
        this.mThickness = mThickness;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 페인트 객체 생성 + 안티에일리어싱
        paint.setColor(getResources().getColor(R.color.primaryColor)); // 빨간색으로 설정
        paint.setAlpha(80); // 투명도 추가해봄
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mThickness);
        paint.setFilterBitmap(true);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    public void renewThickness(int thicnkess){
        setmThickness(thicnkess);
        invalidate();
    }

}
