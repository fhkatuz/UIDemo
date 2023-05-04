package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uidemo.colortracktextview.ColorTrackActivity;
import com.example.uidemo.flowlayout.FlowLayoutActivity;
import com.example.uidemo.flowlayout2.MyFlowLayoutActivity;
import com.example.uidemo.listviewpager.ListViewPagerActivity;
import com.example.uidemo.multitouchview.MultiTouchViewActivity;
import com.example.uidemo.nestedscrollview.viewpager.NestedScrollViewActivity;
import com.example.uidemo.photoview.PhotoView;
import com.example.uidemo.photoview.PhotoViewActivity;
import com.example.uidemo.recycleritemdecoration.RecyclerItemDecorationActivity;
import com.example.uidemo.slidecard.SlideCardActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);


    }


    @OnClick(R.id.bt_to_flowlayout)
    public void btToFlowLayout(){
        startActivity(new Intent(MainActivity.this, FlowLayoutActivity.class));
    }

    @OnClick(R.id.bt_to_colortrack)
    public void btToColortrack(){
        startActivity(new Intent(MainActivity.this, ColorTrackActivity.class));
    }

    @OnClick(R.id.bt_to_listviewpager)
    public void btToListViewPager(){
        startActivity(new Intent(MainActivity.this, ListViewPagerActivity.class));
    }

    @OnClick(R.id.bt_to_photoview)
    public void btToPhotoView(){
        startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
    }

    @OnClick(R.id.bt_to_multitouchview)
    public void btToMultiTouchView(){
        startActivity(new Intent(MainActivity.this, MultiTouchViewActivity.class));
    }

    @OnClick(R.id.bt_to_nestedscrollview)
    public void btToNestedScrollView(){
        startActivity(new Intent(MainActivity.this, NestedScrollViewActivity.class));
    }

    @OnClick(R.id.bt_to_item_decoration)
    public void btToItemDecoration(){
        startActivity(new Intent(MainActivity.this, RecyclerItemDecorationActivity.class));
    }

    @OnClick(R.id.bt_to_slide_card)
    public void btToSlideCard(){
        startActivity(new Intent(MainActivity.this, SlideCardActivity.class));
    }

}