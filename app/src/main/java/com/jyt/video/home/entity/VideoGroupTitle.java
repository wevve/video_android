package com.jyt.video.home.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.jyt.video.video.entity.ThumbVideo;

import java.util.ArrayList;
import java.util.List;

public class VideoGroupTitle implements Parcelable {
    private String text;

    private ArrayList<ThumbVideo> videos;

    protected VideoGroupTitle(Parcel in) {
        text = in.readString();
        videos = in.createTypedArrayList(ThumbVideo.CREATOR);
    }

    public static final Creator<VideoGroupTitle> CREATOR = new Creator<VideoGroupTitle>() {
        @Override
        public VideoGroupTitle createFromParcel(Parcel in) {
            return new VideoGroupTitle(in);
        }

        @Override
        public VideoGroupTitle[] newArray(int size) {
            return new VideoGroupTitle[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public VideoGroupTitle() {
    }

    public VideoGroupTitle(String text) {
        this.text = text;
    }


    public ArrayList<ThumbVideo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<ThumbVideo> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeTypedList(videos);
    }
}
