package com.example.uidemo.nestedscrollview.viewpager.fragment.recyclerview.titleview;

import com.example.uidemo.nestedscrollview.viewpager.base.customview.BaseCustomViewModel;

public class TitleViewViewModel extends BaseCustomViewModel {

    public static final String TAG = "TitleViewViewModel";

    public String title;
    public TitleViewViewModel(String title){
        this.title = title;
    }
}
