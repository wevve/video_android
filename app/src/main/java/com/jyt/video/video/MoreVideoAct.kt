package com.jyt.video.video

import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/video/more")
class MoreVideoAct:BaseAct(){

    var adapter: BaseVideoListAdapter? = null
    var curPage = 1

    var title:String? = null

    var typeId:String? = null

    override fun initView() {


        intent?.let {
            title = it.getStringExtra("title")
            typeId = it.getStringExtra("typeId")
        }


        init()

    }

    private fun init(){

        adapter = BaseVideoListAdapter()
        recycler_view.adapter = adapter

        var layoutManager = GridLayoutManager(this,2)
        recycler_view.layoutManager = layoutManager


        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData(1)
            }

            override fun onLoadingMore() {
                getData(curPage+1)
            }

        })

    }
    override fun getLayoutId(): Int {
        return R.layout.layout_refresh_recyclerview
    }

    fun getData(page: Int) {
        var list = ArrayList<Any>()
        if (page==1){
            adapter?.data?.clear()
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
        }else{
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
        }
        adapter?.data?.addAll(list)
        adapter?.notifyDataSetChanged()
        refresh_layout.refreshComplete()
    }

}
