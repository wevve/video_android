package com.jyt.video.home.entity;

import com.jyt.video.video.entity.ThumbVideo;

import java.util.List;

public class HomeResult {
    private  String app_logo;
    private List<Banner.Data> banner;
    private List<HomeVideoList> Videos;

    public List<Banner.Data> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner.Data> banner) {
        this.banner = banner;
    }

    public List<HomeVideoList> getVideos() {
        return Videos;
    }

    public void setVideos(List<HomeVideoList> videos) {
        Videos = videos;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }
}
