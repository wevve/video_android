package com.jyt.video.video.entity;

import com.jyt.video.common.db.bean.Video;

public class CollectionVideo extends Video {
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
