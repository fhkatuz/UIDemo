package com.example.uidemo.flowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlowLayout extends ViewGroup {

    public static final String TAG = "FlowLayout";

    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item纵向间距

    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于layout

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void clearMeasureParams(){
        allLines.clear();
        lineHeights.clear();
    }

    public void notifyDatas(List<String>  datas){
        removeAllViews();
        for (String data : datas) {
            final TextView textView = new TextView(getContext());
            textView.setTextSize(16);
            textView.setGravity(Gravity.LEFT);
            textView.setText(data);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20,10, 20, 10);

            Random rand = new Random();
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(30);
            drawable.setStroke(2, Color.parseColor("#cccccc"));
            drawable.setColor(Color.rgb(r, g, b));
            textView.setBackground(drawable);

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(getContext(), data + "条目被移除了", Toast.LENGTH_SHORT).show();
                    datas.remove(data);
                    notifyDatas(datas);
                    return true;
                }
            });

            addView(textView);
        }
    }

    //度量：基本都是先度量孩子再自己
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //为了防止父View多次测量：
        clearMeasureParams();

        //先度量孩子：
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        List<View> lineList = new ArrayList<>();
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int parentNeededWidth = 0;  // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededHeight = 0; // measure过程中，子View要求的父ViewGroup的高

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            if(childView.getVisibility() != View.GONE) {
                //获取属性值：
//                LayoutParams layoutParams = childView.getLayoutParams();
//
//                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
//                        layoutParams.width);
//                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
//                        layoutParams.height);
//                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

//                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                //获取子View的度量宽高
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();

                //如果需要换行
                if (childWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                    allLines.add(lineList);
                    lineHeights.add(lineHeight);

                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                    lineList = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }

                lineList.add(childView);
                lineWidthUsed = lineWidthUsed + childWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childHeight);

                //处理最后一行数据：
                if (i == childCount - 1) {
                    allLines.add(lineList);
                    lineHeights.add(lineHeight);

                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                }
            }
        }

        //再度量自己,保存
        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth: parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ?selfHeight: parentNeededHeight;
        setMeasuredDimension(realWidth, realHeight);

    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    //布局
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int lineCount = allLines.size();

        //起点位置：
        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        for (int j = 0; j < lineCount; j++) {
            List<View> views = allLines.get(j);
            int lineHeight = lineHeights.get(j);
            for (int x = 0; x < views.size(); x++) {
                View view = views.get(x);
                int left = curL;
                int top = curT;

                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();

                view.layout(left, top, right, bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT  + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }
    }
}


























