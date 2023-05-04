package com.example.uidemo.recycleritemdecoration.recyclerview;

public class GroupBean {

    public static final String TAG = "GroupBean";

    private String name;
    private String grounpName;

    public GroupBean(String name, String grounpName) {
        this.name = name;
        this.grounpName = grounpName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrounpName() {
        return grounpName;
    }

    public void setGrounpName(String grounpName) {
        this.grounpName = grounpName;
    }
}
