package com.jyt.video.common.db.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jyt.video.video.adapter.CacheVideoAdapter;

@Entity
public class Video {

    @PrimaryKey
    private long id;

    private String title;
    private String desc;
    private String path;
    private String url;
    private int size;
    private int curSize;
    private String cover;
    private String play_time;
    // 0 未开始
    // 1 下载中
    // 2 暂停
    // 3 下载失败
    // 4 下载完成
    private int status;

    @Ignore
    private boolean isSel;

    @Ignore
    transient private CacheVideoAdapter.VideoCacheItemVH holder;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public CacheVideoAdapter.VideoCacheItemVH getHolder() {
        return holder;
    }

    public void setHolder(CacheVideoAdapter.VideoCacheItemVH holder) {
        this.holder = holder;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurSize() {
        return curSize;
    }

    public void setCurSize(int curSize) {
        this.curSize = curSize;
    }
}
