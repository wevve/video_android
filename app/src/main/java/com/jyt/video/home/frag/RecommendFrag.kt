package com.jyt.video.home.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.HomeVideoList
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_video_list_empty.*
import kotlin.collections.ArrayList

class RecommendFrag:BaseVideoListFrag(){


    override fun createAdapter(): BaseVideoListAdapter {
        return BaseVideoListAdapter()
    }



    override fun getData(page: Int) {

        if (page==1) {
            videoService.getHomeData(ServiceCallback { code, data ->
                videoService.videoHorAd(1,ServiceCallback{
                    _, adData ->

                    adapter?.data?.clear()
                    if (data != null) {
                        var newList = ArrayList<HomeVideoList>()

                        //后台不排 前端排序
                        data.videos.forEach {
                            if ("最新视频".equals(it.title)){
                                newList.add(it)
                            }
                        }
                        data.videos.forEach {
                            if ("热门视频".equals(it.title)){
                                newList.add(it)
                            }
                        }
                        data.videos.forEach {
                            if ("推荐视频".equals(it.title)){
                                newList.add(it)
                            }
                        }
                        data.videos = newList



                        var list = adapter?.data
                        var banner = Banner()
                        banner.data = data.banner
                        list?.add(banner)

                        if (adData!=null && adData.img?.isEmpty()==false)
                            list?.add(adData)
                        if (data.videos!=null) {
                            for (i in 0 until data.videos.size){
                                var vl = data.videos[i]
                                var hotTitle = VideoGroupTitle()
                                hotTitle.text = vl.title
                                hotTitle.videos = vl.list

                                list?.add(hotTitle)

                                var tempList = vl.list
                                if (vl.list.size>6) {
                                    tempList = ArrayList(vl.list.subList(0, 6))
                                }

                                list?.addAll(tempList)
                            }
                        }

                        HomeFrag.frag?.img_avatar?.let {
                            Glide.with(context).load(data.app_logo).apply(RequestOptions.circleCropTransform()).into(HomeFrag.frag.img_avatar )
                        }
                    }
                    if (adapter?.data?.isEmpty()==false){
                        setEmptyViewVisible(false)
                    }else{
                        setEmptyViewVisible(true)
                    }
                    adapter?.notifyDataSetChanged()
                })


            })
        }
        curPage = page
        refresh_layout.refreshComplete()
    }

    override fun setEmptyViewVisible(visible: Boolean) {
        empty_view?.visibility = if (visible){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}