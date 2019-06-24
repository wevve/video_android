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
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.adapter.TabAdapter
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.entity.VideoType
import com.jyt.video.home.vh.TabVH
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import kotlinx.android.synthetic.main.frag_home.*
import java.util.ArrayList


class HomeFrag:BaseFrag(),BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>>, View.OnClickListener {

    companion object{
        var videoType:ArrayList<VideoType.TypeGroup>? = null
        lateinit var frag:HomeFrag
    }
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
            img_download->{
                if (!UserInfo.isLogin()){
                    ToastUtil.showShort(context,"请先登录")
                    ARouter.getInstance().build("/login/index").navigation()
                    return
                }
                ARouter.getInstance().build("/video/cache").navigation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frag = this
        init()
    }
    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is TabVH->{
//                curTab?.sel = false
//                holder.data?.sel = true

                curTab = holder.data

//                tabAdapter?.notifyDataSetChanged()
//                ll_home_root.removeAllViews()

                var tabFrag = TypeVideoFrag()
                tabFrag.tab = curTab!!
                tabFrag.parentFrag = this


                childFragmentManager.beginTransaction()
                    .replace(R.id.child_video,tabFrag)
                    .addToBackStack("type")
                    .commit()
            }
        }
    }
    private fun init(){
        videoService = VideoServiceImpl()

        getHomeTab()
        getTabFilterData()
        search_view.setOnClickListener(this)
        img_download.setOnClickListener(this)
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

    override fun onResume() {
        super.onResume()

//        if (UserInfo.isLogin()){
//            Glide.with(this).load(UserInfo.getUserHomeInfo().avatar).apply(GlideHelper.avatar()).into(img_avatar)
//        }else{
//            img_avatar.setImageDrawable(resources.getDrawable(R.mipmap.default_avatar))
//        }
    }

    private fun getHomeTab(){
        videoService.getHomeTab(ServiceCallback{
            code, data ->
//            var recommend = TabEntity("推荐")
//            recommend.sel = true
//            tabAdapter?.data?.add(recommend)
            if (data!=null){
                tabAdapter?.data?.addAll(data.homeTab)
            }
            tabAdapter?.notifyDataSetChanged()
        })
    }


    private fun getTabFilterData(){
        videoService.getHomeVideoType(ServiceCallback{
            code, data ->
            videoType = data

            var type = VideoType()
            var tg =  type.TypeGroup()
            tg.name = "排序"
            var zuixin = type.Type()
            zuixin.id = 0
            zuixin.name = "最新"
            var zuire = type.Type()
            zuire.id = 1
            zuire.name = "热门"

            var zuiduo = type.Type()
            zuiduo.id = 2
            zuiduo.name = "点赞"

            tg.items = arrayListOf(zuixin,zuire,zuiduo)
            videoType?.add(tg)
        })
    }

}