package com.example.viewpager.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.viewpager.FragmentDelegater;

public abstract class LazyFragment extends Fragment {

    FragmentDelegater mFragmentDelegater;
    private View rootView = null;

    private boolean isViewCreated = false;

    // 记录上一次的状态
    private boolean isVisibleStateUP = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        E("onCreateView: ");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }

        isViewCreated = true;

        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }

        initView(rootView); // 初始化控件 findvxxx

        return rootView;
    }

    // 在生命周期之前 就会执行
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        E("setUserVisibleHint");

        // 当前显示的是T1，这个时候按下T2
        // T1 的isVisibleToUser = false，isVisibleStateUP = true；
        // T2 的isVisibleToUser = true，isVisibleStateUP = false；
        if (isViewCreated) {
            if (isVisibleToUser && !isVisibleStateUP) { // true,加载数据
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && isVisibleStateUP) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    private void dispatchUserVisibleHint(boolean visibleState) {

        isVisibleStateUP = visibleState;

        if (visibleState) {
            // 加载数据
            onFragmentLoad();
        } else {
            // 停止一切操作
            onFragmentLoadStop();
        }
    }


    // 让子类完成，初始化布局，初始化控件
    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();

    // -->>>停止网络数据请求
    public void onFragmentLoadStop() {
        E("onFragmentLoadStop");
    }

    // -->>>加载网络数据请求
    public void onFragmentLoad() {
        E("onFragmentLoad");
    }


    @Override
    public void onResume() {
        super.onResume();
        E("onResume");

        if (getUserVisibleHint() && !isVisibleStateUP) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        E("onPause");
        if (getUserVisibleHint() && isVisibleStateUP) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        E("onDestroyView");
    }

    // 工具相关而已
    public void setFragmentDelegater(FragmentDelegater fragmentDelegater) {
        mFragmentDelegater = fragmentDelegater;
    }

    private void E(String string) {
        if (mFragmentDelegater != null) {
            mFragmentDelegater.dumpLifeCycle(string);
        }
    }
}
