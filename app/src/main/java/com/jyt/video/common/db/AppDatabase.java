package com.jyt.video.common.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.jyt.video.common.db.bean.SearchText;
import com.jyt.video.common.db.bean.Video;
import com.jyt.video.common.db.dao.SearchTextDao;
import com.jyt.video.common.db.dao.VideoDao;

@Database(entities = { Video.class, SearchText.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract VideoDao videoDao();

    public abstract SearchTextDao searchTextDao();
}
