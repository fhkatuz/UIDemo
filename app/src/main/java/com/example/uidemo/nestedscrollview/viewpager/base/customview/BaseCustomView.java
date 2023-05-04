package com.example.uidemo.nestedscrollview.viewpager.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseCustomView <T extends ViewDataBinding, S extends BaseCustomViewModel> extends LinearLayout implements ICustomView<S>, View.OnClickListener{

    private T dataBinding;
    private S viewModel;


    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public View getRootView() {
        return dataBinding.getRoot();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (setViewLayoutId() != 0) {
            dataBinding = DataBindingUtil.inflate(inflater, setViewLayoutId(), this, false);
            this.addView(dataBinding.getRoot());
        }

    }

    @Override
    public void setData(S data) {
        viewModel = data;
        setDataToView(viewModel);
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
    }

    public T getDataBinding() {
        return dataBinding;
    }

    public S getViewModel() {
        return viewModel;
    }

    protected abstract int setViewLayoutId();

    protected abstract void setDataToView(S data);
}
