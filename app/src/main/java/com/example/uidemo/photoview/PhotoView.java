package com.example.uidemo.photoview;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.example.uidemo.R;

public class PhotoView extends View {

    public static final String TAG = "PhotoView";
    private Bitmap mBitmap;
    private float originalOffsetX;
    private float originalOffsetY;
    private Paint mPaint;
    private float smallScale;
    private float bigScale;
    private float OVER_SCALE_FACTOR = 1.5f; //留出放大间隙
    private float currentScale;
    private boolean isEnlarge;
    private GestureDetector mGestureDetector;
    private ObjectAnimator scaleAnimator;
    private boolean isScale;

    private float offsetX;
    private float offsetY;
    private OverScroller overScroller;
    private FlingRunner flingRunner;
    private ScaleGestureDetector scaleGestureDetector;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo);

        mPaint = new Paint();

        mGestureDetector = new GestureDetector(context, new PhotoGestureListener());

        overScroller = new OverScroller(context);

        flingRunner = new FlingRunner();

        scaleGestureDetector = new ScaleGestureDetector(context, new PhotoScaleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 双指操作优先
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            result = mGestureDetector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);

        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);

        canvas.drawBitmap(mBitmap, originalOffsetX, originalOffsetY, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - mBitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - mBitmap.getHeight()) / 2f;

        if((float) mBitmap.getWidth() / mBitmap.getHeight() > (float) getWidth() / getHeight()){
             smallScale = (float) getWidth() / mBitmap.getWidth();
             bigScale = (float) getHeight() / mBitmap.getHeight() * OVER_SCALE_FACTOR;
        }else{
            smallScale = (float) getHeight() / mBitmap.getHeight();
            bigScale = (float) getWidth() / mBitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    class PhotoGestureListener extends GestureDetector.SimpleOnGestureListener{

        //Up时触发，单击抬手，双击第二次抬起时触发，只会触发一次
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e(TAG, "onSingleTapUp: " );
            return super.onSingleTapUp(e);
        }

        //长按：默认300ms
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.e(TAG, "onLongPress: " );
        }

        /**
         * @param e1
         * @param e2
         * @param distanceX  在X轴上滑过的距离(单位时间)
         * @param distanceY
         * @return
         */
        //类似move事件；
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            // 只有在放大的情况下，才能进行移动
            if (isEnlarge) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //急速滑动后触发
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isEnlarge) {
                // 只会处理一次
                overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (mBitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (mBitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (mBitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (mBitmap.getHeight() * bigScale - getHeight()) / 2, 200, 200);
                postOnAnimation(flingRunner);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        //延时触发  100ms 处理点击效果，水波纹
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            Log.e(TAG, "onShowPress: ");
        }

        //按下时触发
        @Override
        public boolean onDown(MotionEvent e) {
            //return super.onDown(e);
            Log.e(TAG, "onDown: ");
            return true;  //如果想让按下后后续事件有效果，此处必须消费
        }

        //双击触发，第二次点击按下时触发   40ms-300ms内第二次点击   40ms内可能是手指抖动
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isEnlarge = !isEnlarge;
            new RuntimeException("onDoubleTap").printStackTrace();
            if(isEnlarge){
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
                fixOffsets();
                // 启动属性动画
                getObjectAnimator().start();
            }else{
                getObjectAnimator().reverse();
            }

            return super.onDoubleTap(e);
        }

        //双击第二次down、move、up都会触发
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        //单击按下时触发，双击时不触发。延时300ms触发的TAP事件
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e(TAG, "onSingleTapConfirmed: ");
            return super.onSingleTapConfirmed(e);
        }
    }

    private void fixOffsets() {
        offsetX = Math.min(offsetX, (mBitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(mBitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (mBitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(mBitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private ObjectAnimator getObjectAnimator(){
        if(scaleAnimator == null){
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }

        if (isScale) {   //是否进行了双指放大
            isScale = false;
            scaleAnimator.setFloatValues(smallScale, currentScale);
        } else {
//             放大缩小的范围
            scaleAnimator.setFloatValues(smallScale, bigScale);
        }

        return scaleAnimator;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    class FlingRunner implements Runnable {
        @Override
        public void run() {
            // 动画还在执行 则返回true
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                // 没帧动画执行一次，性能更好
                postOnAnimation(this);
            }
        }
    }

    class PhotoScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if ((currentScale > smallScale && !isEnlarge)
                    || (currentScale == smallScale && isEnlarge)) {
                isEnlarge = !isEnlarge;
            }
            currentScale = initialScale * detector.getScaleFactor();
            if(currentScale < smallScale){
                currentScale = smallScale;
            }else if(currentScale > bigScale){
                currentScale = bigScale;
            }
            isScale = true;
            invalidate();
            return false;
        }

        // 注意：返回true，消费事件
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
