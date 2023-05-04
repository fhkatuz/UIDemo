package com.example.uidemo.recycleritemdecoration.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidemo.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    public static final String TAG = "DataAdapter";

    private Context mContext;

    private ArrayList<GroupBean> mDataList;

    public DataAdapter(Context context, ArrayList<GroupBean> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    // 是否是组的第一个Item
    public boolean isFirstItemOfGroup(int position) {
        if(position == 0){
            return true;
        } else {
            // 拿到当前位置的和前一个位置的 组名
            String currentItemGroupName = getGroupName(position);
            String preItemGroupName = getGroupName(position - 1);
            // 如果相等，则表示position的item不是第一个，否则是的
            if (preItemGroupName.equals(currentItemGroupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public String getGroupName(int position) {
        return mDataList.get(position).getGrounpName();
    }

    @NonNull
    @Override
    public DataAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_data, null);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataHolder holder, int position) {
        holder.tv_item.setText(mDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder{
        private TextView tv_item;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
