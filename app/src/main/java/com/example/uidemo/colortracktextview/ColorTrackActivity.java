package com.example.uidemo.colortracktextview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uidemo.R;

public class ColorTrackActivity extends AppCompatActivity {

    private ColorTrackTextView mColorTrackTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);
        initView();
    }

    public void leftToRight(View view) {
        setAnimation(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
    }

    public void rightToLeft(View view) {
        setAnimation(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
    }

    private void setAnimation(ColorTrackTextView.Direction direction) {
        mColorTrackTv.setDirection(direction);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorTrackTv.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

    private void initView() {
        mColorTrackTv = findViewById(R.id.color_track_tv);
    }
}