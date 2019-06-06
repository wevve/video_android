package com.jyt.video.home.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*

class RecommendFrag:BaseVideoListFrag(){


    override fun createAdapter(): BaseVideoListAdapter {
        return BaseVideoListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getData(page: Int) {

        if (page==1) {
            videoService.getHomeData(ServiceCallback { code, data ->
                if (data != null) {
                    var list = adapter?.data
                    var banner = Banner()
                    banner.data = data.banner
                    list?.add(banner)
                    var hotTitle = VideoGroupTitle()
                    hotTitle.text = "热门视频"
                    list?.add(hotTitle)
                    list?.addAll(data.hotVideos)

                    Glide.with(context).load(data.app_logo).apply(RequestOptions.circleCropTransform()).into(HomeFrag.frag.img_avatar )
                }
                adapter?.notifyDataSetChanged()

            })
        }
        curPage = page
        refresh_layout.refreshComplete()
    }

    override fun setEmptyViewVisible(visible: Boolean) {
    }

}