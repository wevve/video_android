package com.jyt.video.video.entity;

import com.jyt.video.common.db.bean.Video;

public class CollectionVideo extends Video {
    private boolean isSel;
    /**
     * videoId : 5619
     * videoTitle : 视频集
     * videoImgUrl : http://v.msvodx.com/XResource/20190407/xiMitFYYQdYm4pbmHpNyYBWKr52dw3ie.jpg
     * videoSendDate : 1554576013
     * videoKind : 2
     */

    private Long videoId;
    private String videoTitle;
    private String videoImgUrl;
    private long videoSendDate;
    private String videoKind;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }


    public CollectionVideo() {
    }

    public CollectionVideo(boolean isSel, Long videoId, String videoTitle, String videoImgUrl, long videoSendDate, String videoKind) {
        this.isSel = isSel;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoImgUrl = videoImgUrl;
        this.videoSendDate = videoSendDate;
        this.videoKind = videoKind;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public long getVideoSendDate() {
        return videoSendDate;
    }

    public void setVideoSendDate(long videoSendDate) {
        this.videoSendDate = videoSendDate;
    }

    public String getVideoKind() {
        return videoKind;
    }

    public void setVideoKind(String videoKind) {
        this.videoKind = videoKind;
    }
}
