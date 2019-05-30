package com.jyt.video.service

import com.jyt.video.home.entity.HomeResult
import com.jyt.video.home.entity.HomeTabResult
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.video.entity.CollectionVideo
import com.jyt.video.video.entity.VideoDetail
import io.reactivex.Observable

interface VideoService {


    fun getCollectionVideoList(page: Int, callback: ServiceCallback<List<CollectionVideo>>)

    fun getHomeTab(callback: ServiceCallback<HomeTabResult>)

    fun getHomeData(callback: ServiceCallback<HomeResult>)

    fun getVideoDetail(videoId: Long?, callback: ServiceCallback<VideoDetail>)

    fun doLikeVideo(videoId: Long?, callback: ServiceCallback<Any>)

    fun doCollection(videoId: Long?,callback: ServiceCallback<Any>)

    fun delCollection(callback: ServiceCallback<Any>,vararg videoId: Long)
}
