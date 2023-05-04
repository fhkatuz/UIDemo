package com.example.uidemo.nestedscrollview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class NewNestedScrollview extends NestedScrollView {

    public static final String TAG = "NewNestedScrollview";

    public NewNestedScrollview(@NonNull Context context) {
        this(context, null);
    }

    public NewNestedScrollview(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewNestedScrollview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    //处理上滑HeaderView时，RecyclerView一起上滑
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int headerViewHeight = ((LinearLayoutCompat)getChildAt(0)).getChildAt(0).getMeasuredHeight();
        // 向上滑动。若当前bannerview可见，需要将bannervie滑动至不可见
        boolean isNeedToHideTop = dy > 0 && getScrollY() < headerViewHeight;
        if (isNeedToHideTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY > 0) {
            ViewPager2 viewPager2 = getChildView(this, ViewPager2.class);
            if(viewPager2 != null) {
                // In this project configuration, ViewPager2 has only one child RecyclerViewImpl,
                // and RecyclerViewImpl has only one FrameLayout, at last,
                // FrameLayout has only one RecyclerView.
                RecyclerView childRecyclerView = getChildView(((ViewGroup)viewPager2.getChildAt(0)), RecyclerView.class);
                if (childRecyclerView != null) {
                    childRecyclerView.fling(0, velocityY);
                }
            }
        }
    }

    private <T> T getChildView(View viewGroup, Class<T> targetClass) {
        if (viewGroup != null && viewGroup.getClass() == targetClass) {
            return (T) viewGroup;
        }

        if(viewGroup instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
                View view = ((ViewGroup) viewGroup).getChildAt(i);
                if (view instanceof ViewGroup) {
                    T result = getChildView(view, targetClass);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
