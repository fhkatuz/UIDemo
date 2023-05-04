package com.example.uidemo.slidecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.uidemo.R;
import com.example.uidemo.slidecard.recyclerview.SlideCardAdapter;
import com.example.uidemo.slidecard.recyclerview.ViewHolder;

import java.util.List;

public class SlideCardActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private List<SlideCardBean> mCardBeans;
    private SlideCardAdapter<SlideCardBean> mSlideCardAdapter;
    public static final String TAG = "SlideCardActivity";
    public static final Integer LEFT = 0;
    public static final Integer RIGHT = 1;
    private Integer direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_card);

        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new SlideCardLayoutManager());

        mCardBeans = SlideCardBean.initDatas();
        mSlideCardAdapter = new SlideCardAdapter<SlideCardBean>(this, mCardBeans, R.layout.slide_card_view) {

            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
                viewHolder.setText(R.id.tvName, slideCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mCardBeans.size());

                Glide.with(SlideCardActivity.this)
                        .load(slideCardBean.getId())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度(像素)

        mRv.setAdapter(mSlideCardAdapter);
        mRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        if(event.getX() > width / 2){
                            CardConfig.DIRECTION = RIGHT;
                        }else{
                            CardConfig.DIRECTION = LEFT;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        CardConfig.initConfig(this);

        // 创建 ItemTouchHelper ，必须要使用 ItemTouchHelper.Callback
        ItemTouchHelper.Callback callback = new SlideCardCallback(mRv, mSlideCardAdapter, mCardBeans);
        ItemTouchHelper helper = new ItemTouchHelper(callback);

        // 绑定rv
        helper.attachToRecyclerView(mRv);
    }


}