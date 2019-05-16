package com.jyt.video.video.entity;

import android.arch.persistence.room.Ignore;
import com.jyt.video.common.db.bean.Video;

public class CacheVideo extends Video {
    @Ignore
    private boolean isSel;


    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
