package com.example.uidemo.nestedscrollview.viewpager.fragment.recyclerview.titleview;

import android.content.Context;
import android.view.View;

import com.example.uidemo.R;
import com.example.uidemo.databinding.TitleViewBinding;
import com.example.uidemo.nestedscrollview.viewpager.base.customview.BaseCustomView;

import java.util.Random;

public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewViewModel> {

    public static final String TAG = "TitleView";
    private TitleViewViewModel viewModel;

    public TitleView(Context context) {
        super(context);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    protected void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
        int min = 1;
        int max = 4;

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        if(randomNumber == 1) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_1);
            getDataBinding().itemFileName.getLayoutParams().height = 100;

        } else if(randomNumber == 2) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_2);
            getDataBinding().itemFileName.getLayoutParams().height = 200;
        } else if(randomNumber == 3) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_3);
            getDataBinding().itemFileName.getLayoutParams().height = 300;
        } else if(randomNumber == 4) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_4);
            getDataBinding().itemFileName.getLayoutParams().height = 400;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
