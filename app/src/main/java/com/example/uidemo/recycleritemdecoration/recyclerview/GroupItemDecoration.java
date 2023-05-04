package com.example.uidemo.recycleritemdecoration.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GroupItemDecoration extends RecyclerView.ItemDecoration {

    public static final String TAG = "GroupItemDecoration";

    private int headerHeight;

    private Paint headPaint;
    private Paint headOverPaint;
    private Paint drawTextPaint;
    private Paint drawOverTextPaint;

    private Rect textRect;

    public GroupItemDecoration(Context context) {
        // 顶部吸顶栏的高度
        headerHeight = dp2px(context, 50);

        // 每一组的头部的Paint
        headPaint = new Paint();
        headPaint.setColor(Color.RED);

        headOverPaint = new Paint();
        headOverPaint.setColor(Color.YELLOW);

        drawTextPaint = new Paint();
        drawTextPaint.setTextSize(50);
        drawTextPaint.setColor(Color.BLACK);

        drawOverTextPaint = new Paint();
        drawOverTextPaint.setTextSize(50);
        drawOverTextPaint.setColor(Color.BLUE);

        textRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if(parent.getAdapter() instanceof DataAdapter){
            DataAdapter adapter = (DataAdapter) parent.getAdapter();

            // 获取当前屏幕上的item数目：
            int count = parent.getChildCount();

            // 实现itemView的宽度和分割线的宽度一样
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);
                if(view.getTop() - headerHeight - parent.getPaddingTop() >= 0){
                    //获取当前Item位置
                    int position = parent.getChildLayoutPosition(view);
                    //判断item是否为头部：
                    boolean isGroupHeader = adapter.isFirstItemOfGroup(position);
                    if(isGroupHeader){
                        c.drawRect(left, view.getTop() - headerHeight, right, view.getTop(), headPaint);
                        String groupName = adapter.getGroupName(position);
                        drawTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                        c.drawText(groupName, left + 20, view.getTop() - headerHeight / 2 + textRect.height() / 2, drawTextPaint);
                    }else{   //普通itemView的分割线
                        c.drawRect(left, view.getTop() - 4, right, view.getTop(), headPaint);
                    }

                }

            }

        }
    }

    //下面这个是要绘制吸顶的组标
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(parent.getAdapter() instanceof DataAdapter) {
            DataAdapter adapter = (DataAdapter) parent.getAdapter();

            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();

            int firstVisibleItemPosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            View itemView = parent.findViewHolderForAdapterPosition(firstVisibleItemPosition).itemView;

            boolean isFirstItemOfGroup = adapter.isFirstItemOfGroup(firstVisibleItemPosition + 1);
            if(isFirstItemOfGroup){
                int bottom = Math.min(top + headerHeight, itemView.getBottom());  //这一步是为了把第一个header顶上去
                c.drawRect(left, top, right, bottom, headOverPaint);
                String groupName = adapter.getGroupName(firstVisibleItemPosition);
                drawOverTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);

                c.clipRect(left, top, right, bottom);

                c.drawText(groupName, left + 20,
                        bottom - headerHeight / 2 + textRect.height() / 2, drawOverTextPaint);
            }else{
                c.drawRect(left, top, right, top + headerHeight, headOverPaint);
                String groupName = adapter.getGroupName(firstVisibleItemPosition);
                drawTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                // 绘制文字
                c.drawText(groupName, left + 20,
                        top + headerHeight / 2 + textRect.height() / 2, drawOverTextPaint);
            }

        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() instanceof DataAdapter) {
            DataAdapter adapter = (DataAdapter) parent.getAdapter();
            // 当前Item的位置
            int position = parent.getChildLayoutPosition(view);

            // 如何判断 item 是头部
            boolean isGroupHeader = adapter.isFirstItemOfGroup(position);
            // 是第一个
            if (isGroupHeader) {
                outRect.set(0, headerHeight, 0, 0);
            } else {
                // padding，margin
                outRect.set(0, 4, 0, 0);
            }
        }
    }

    private int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getResources().getDisplayMetrics());
    }
}
