package com.example.fishdrawable;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FishDrawable extends Drawable {

    public static final String TAG = "FishDrawable";
    private Path mPath;
    private Paint mPaint;

    // 身体之外的部分的透明度
    private final static int OTHER_ALPHA = 110;
    // 身体的透明度
    private final static int BODY_ALPHA = 160;

    private float fishMainAngle = 90;

    private PointF middlePoint;

    public final static float HEAD_RADIUS = 50;

    public final static float EYE_RADIUS = (float) (0.1 * HEAD_RADIUS);

    // 鱼身的长度
    private final static float BODY_LENGTH = 3.2f * HEAD_RADIUS;

    //鱼鳍相关数据：
    private final static float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    private final static float FINS_LENGTH = 1.3f * HEAD_RADIUS;

    // 尾部大圆的半径(圆心就是身体底部的中点)
    private final float BIG_CIRCLE_RADIUS = HEAD_RADIUS * 0.7f;
    // 尾部中圆的半径
    private final float MIDDLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS * 0.6f;
    // 尾部小圆的半径
    private final float SMALL_CIRCLE_RADIUS = MIDDLE_CIRCLE_RADIUS * 0.4f;

    // --寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS + MIDDLE_CIRCLE_RADIUS;
    // --寻找尾部小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --寻找大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;

    private PointF headPoint;

    private float frequence = 1f;

    float currentValue = 0;

    boolean isFishMove = false;

    public FishDrawable() {
        init();
    }

    private void init() {
        mPath = new Path();// 路径
        mPaint = new Paint();// 画笔
        mPaint.setStyle(Paint.Style.FILL);// 画笔类型，填充
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);// 设置颜色
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 防抖

        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);
        // 1.2  1.5 ==》 10
        // 1.2*n = 整数 1.5 *n = 整数 ==》 公倍数
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 3600f);
        // 动画周期
        valueAnimator.setDuration(15 * 1000);
        // 重复的模式：重新开始
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        // 重复的次数
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                currentValue = (float) animator.getAnimatedValue();

                invalidateSelf();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // sin(currentValue*2) ==> 0-3600 1s, 10次，20次
        float fishAngle = (float) (fishMainAngle + Math.sin(Math.toRadians(currentValue * 1.2 * frequence)) * 10);

        // 绘制鱼头
        headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        //绘制鱼眼睛：鱼眼睛在鱼头上，可以稍稍往里一点
        PointF eyeRightPoint = calculatePoint(headPoint, HEAD_RADIUS - 5, fishAngle - 30);  //右眼
        canvas.drawCircle(eyeRightPoint.x, eyeRightPoint.y, EYE_RADIUS, mPaint);

        PointF eyeLeftPoint = calculatePoint(headPoint, HEAD_RADIUS - 5, fishAngle + 30);  //左眼
        canvas.drawCircle(eyeLeftPoint.x, eyeLeftPoint.y, EYE_RADIUS, mPaint);

        // 鱼右鳍
        PointF rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        drawFins(canvas, rightFinsPoint, fishAngle, true);

        //鱼左鳍：
        PointF leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        drawFins(canvas, leftFinsPoint, fishAngle, false);

        // 身体的底部的中心点
        PointF bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180);
        // 绘制节肢1
        PointF middleCircleCenterPoint = drawSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS,
                FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);

        // 绘制节肢2：是没有大圆的
        //找到节肢1短底边中心点：
        //PointF middleCircleCenterPoint = calculatePoint(bodyBottomCenterPoint, FIND_MIDDLE_CIRCLE_LENGTH , fishAngle - 180);
        drawSegment(canvas, middleCircleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);

        float findEdgeLength = (float) Math.abs(Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * BIG_CIRCLE_RADIUS);

        // 绘制大三角形
        drawTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH,
                findEdgeLength, fishAngle);
        // 绘制小三角形
        drawTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH - SMALL_CIRCLE_RADIUS,
                findEdgeLength - MIDDLE_CIRCLE_RADIUS, fishAngle);

        // 画身体
        drawBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle);
    }

    /**
     * 求对应点的坐标 -- 知道起始点，知道鱼头的角度，知道两点间的距离，就可以算出想要的点的坐标
     *
     * @param startPoint 起始点的坐标
     * @param length     两点间的长度
     * @param angle      鱼头相对于x坐标的角度
     * @return
     */
    public static PointF calculatePoint(PointF startPoint, float length, float angle) {
        // angle 角度（0度~360度）  三角函数 -- 弧度
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        float deltaY = (float) (-Math.sin(Math.toRadians(angle)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    /**
     * 绘制鱼鳍
     *
     * @param startPoint  起始点的坐标
     * @param fishAngle   鱼头相对于x坐标的角度
     * @param isRightFins
     */
    private void drawFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRightFins) {

        float controlAngle = isFishMove ? (float) (80 + Math.cos(Math.toRadians(currentValue * 1.0 * frequence)) * 35) : 115;

        // 结束点
        PointF endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        // 控制点
        PointF controlPoint = calculatePoint(startPoint, 1.8f * FINS_LENGTH,
                isRightFins ? fishAngle - controlAngle : fishAngle + controlAngle);

        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        // 二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);

        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 画节肢
     * @param bottomCenterPoint  梯形底部的中心点坐标（长边）
     * @param bigRadius 大圆的半径
     * @param smallRadius 小圆的半径
     * @param findSmallCircleLength 寻找梯形小圆的线长
     * @param hasBigCircle 是否有大圆
     */
    private PointF drawSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius,
                             float smallRadius, float findSmallCircleLength, float fishAngle,
                             boolean hasBigCircle) {

        float segmentAngle;
        if (hasBigCircle) {
            // 节肢1
            segmentAngle = (float) (fishAngle + Math.cos(Math.toRadians(currentValue * 1.5 * frequence)) * 15);
        } else {
            // 节肢2
            segmentAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35);
        }

        // 梯形短底边的中心点（短边）
        PointF upperCenterPoint = calculatePoint(bottomCenterPoint, findSmallCircleLength,
                segmentAngle - 180);
        // 梯形的四个顶点
        PointF bottomLeftPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle + 90);
        PointF bottomRightPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle - 90);
        PointF upperLeftPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle + 90);
        PointF upperRightPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle - 90);

        if(hasBigCircle){
            // 绘制大圆
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }
        // 绘制小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);

        // 绘制梯形
        mPath.reset();
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        canvas.drawPath(mPath, mPaint);

        return upperCenterPoint;
    }

    /**
     * 画三角形
     * @param findCenterLength 顶点到底部的垂直线长
     * @param findEdgeLength 底部一半
     */
    private void drawTriangle(Canvas canvas, PointF startPoint,
                              float findCenterLength, float findEdgeLength, float fishAngle) {

        float triangelAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35);
        // 底部中心点的坐标
        PointF centerPoint = calculatePoint(startPoint, findCenterLength, triangelAngle - 180);
        // 三角形底部两个点
        PointF leftPoint = calculatePoint(centerPoint, findEdgeLength, triangelAngle + 90);
        PointF rightPoint = calculatePoint(centerPoint, findEdgeLength, triangelAngle - 90);

        // 绘制三角形
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param headPoint  鱼头圆心
     * @param bodyBottomCenterPoint 鱼节肢大圆圆心
     * @param fishAngle
     */
    private void drawBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint, float fishAngle) {
       //首先获取身体四个点：
        PointF rightTopPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF leftTopPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF rightBottomPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90);
        PointF leftBottomPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90);

        // 二阶贝塞尔曲线的控制点
        PointF controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle + 130);
        PointF controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle - 130);

        mPath.reset();
        mPath.moveTo(rightTopPoint.x, rightTopPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, rightBottomPoint.x, rightBottomPoint.y);
        mPath.lineTo(leftBottomPoint.x, leftBottomPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, leftTopPoint.x, leftTopPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    // 设置透明度
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    // 设置颜色过滤器
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        // 只有绘制的地方才覆盖底下的内容
        return PixelFormat.TRANSLUCENT;
    }

    // 如果ImageView的宽高为wrap_content,则获取这个值
    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public PointF getHeadPoint() {
        return headPoint;
    }

    public float getHEAD_RADIUS() {
        return HEAD_RADIUS;
    }

    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }

    public void setFishMainAngle(float fishMainAngle) {
        this.fishMainAngle = fishMainAngle;
    }

    public boolean isFishMove() {
        return isFishMove;
    }

    public void setFishMove(boolean fishMove) {
        isFishMove = fishMove;
    }

}
