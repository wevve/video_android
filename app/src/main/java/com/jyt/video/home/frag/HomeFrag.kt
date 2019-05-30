package com.jyt.video.home.frag

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import android.support.v4.view.ViewCompat.dispatchApplyWindowInsets
import android.view.WindowInsets
import android.os.Build
import android.annotation.TargetApi
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.adapter.TabAdapter
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.vh.TabVH
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import kotlinx.android.synthetic.main.frag_home.*
import java.util.ArrayList


class HomeFrag:BaseFrag(),BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>>, View.OnClickListener {

    lateinit var videoService: VideoService

    var tab = ArrayList<TabEntity>()


    var tabAdapter:TabAdapter? = null

    var curTab:TabEntity? = null
    override fun getLayoutId(): Int {
        return R.layout.frag_home
    }
    override fun onClick(v: View?) {
        when(v){
            search_view->{
                ARouter.getInstance().build("/video/search").navigation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is TabVH->{
                curTab?.sel = false

                holder.data?.sel = true

                curTab = holder.data

                tabAdapter?.notifyDataSetChanged()
            }
        }
    }
    private fun init(){
        videoService = VideoServiceImpl()
        getHomeTab()
        search_view.setOnClickListener(this)

//        tab.add(TabEntity("推荐"))
//        tab.add(TabEntity("推荐1"))
//        tab.add(TabEntity("推荐2"))
//        tab.add(TabEntity("推荐3"))
//        tab.add(TabEntity("推荐4"))
//        tab.add(TabEntity("推荐5"))

        tabAdapter = TabAdapter()
        tabAdapter?.setOnTriggerListener(this)
        tab_layout.adapter = tabAdapter
        tab_layout.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        childFragmentManager.beginTransaction().replace(R.id.fl_content,RecommendFrag()).commit()
    }


    private fun getHomeTab(){
        videoService.getHomeTab(ServiceCallback{
            code, data ->
            var recommend = TabEntity("推荐")
            recommend.sel = true
            tabAdapter?.data?.add(recommend)
            if (data!=null){
                tabAdapter?.data?.addAll(data.homeTab)
            }
            tabAdapter?.notifyDataSetChanged()
        })
    }


}