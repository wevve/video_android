package com.jyt.video.home.entity;

import com.jyt.video.video.entity.ThumbVideo;

import java.util.ArrayList;

public class HomeVideoList {
    private String title;
    private ArrayList<ThumbVideo> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ThumbVideo> getList() {
        return list;
    }

    public void setList(ArrayList<ThumbVideo> list) {
        this.list = list;
    }
}
