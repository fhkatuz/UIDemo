package com.example.viewpager2;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private MyFragmentAdapter mFragmentAdapter;
    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager2);

        //准备数据
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(new MyFragment(i+1));
        }

        //准备适配器
        mFragmentAdapter = new MyFragmentAdapter(this, list);
        mViewPager2.setAdapter(mFragmentAdapter);

        //添加数据：
        for (int i = 0; i < list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab"+ (i + 1)));
        }

        //添加监听
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.setScrollPosition(position, 0, true);

            }
        };

        mViewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);

        //ViewPager2默认RecyclerView的预取是关闭的：
        ((RecyclerView) mViewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        mViewPager2.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        super.onDestroy();
    }
}