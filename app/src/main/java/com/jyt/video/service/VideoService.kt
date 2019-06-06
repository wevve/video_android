package com.jyt.video.service

import com.jyt.video.api.entity.FilterVideoListResult
import com.jyt.video.home.entity.HomeResult
import com.jyt.video.home.entity.HomeTabResult
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.entity.VideoType
import com.jyt.video.video.entity.CollectionVideo
import com.jyt.video.video.entity.SearchVideoResult
import com.jyt.video.video.entity.VideoDetail
import io.reactivex.Observable

interface VideoService {

    fun buyVideo(videoId: Long,callback: ServiceCallback<Any>)

    fun searchVideo(keyWord:String,callback: ServiceCallback<SearchVideoResult>)

    fun getCollectionVideoList(page: Int, callback: ServiceCallback<List<CollectionVideo>>)

    fun getHomeTab(callback: ServiceCallback<HomeTabResult>)

    fun getHomeData(callback: ServiceCallback<HomeResult>)

    fun getVideoDetail(videoId: Long?, callback: ServiceCallback<VideoDetail>)

    fun doLikeVideo(videoId: Long?, callback: ServiceCallback<Any>)

    fun doCollection(videoId: Long?,callback: ServiceCallback<Any>)

    fun delCollection(callback: ServiceCallback<Any>,vararg videoId: Long)

    fun getHomeVideoType(callback: ServiceCallback<ArrayList<VideoType.TypeGroup>>)

    fun getVideoAfterFilter(map: Map<String,String>,page: Long,callback: ServiceCallback<FilterVideoListResult>)
}
