package com.example.uidemo.listviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    public static final String TAG = "MyViewPager";
    private int mLastX, mLastY;

    public MyViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
             mLastX = (int) ev.getX();
             mLastY = (int) ev.getY();
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            int dx = (int) (ev.getX() - mLastX);
            int dy = (int) (ev.getY() - mLastY);
            if(Math.abs(dx) > Math.abs(dy)){
                return true;
            }

        }
        return super.onInterceptTouchEvent(ev);
    }
}
