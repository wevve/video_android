package com.jyt.video.video.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoInfoBean implements Serializable {

    /**
     * title : Plastic - WonderlandPlastic - Wonderland
     * url : http://v.msvodx.com/mp4/65100154FF586807762E1F417B6905FA.mp4
     * thumbnail : http://v.msvodx.com/static/pic2018/054205085645E55E6A0A4D0475B01B0D.jpg
     * info : Msvod魅思Cms V10 相对以前版本功能更加强大、稳定同时提升了用户使用体验，全新的模板+全新开发的系统内核，本次我司采用最新 ThinkPHP框架版本 5.0.11 进行开发Msvod V10 版本 也是魅思视频产品终极版更新换代，以后升级都会在V10基础上升级，不会再更新换代，为什么由V5到V9版本我们都一直在换代换程序内核，原因是每次开发出来的版本都会被外面盗版倒卖，严重损失我司权益，本身换代我们采取防止盗版倒卖行为，也就是说MSVOD V10 所有站长的管理后台我们采取软件统一管理，购买MSVOD V10的站长只需要凭用户账号密码登陆软件就可以管理后台，没有域名限制，前台代码开源可二次开发 自由更换模板等。更多详细请到我们演示站体验吧
     * short_info :
     * gold : 10
     * click : 2666
     * class : 27
     */

    private String title;
    private String url;
    private String thumbnail;
    private String info;
    private String short_info;
    private int gold;
    private long click;
    private String play_time;
    @SerializedName("class")
    private String classX;

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getShort_info() {
        return short_info;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }
}
