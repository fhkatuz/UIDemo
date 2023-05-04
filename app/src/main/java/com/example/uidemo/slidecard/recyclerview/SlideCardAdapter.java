package com.example.uidemo.slidecard.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class SlideCardAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public static final String TAG = "SlideCardAdapter";

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected ViewGroup mRv;
    private OnItemClickListener mOnItemClickListener;

    public SlideCardAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(this.mContext, (View)null, parent, this.mLayoutId);
        if(null == this.mRv) {
            this.mRv = parent;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.setListener(position, holder);
        this.convert(holder, this.mDatas.get(position));
    }

    public abstract void convert(ViewHolder viewHolder, T t);

    private void setListener(int position, ViewHolder viewHolder) {
        if(this.isEnabled(this.getItemViewType(position))) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(SlideCardAdapter.this.mOnItemClickListener != null) {
                        SlideCardAdapter.this.mOnItemClickListener.onItemClick(SlideCardAdapter.this.mRv, v, SlideCardAdapter.this.mDatas.get(position), position);
                    }

                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if(SlideCardAdapter.this.mOnItemClickListener != null) {
                        int position = SlideCardAdapter.this.getPosition(viewHolder);
                        return SlideCardAdapter.this.mOnItemClickListener.onItemLongClick(SlideCardAdapter.this.mRv, v, SlideCardAdapter.this.mDatas.get(position), position);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    private int getPosition(ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    private boolean isEnabled(int itemViewType) {
        return true;
    }

    @Override
    public int getItemCount() {
        return this.mDatas != null?this.mDatas.size():0;
    }
}
