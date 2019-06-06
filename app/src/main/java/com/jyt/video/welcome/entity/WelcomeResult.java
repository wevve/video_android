package com.jyt.video.welcome.entity;

public class WelcomeResult {

    /**
     * app_start_screen : http://www.client.com/XResource/20190531/z5D8TS5YwxBHr6nDc7bYkbh2SFGP3z66.png
     * app_start_time : 5
     * app_start_url : http://www.baidu.com
     */

    private String app_start_screen;
    private String app_start_time;
    private String app_start_url;

    public String getApp_start_screen() {
        return app_start_screen;
    }

    public void setApp_start_screen(String app_start_screen) {
        this.app_start_screen = app_start_screen;
    }

    public String getApp_start_time() {
        return app_start_time;
    }

    public void setApp_start_time(String app_start_time) {
        this.app_start_time = app_start_time;
    }

    public String getApp_start_url() {
        return app_start_url;
    }

    public void setApp_start_url(String app_start_url) {
        this.app_start_url = app_start_url;
    }
}
