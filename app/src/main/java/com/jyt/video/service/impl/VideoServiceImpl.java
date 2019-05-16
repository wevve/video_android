package com.jyt.video.service.impl;

import com.jyt.video.api.ApiService;
import com.jyt.video.common.util.RxHelper;
import com.jyt.video.home.entity.HomeResult;
import com.jyt.video.home.entity.HomeTabResult;
import com.jyt.video.home.entity.TabEntity;
import com.jyt.video.service.ServiceCallback;
import com.jyt.video.video.entity.CollectionVideo;
import com.jyt.video.service.VideoService;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class VideoServiceImpl implements VideoService {

    @Override
    public void getCollectionVideoList(int page, ServiceCallback<List<CollectionVideo>> callback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CollectionVideo());
        arrayList.add(new CollectionVideo());
        arrayList.add(new CollectionVideo());

        callback.getResult().invoke(200,arrayList);
    }

    @Override
    public void getHomeTab(ServiceCallback<HomeTabResult> callback) {
        ApiService.getInstance().api.getHomeTab().compose(RxHelper.schedulersTransformer()).subscribe(new RxHelper.SimpleConsume(callback), new RxHelper.ErrorConsume(callback));
    }

    @Override
    public void getHomeData(ServiceCallback<HomeResult> callback) {
        ApiService.getInstance().api.getHomeData().compose(RxHelper.schedulersTransformer()).subscribe(new RxHelper.SimpleConsume(callback), new RxHelper.ErrorConsume(callback));
    }
}
