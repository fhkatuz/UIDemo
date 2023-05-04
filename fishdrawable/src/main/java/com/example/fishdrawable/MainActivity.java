package com.example.fishdrawable;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvFish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();
    }

//    private void initView() {
//        mIvFish = findViewById(R.id.iv_fish);
//        mIvFish.setImageDrawable(new FishDrawable());
//    }
}