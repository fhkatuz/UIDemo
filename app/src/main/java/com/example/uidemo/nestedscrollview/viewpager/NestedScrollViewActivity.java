package com.example.uidemo.nestedscrollview.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.uidemo.R;
import com.example.uidemo.databinding.ActivityNestedScrollViewBinding;
import com.example.uidemo.nestedscrollview.viewpager.fragment.RecyclerViewFragment;
import com.example.uidemo.nestedscrollview.viewpager.fragment.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedScrollViewActivity extends AppCompatActivity {

    private ActivityNestedScrollViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nested_scroll_view);

        final String[] labels = new String[]{"个性推荐", "歌单", "主播电台", "排行榜"};

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());

        mBinding.viewpagerView.setAdapter(pagerAdapter);
        new TabLayoutMediator(mBinding.tablayout, mBinding.viewpagerView, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();

        mBinding.tablayoutViewpager.post(new Runnable() {
            @Override
            public void run() {
                mBinding.tablayoutViewpager.getLayoutParams().height = mBinding.nestedscrollview.getMeasuredHeight();
                mBinding.tablayoutViewpager.requestLayout();
            }
        });
    }

    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }
}