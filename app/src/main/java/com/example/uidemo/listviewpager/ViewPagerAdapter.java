package com.example.uidemo.listviewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.uidemo.R;

import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends PagerAdapter {

    public static final String TAG = "ViewPagerAdapte";

    private Context mContext;
    private List<Map<String, Integer>> mList;

    public ViewPagerAdapter(Context context, List<Map<String, Integer>> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = View.inflate(mContext, R.layout.item_viewpager, null);
        MyListVIew listView= (MyListVIew) inflate.findViewById(R.id.item_listview);
        listView.setAdapter(new SimpleAdapter(container.getContext(), mList, R.layout.item_base, new String[]{"key"}, new int[]{R.id.iv}));
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
