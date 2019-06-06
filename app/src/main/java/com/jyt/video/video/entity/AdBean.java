package com.jyt.video.video.entity;

import java.io.Serializable;

public class AdBean implements Serializable {

    Data before;
    Data pause;

    public Data getBefore() {
        return before;
    }

    public void setBefore(Data before) {
        this.before = before;
    }

    public Data getPause() {
        return pause;
    }

    public void setPause(Data pause) {
        this.pause = pause;
    }

    public class Data implements Serializable{
        /**
         * img : http://v.msvodx.com/XResource/20180525/JzRWwtacxRWxyzkZw8jzkpxC3GchKT7H.jpg
         * url : https://www.msvod.cc/
         */

        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
