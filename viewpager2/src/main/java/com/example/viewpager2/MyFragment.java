package com.example.viewpager2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    public static final String TAG = "MyFragment";
    private  int content;

    public MyFragment(int content) {
        Log.e(TAG, "MyFragment: " + content );
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Fragment" + content);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " + content);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " + content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: " + content);
    }
}
