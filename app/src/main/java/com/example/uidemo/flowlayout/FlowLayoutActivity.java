package com.example.uidemo.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uidemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout = (FlowLayout) findViewById(R.id.layout_flow);

        initFlowView();

    }

    private void initFlowView() {
        String [] strs = new String[]{"lili言以时间来开发dasdsaaasdsasadsadsddadassdadsaasdas", "东北效力", "花花", "一言以时间来开发dasdsaaasdsasadsadsddadassdadsaasdasdasdsadaddadad", "被电话卡是快乐的","lili", "大撒大撒", "反射的", "发", "发生大事","lili", "的撒大大大撒", "发士大夫士大夫", "打撒撒旦撒", "啊飒飒","lili", "高度发达言以时间来开发dasdsaaasdsasadsadsddadassdadsaasdas"};
        List<String> strings = new ArrayList<>(Arrays.asList(strs));

        for (int i = 0; i < strings.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setTextSize(16);
            textView.setGravity(Gravity.LEFT);
            textView.setText(strs[i]);
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

            mFlowLayout.addView(textView);

            //设置点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            int finalI = i;
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String remove = strings.remove(finalI);
                    Toast.makeText(FlowLayoutActivity.this, remove + "条目被移除了", Toast.LENGTH_SHORT).show();
                    mFlowLayout.notifyDatas(strings);
                    return true;
                }
            });
        }
    }
}