package com.jyt.video.video

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.Advertising
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import kotlinx.android.synthetic.main.act_search.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/search/result")
class SearchResultAct: BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            search_view->{
                finish()
            }
            img_back2->{
                finish()
            }
        }

    }

    var keyWord:String? = null

    lateinit var videoService: VideoService

    lateinit var adapter: BaseVideoListAdapter
    override fun getLayoutId(): Int {
        return R.layout.act_search_result
    }

    override fun initView() {
        hideToolbar()
        videoService = VideoServiceImpl()

        search_view.setOnClickListener(this)
        img_back2.setOnClickListener(this)

        keyWord = intent.getStringExtra("keyWord")
        if(keyWord.isNullOrEmpty()){
            finish()
            return
        }
        search_view.setText(keyWord!!)
        initRcv()



        refresh_layout.autoRefresh()
    }

    private fun getData(){
        videoService.searchVideo(keyWord!!, ServiceCallback{
                code, data ->
            if (data!=null){
                adapter.data.clear()
                adapter.data.addAll(data?.list)
                adapter.notifyDataSetChanged()
            }
            if (adapter.data.size==0){
                empty_view.visibility = View.VISIBLE
            }else{
                empty_view.visibility = View.GONE
            }
            refresh_layout.refreshComplete()
        })
    }


    private fun initRcv(){
        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData()
            }

            override fun onLoadingMore() {
                refresh_layout.refreshComplete()
            }

        })
        adapter = BaseVideoListAdapter()
        recycler_view.adapter = adapter
        var layoutManager = GridLayoutManager(this,2)
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
                        is VideoType ->{
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

}
