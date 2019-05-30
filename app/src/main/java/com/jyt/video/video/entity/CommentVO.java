package com.jyt.video.video.entity;

import java.util.ArrayList;
import java.util.List;

public class CommentVO {
    Long count;
    ArrayList<CommentItem> list;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ArrayList<CommentItem> getList() {
        return list;
    }

    public void setList(ArrayList<CommentItem> list) {
        this.list = list;
    }
}
