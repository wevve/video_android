package com.jyt.video.video.entity;

import java.io.Serializable;

public class GuessBean implements Serializable {

    /**
     * id : 229
     * title : 洪真英 - 爱不爱洪真英 - 爱不爱
     * thumbnail : http://v.msvodx.com/static/pic2018/054208085889F10F6F0A2F5B68CA73F9.jpg
     * good : 0
     * play_time : 00:03:23
     * click : 2336
     * add_time : 1516329866
     * gold : 10
     * update_time : 1516329877
     */

    private long id;
    private String title;
    private String thumbnail;
    private long good;
    private String play_time;
    private long click;
    private long add_time;
    private double gold;
    private long update_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getGood() {
        return good;
    }

    public void setGood(long good) {
        this.good = good;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
}
