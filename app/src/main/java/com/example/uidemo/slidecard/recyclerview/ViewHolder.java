package com.example.uidemo.slidecard.recyclerview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "ViewHolder";
    private SparseArray<View> mViews = new SparseArray();
    private int mLayoutId;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            View holder2 = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder1 = new ViewHolder(holder2);
            holder1.mLayoutId = layoutId;
            return holder1;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            return holder;
        }
    }

    public void setText(int viewId, CharSequence text) {
        TextView textView = (TextView) this.getView(viewId);
        textView.setText(text);
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.mViews.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            this.mViews.put(viewId, view);
        }

        return (T) view;
    }
}

