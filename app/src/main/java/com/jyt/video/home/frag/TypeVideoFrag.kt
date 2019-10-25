package com.jyt.video.home.frag

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.api.entity.FilterVideoListResult
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.*
import com.jyt.video.home.widget.TypeFilterView
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.frag_type_video.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

class TypeVideoFrag:BaseFrag(), View.OnClickListener {


    lateinit var tab:TabEntity
    lateinit var videoService: VideoService
    var parentFrag:Fragment? = null

    var curPage = 1L

    lateinit var adapter:BaseVideoListAdapter

    var filterMap = HashMap<String,String>()
    override fun onClick(v: View?) {
        when(v){
            img_back->{
                parentFrag?.childFragmentManager?.popBackStack()
            }
            search_view->{
                ARouter.getInstance().build("/video/search").navigation()
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_type_video
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoService = VideoServiceImpl()
        setListener()

        initFileTab()


        refresh_layout.autoRefresh()
    }


    private fun setListener(){
        img_back.setOnClickListener(this)
        search_view.setOnClickListener(this)

        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData(1)
            }

            override fun onLoadingMore() {
                getData((curPage+1).toLong())
            }

        })
        adapter = BaseVideoListAdapter()
        recycler_view.adapter = adapter
        var layoutManager = GridLayoutManager(context,2)
        recycler_view.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(p0: Int): Int {

                if (adapter?.data?.isEmpty()|| p0>=adapter?.data?.size?:0){
                    return 2
                }else{
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

        }

    }
    var  addToMap = {
            typeName:String,type:VideoType.Type?, subType:VideoType.Type? ->

        var typeId = type?.id
        var subTypeId = subType?.id
        when(typeName){
            "分类"->{
                filterMap["cid"] = typeId.toString()
                if (subTypeId!=null){
                    if (subType?.name=="全部") {
                        filterMap["sub_cid"] = "0"

                    }else{
                        filterMap["sub_cid"] = subTypeId.toString()

                    }
                }else{
                    if (filterMap.containsKey("sub_cid")) {
                        filterMap.remove("sub_cid")
                    }
                }
            }
            "类型"->{
                filterMap["tag_id"] = typeId.toString()
            }
            "区域"->{
                filterMap["area_id"] = typeId.toString()
            }
            "排序"->{
                when(typeId){
                    0->{
                        filterMap["orderCode"] = "lastTime"
                    }
                    1->{
                        filterMap["orderCode"] = "hot"
                    }
                    2->{
                        filterMap["orderCode"] = "good"
                    }
                    3->{
                        filterMap["orderCode"] = "reco"

                    }
                }
            }
        }
    }
    private fun initFileTab(){
        var  typeClickListener ={
                typeName:String,type:VideoType.Type?, subType:VideoType.Type? ->

//            if (type?.name=="全部"){
//                parentFrag?.childFragmentManager?.popBackStack()
//            }else{
                addToMap.invoke(typeName,type,subType)
                getData(1)
//            }
        }

        HomeFrag.videoType?.forEach {
                tg->



            var tfv = TypeFilterView(context!!)
            tfv.typeClickListener = typeClickListener
            tfv.tab = tab
            ll_video_type.addView(tfv)
            tfv.setData(tg)
            if (tg.name=="分类" && tab!=null) {
                tg.items.forEach {
                    if (it.id.toLong()==tab.tabId){
                        addToMap.invoke(tg.name,it,it.subItem?.firstOrNull())
                    }
                }
            }else{
                addToMap.invoke(tg.name,tg.items?.firstOrNull(),tg.items?.firstOrNull()?.subItem?.firstOrNull())
            }

        }


    }


    private fun getData(page:Long){



        videoService.getVideoAfterFilter(filterMap,page, ServiceCallback{
                code, data ->
            videoService.videoHorAd(2,ServiceCallback {
                    _, ad ->


                if (data!=null ){
                    if (page==1L){
                        adapter?.data?.clear()
                        if (ad!=null && ad.img?.isEmpty()==false){
                            adapter.data.add(ad)
                        }
                    }
                    if(data.videolist?.isEmpty()==false){
                        adapter?.data?.addAll( data.videolist!!)
                    }
                    adapter?.notifyDataSetChanged()
                    curPage = page
                }

                if (isVideoEmpty()){
                    ll_empty?.let {
                        ll_empty.visibility = View.VISIBLE
                    }
                }else{
                    ll_empty?.let {
                        ll_empty.visibility = View.GONE
                    }
                }
                refresh_layout?.let {
                    refresh_layout.refreshComplete()
                }

            })

        })

    }


    private fun isVideoEmpty():Boolean{
        adapter?.data?.forEach {
            if(it is ThumbVideo){
                return false
            }
        }

        return true
    }

}