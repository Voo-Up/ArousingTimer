package com.vup.arousingtimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class OverlayTimerView extends View{
    final private String TAG = "OverlayTimerView";
    private int thickness = 0;
    private int minute = 1;
    private float enlargeStep = 10;

    private int screenWidth;
    private int screenHeight;

    public OverlayTimerView(Context context) {
        super(context);
        Point size = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;
    }

    public OverlayTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        Log.i(TAG, "Set Minute = " + minute);
        this.minute = minute;
        this.enlargeStep = (float)screenWidth / 60 / minute;
        Log.i(TAG, "Enlarge Step = " + this.enlargeStep);
    }
    public float getEnlargeStep() {
        return enlargeStep;
    }

    public int getThickness() {
        return thickness;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 페인트 객체 생성 + 안티에일리어싱
        paint.setColor(getResources().getColor(R.color.primaryColor)); // 빨간색으로 설정
        paint.setAlpha(95); // 투명도 추가해봄
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        paint.setFilterBitmap(true);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    public boolean nextEnlargeStep(){
        this.thickness += this.enlargeStep;
        if(this.thickness > screenWidth){
            return false;
        }
        return true;
    }
}
