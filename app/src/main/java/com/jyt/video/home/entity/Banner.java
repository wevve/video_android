package com.jyt.video.home.entity;

import java.util.List;

public class Banner {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{

        /**
         * url : http://www.msvod.cc/
         * images_url : http://v.msvodx.com/XResource/20180522/pdNihDezTKKbXPjxSQCDt7zY8PXiyyYC.jpg
         * target : 1
         * info : AD-3
         */

        private String url;
        private String images_url;
        private int target;
        private String info;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImages_url() {
            return images_url;
        }

        public void setImages_url(String images_url) {
            this.images_url = images_url;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
