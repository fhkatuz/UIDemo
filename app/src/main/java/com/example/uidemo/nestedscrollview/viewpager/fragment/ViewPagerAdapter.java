package com.example.uidemo.nestedscrollview.viewpager.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final String TAG = "ViewPagerAdapter";
    private final List<Fragment> data;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
