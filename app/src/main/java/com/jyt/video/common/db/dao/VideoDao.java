package com.jyt.video.common.db.dao;

import android.arch.persistence.room.*;
import com.jyt.video.common.db.bean.Video;

import java.util.List;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVideos(Video ... videos);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateVideos(Video... videos);

    @Delete
    void deleteVideos(Video ... videos);

    @Query("SELECT * FROM Video order by id desc")
    List<Video> loadAllVideos();
}
