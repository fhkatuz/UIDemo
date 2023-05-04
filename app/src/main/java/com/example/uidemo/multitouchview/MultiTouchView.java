package com.example.uidemo.multitouchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.uidemo.R;

public class MultiTouchView extends View {

    public static final String TAG = "MultiTouchView";
    private Bitmap mBitmap;
    private Paint mPaint;
    private float mDownX;
    private float mDownY;
    private float mOffsetX;
    private float mOffsetY;
    private float mLastOffsetX;
    private float mLastOffsetY;
    private int currentPointId;

    public MultiTouchView(Context context) {
        this(context, null);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mOffsetX, mOffsetY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                currentPointId = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(currentPointId);
                mOffsetX = mLastOffsetX + event.getX(pointerIndex) - mDownX;
                mOffsetY = mLastOffsetY + event.getY(pointerIndex) - mDownY;

                invalidate();  //一定要触发重绘才会有效果
                break;
            case MotionEvent.ACTION_UP:
                mLastOffsetX = mOffsetX;
                mLastOffsetY = mOffsetY;
                break;
            // 非第一根手指按下，触发
            case MotionEvent.ACTION_POINTER_DOWN:
                // 获取index值
                int actionIndex = event.getActionIndex();
                currentPointId = event.getPointerId(actionIndex);

                mDownX = event.getX(actionIndex);
                mDownY = event.getY(actionIndex);
                mLastOffsetX = mOffsetX;
                mLastOffsetY = mOffsetY;
                break;
            // 非最后一根手指抬起，触发
            case MotionEvent.ACTION_POINTER_UP:
                // 获取抬起手指的 index
                int upIndex = event.getActionIndex();
                // 获取抬起手指的 id
                int pointerId = event.getPointerId(upIndex);
                if (pointerId == currentPointId) {
                    // 如果抬起的手指是最后一根手指，id是不是最后一个
                    if (upIndex == event.getPointerCount() - 1) {
                        upIndex = event.getPointerCount() - 2;
                    } else {
                        upIndex++;
                    }
                    currentPointId = event.getPointerId(upIndex);
                    mDownX = event.getX(upIndex);
                    mDownY = event.getY(upIndex);
                    mLastOffsetX = mOffsetX;
                    mLastOffsetY = mOffsetY;
                }
                break;
        }

        return true;
    }
}
