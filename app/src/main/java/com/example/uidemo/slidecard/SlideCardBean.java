package com.example.uidemo.slidecard;

import com.example.uidemo.R;

import java.util.ArrayList;
import java.util.List;

public class SlideCardBean {
    private int postition;
    private String name;
    private int id;

    public SlideCardBean(int postition, int id, String name) {
        this.postition = postition;
        this.id = id;
        this.name = name;
    }

    public int getPostition() {
        return postition;
    }

    public SlideCardBean setPostition(int postition) {
        this.postition = postition;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SlideCardBean setName(String name) {
        this.name = name;
        return this;
    }

    public static List<SlideCardBean> initDatas() {
        List<SlideCardBean> datas = new ArrayList<>();
        int i = 1;
        datas.add(new SlideCardBean(i++, R.drawable.img_1, "美女1"));
        datas.add(new SlideCardBean(i++, R.drawable.img_2, "美女2"));
        datas.add(new SlideCardBean(i++, R.drawable.img_3, "美女3"));
        datas.add(new SlideCardBean(i++, R.drawable.img_4, "美女4"));
        datas.add(new SlideCardBean(i++, R.drawable.img_5, "美女5"));
        datas.add(new SlideCardBean(i++, R.drawable.img_6, "美女6"));
        datas.add(new SlideCardBean(i++, R.drawable.img_7, "美女7"));
        datas.add(new SlideCardBean(i++, R.drawable.img_8, "美女8"));
        return datas;
    }
}
