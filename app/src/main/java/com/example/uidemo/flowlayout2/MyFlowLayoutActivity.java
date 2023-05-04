package com.example.uidemo.flowlayout2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.uidemo.R;

import java.util.ArrayList;

public class MyFlowLayoutActivity extends AppCompatActivity {

    private MyFlowLayout mFlowLayout;
    private ArrayList<FlowDataBean> mFlowDataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flow_layout);

        mFlowLayout = (MyFlowLayout) findViewById(R.id.fl);

        mFlowDataBeans = new ArrayList<>();

        FlowDataBean a = new FlowDataBean("陕西潼关肉夹馍的撒旦的撒旦大苏打骄傲了奥兰多就爱上了了大数据扩大深刻领会就打开拉萨的绿卡", R.drawable.a);
        FlowDataBean b = new FlowDataBean("薯条", R.drawable.b);
        FlowDataBean c = new FlowDataBean("鸡腿", R.drawable.c);
        FlowDataBean d = new FlowDataBean("豪华鸡腿套餐", R.drawable.c);

        mFlowDataBeans.add(a);
        mFlowDataBeans.add(b);
        mFlowDataBeans.add(c);
        mFlowDataBeans.add(b);
        mFlowDataBeans.add(d);
        mFlowDataBeans.add(b);
        mFlowDataBeans.add(a);
        mFlowDataBeans.add(c);

        mFlowLayout.setVerticalMargin(30);
        mFlowLayout.setTextColor(R.color.purple_500);
        mFlowLayout.setHorizontalMargin(10);
        mFlowLayout.setTextList(mFlowDataBeans);

        mFlowLayout.setOnClickItemListener((v, text,pos) -> {
            Toast.makeText(this, text, Toast.LENGTH_LONG ).show();
        });

        mFlowLayout.setOnClickLongDelItemListener((v, text, pos) -> {

        });

    }
}