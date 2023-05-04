package com.example.uidemo.recycleritemdecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uidemo.R;
import com.example.uidemo.recycleritemdecoration.recyclerview.DataAdapter;
import com.example.uidemo.recycleritemdecoration.recyclerview.GroupBean;
import com.example.uidemo.recycleritemdecoration.recyclerview.GroupItemDecoration;

import java.util.ArrayList;

public class RecyclerItemDecorationActivity extends AppCompatActivity {

    private ArrayList<GroupBean> starList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_decoration);

        init();

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DataAdapter(this, starList));

        // 添加自定义ItemDecoration
        recyclerView.addItemDecoration(new GroupItemDecoration(this));
    }

    private void init(){
        starList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 20; j++) {
                if (i % 2 == 0) {
                    starList.add(new GroupBean("何炅" + j, "快乐家族" + i));
                } else {
                    starList.add(new GroupBean("汪涵" + j, "天天兄弟" + i));
                }
            }
        }
    }
}