package com.jyt.video.video.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ThumbVideo implements Serializable , Parcelable {
    private Long id;
    private String title;
    private Long click;
    private Long good;
    private String thumbnail;
    private String play_time;
    private Long gold;
    private Long update_time;

    public ThumbVideo() {
    }

    protected ThumbVideo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            click = null;
        } else {
            click = in.readLong();
        }
        if (in.readByte() == 0) {
            good = null;
        } else {
            good = in.readLong();
        }
        thumbnail = in.readString();
        play_time = in.readString();
        if (in.readByte() == 0) {
            gold = null;
        } else {
            gold = in.readLong();
        }
        if (in.readByte() == 0) {
            update_time = null;
        } else {
            update_time = in.readLong();
        }
    }

    public static final Creator<ThumbVideo> CREATOR = new Creator<ThumbVideo>() {
        @Override
        public ThumbVideo createFromParcel(Parcel in) {
            return new ThumbVideo(in);
        }

        @Override
        public ThumbVideo[] newArray(int size) {
            return new ThumbVideo[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getGood() {
        return good;
    }

    public void setGood(Long good) {
        this.good = good;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        if (click == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(click);
        }
        if (good == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(good);
        }
        dest.writeString(thumbnail);
        dest.writeString(play_time);
        if (gold == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(gold);
        }
        if (update_time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(update_time);
        }
    }
}
