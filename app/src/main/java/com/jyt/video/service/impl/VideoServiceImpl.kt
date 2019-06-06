package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.api.Api
import com.jyt.video.api.ApiService
import com.jyt.video.api.entity.FilterVideoListResult
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.entity.HomeResult
import com.jyt.video.home.entity.HomeTabResult
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.entity.VideoType
import com.jyt.video.service.ServiceCallback
import com.jyt.video.video.entity.CollectionVideo
import com.jyt.video.service.VideoService
import com.jyt.video.video.entity.SearchVideoResult
import com.jyt.video.video.entity.VideoDetail
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

import java.util.ArrayList
import java.util.Collections
import java.util.concurrent.ConcurrentNavigableMap
import java.util.concurrent.atomic.AtomicInteger

class VideoServiceImpl : VideoService {
    override fun buyVideo(videoId: Long, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.buyVideo(videoId,UserInfo.getUserId()),callback);
    }

    override fun searchVideo(keyWord: String, callback: ServiceCallback<SearchVideoResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.searchVideo(keyWord),callback)

    }

    override fun getVideoAfterFilter(map: Map<String,String>,page: Long,callback: ServiceCallback<FilterVideoListResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getVideoListByFilter(map,page),callback)

    }

    override fun getHomeVideoType(callback: ServiceCallback<ArrayList<VideoType.TypeGroup>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.videoType,callback)
    }

    override fun getCollectionVideoList(page: Int, callback: ServiceCallback<List<CollectionVideo>>) {
        if (page == 1) {
            RxHelper.simpleResult(ApiService.getInstance().api.getCollectionVideoList(UserInfo.getUserId()), callback)
        } else {
            callback.result.invoke(200, emptyList())
        }
    }

    override fun getHomeTab(callback: ServiceCallback<HomeTabResult>) {
        ApiService.getInstance().api.homeTab.compose(RxHelper.schedulersTransformer())
            .subscribe(RxHelper.SimpleConsume(callback), RxHelper.ErrorConsume(callback))
    }

    override fun getHomeData(callback: ServiceCallback<HomeResult>) {
        ApiService.getInstance().api.homeData.compose(RxHelper.schedulersTransformer())
            .subscribe(RxHelper.SimpleConsume(callback), RxHelper.ErrorConsume(callback))
    }

    override fun getVideoDetail(videoId: Long?, callback: ServiceCallback<VideoDetail>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getVideoDetail(videoId!!, UserInfo.getUserId()!!), callback)
    }

    override fun doLikeVideo(videoId: Long?, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.doLike(videoId!!, UserInfo.getUserId()!!), callback)
    }

    override fun doCollection(videoId: Long?, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.doCollection(UserInfo.getUserId(), videoId), callback)
    }


    override fun delCollection( callback: ServiceCallback<Any>,vararg videoId:Long) {

        var count = videoId.size
        var successCount = AtomicInteger(0)
        var failCount = AtomicInteger(0)
        Observable.fromIterable(videoId.asIterable()).flatMap {
            id->
            return@flatMap Observable.create<Boolean> {
                emmit->
                ApiService.getInstance().api.delCollection(UserInfo.getUserId(),id).compose(RxHelper.schedulersTransformer()).subscribe({
                    var data = it as BaseJson<Any>?
                    if (data?.code==BaseJson.CODE_SUCCESS){
                        emmit.onNext(true)
                    }else{
                        emmit.onNext(false)
                    }

                },{
                    emmit.onNext(false)
                })
            }
        }.compose(RxHelper.schedulersTransformer()).subscribe({
            if (true){
                successCount.addAndGet(1)
            }else{
                failCount.addAndGet(1)
            }
            if (successCount.get()+failCount.get()==count){
                callback.result(BaseJson.CODE_SUCCESS,null)
            }
        },{
            ToastUtil.showShort(App.app,it.message)
            callback.result(BaseJson.CODE_ERROR,null)
        })
    }


}
