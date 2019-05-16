package com.jyt.video.home.frag

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.Advertising
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

abstract class BaseVideoListFrag:BaseFrag(){

    var adapter:BaseVideoListAdapter? = null
    var curPage = 1

    lateinit var videoService: VideoService

    override fun getLayoutId(): Int {
        return R.layout.frag_base_video_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoService = VideoServiceImpl()
        init()


        refresh_layout.autoRefresh()
    }

    private fun init(){

        adapter = createAdapter()
        recycler_view.adapter = adapter

        var layoutManager = GridLayoutManager(context,2)
        recycler_view.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(p0: Int): Int {

                var data = adapter?.data?.get(p0)

               return when(data){
                    is Banner,
                    is VideoGroupTitle,
                    is Advertising,
                    is VideoType->{
                        2
                    }
                   else->{
                       1
                   }
                }
            }

        }

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData(1)
            }

            override fun onLoadingMore() {
                getData(curPage+1)
            }

        })

    }

    abstract fun createAdapter():BaseVideoListAdapter

    abstract fun getData(page:Int)


    abstract fun setEmptyViewVisible(visible:Boolean)

}