package com.jyt.video.video

import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.Advertising
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/video/more")
class MoreVideoAct:BaseAct(){

    var adapter: BaseVideoListAdapter? = null
    var curPage = 1

    var title:String? = null

    var typeId:String? = null

    var videos: VideoGroupTitle? = null


    override fun initView() {


        intent?.let {
            title = it.getStringExtra("title")
//            typeId = it.getStringExtra("typeId")
            videos = it.getParcelableExtra<VideoGroupTitle>("data")

            tv_title?.text = videos?.text
        }


        init()


        getData()
    }

    private fun init(){

        adapter = BaseVideoListAdapter()
        recycler_view.adapter = adapter

        var layoutManager = GridLayoutManager(this,2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                if (adapter?.data?.isEmpty() == true|| p0>=adapter?.data?.size?:0) {
                    return 2
                } else {
                    var data = adapter?.data?.get(p0)
                    return when (data) {
                        is Banner,
                        is VideoGroupTitle,
                        is Advertising,
                        is VideoType -> {
                            2
                        }
                        else -> {
                            1
                        }
                    }
                }

            }
        }



        recycler_view.layoutManager = layoutManager


        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
//                getData(1)
                refresh_layout.refreshComplete()

            }

            override fun onLoadingMore() {
//                getData(curPage+1)
                refresh_layout.refreshComplete()

            }

        })

    }
    override fun getLayoutId(): Int {
        return R.layout.act_more_video
    }

    fun getData() {
        if (videos!=null) {
            adapter?.data?.clear()
            adapter?.data?.addAll(videos?.videos!!)
        }
        adapter?.notifyDataSetChanged()
        refresh_layout.refreshComplete()
    }

}
