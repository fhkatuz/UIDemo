package com.example.uidemo.listviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.uidemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewPagerActivity extends AppCompatActivity {

    private int[] iv = new int[]{R.mipmap.iv_0, R.mipmap.iv_1, R.mipmap.iv_2,
            R.mipmap.iv_3, R.mipmap.iv_4, R.mipmap.iv_5,
            R.mipmap.iv_6, R.mipmap.iv_7, R.mipmap.iv_8};
    private MyViewPager mViewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_pager);

        mViewPager = (MyViewPager) findViewById(R.id.myviewpager);

        List<Map<String, Integer>> list = new ArrayList<>();

        Map<String, Integer> map;

        for (int i = 0; i < iv.length; i++) {
            map = new HashMap<>();
            map.put("key", iv[i]);
            list.add(map);
        }

        mViewPager.setAdapter(new ViewPagerAdapter(this, list));


    }
}