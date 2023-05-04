package com.example.uidemo.utils;

import android.content.Context;

public class DPUtils {

    public static final String TAG = "DPUtils";

    public static int dip2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
