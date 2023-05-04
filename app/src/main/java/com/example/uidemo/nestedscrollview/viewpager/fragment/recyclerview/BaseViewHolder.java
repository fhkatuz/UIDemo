package com.example.uidemo.nestedscrollview.viewpager.fragment.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidemo.nestedscrollview.viewpager.base.customview.BaseCustomViewModel;
import com.example.uidemo.nestedscrollview.viewpager.base.customview.ICustomView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "BaseViewHolder";

    ICustomView view;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.view = view;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        view.setData(item);
    }
}
