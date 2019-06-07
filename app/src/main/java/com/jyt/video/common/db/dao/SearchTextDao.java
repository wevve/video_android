package com.jyt.video.common.db.dao;

import android.arch.persistence.room.*;
import com.jyt.video.common.db.bean.SearchText;

import java.util.List;

@Dao
public interface SearchTextDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchText... searchTexts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(SearchText... videos);

    @Delete
    void deleteOne(SearchText  video);

    @Delete
    void delete(SearchText...  video);


    @Query("SELECT * FROM SearchText order by weight desc")
    List<SearchText> loadAll();

    @Query("SELECT * FROM SearchText where content == :content")
    SearchText selectByContent(String content);


    @Query("DELETE FROM searchtext")
    void deleteAll();
}
