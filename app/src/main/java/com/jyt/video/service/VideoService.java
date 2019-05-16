package com.jyt.video.service;

import com.jyt.video.home.entity.HomeResult;
import com.jyt.video.home.entity.HomeTabResult;
import com.jyt.video.home.entity.TabEntity;
import com.jyt.video.video.entity.CollectionVideo;
import io.reactivex.Observable;

import java.util.List;

public interface VideoService {

    public void getCollectionVideoList(int page,ServiceCallback<List<CollectionVideo>> callback);

    void getHomeTab(ServiceCallback<HomeTabResult> callback);

    void getHomeData(ServiceCallback<HomeResult> callback);
}
