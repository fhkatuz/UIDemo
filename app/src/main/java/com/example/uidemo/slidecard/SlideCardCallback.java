package com.example.uidemo.slidecard;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidemo.slidecard.recyclerview.SlideCardAdapter;

import java.util.List;

public class SlideCardCallback extends ItemTouchHelper.SimpleCallback {

    public static final String TAG = "SlideCardCallback";

    private RecyclerView mRv;
    private SlideCardAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    public SlideCardCallback(RecyclerView mRv,
                             SlideCardAdapter<SlideCardBean> adapter, List<SlideCardBean> mDatas) {
        // 设置方向，拖拽，滑动  上下左右 0x1111
        super(0, 15);//1111
        this.mRv = mRv;
        this.adapter = adapter;
        this.mDatas = mDatas;
    }

    //drag用的
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    // 动画结束后的操作；滑出上面的item，添加下面的item
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 数据循环使用
        // 移除最上面的
        SlideCardBean remove = mDatas.remove(viewHolder.getLayoutPosition());
        // 添加到数组的第一个位置
        mDatas.add(0, remove);
        // 刷新
        adapter.notifyDataSetChanged();



        switch (direction){
            case ItemTouchHelper.UP:
                String direct = CardConfig.DIRECTION == 0 ? "向左" :  "向右";
                Log.e(TAG, "onSwiped:  " + direct );
                break;
            case ItemTouchHelper.DOWN:
                direct = CardConfig.DIRECTION == 0 ? "向左" :  "向右";
                Log.e(TAG, "onSwiped:  " + direct );
                break;
            case ItemTouchHelper.RIGHT:
                Log.e(TAG, "onSwiped:  向右" );
                break;
            case ItemTouchHelper.LEFT:
                Log.e(TAG, "onSwiped:  向左" );
                break;
            default:
                break;
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);

        // 放大的系数
        double fraction = distance / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }

         //当前显示在界面的item数，0，1，2，3
//        int itemCount = recyclerView.getChildCount();
//        for (int i = 0; i < itemCount; i++) {
//            View view = recyclerView.getChildAt(i);
//
//            int level = itemCount - i - 1;
//
//            // 对子View进行缩放平移处理
//            if (level > 0) {
//                // level < 3
//                if (level < CardConfig.MAX_SHOW_COUNT - 1) {// 2,1
//                    // 最大达到它的上一个Item的效果
//                    view.setTranslationY((float) (-CardConfig.TRANS_Y_GAP * level + fraction * CardConfig.TRANS_Y_GAP));
//                    view.setTranslationX((float) (CardConfig.TRANS_X_GAP * level - fraction * CardConfig.TRANS_X_GAP));
//                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
//                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
//                }
//            }
//        }

        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;
            if (level > 0) {   //不包括第一张
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (-CardConfig.TRANS_Y_GAP * level + fraction * CardConfig.TRANS_Y_GAP));
                    view.setTranslationX((float) (CardConfig.TRANS_X_GAP * level - fraction * CardConfig.TRANS_X_GAP));
                }
            }
        }
    }

    // 回弹时间
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 500;
    }

    // 回弹距离
    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }
}
