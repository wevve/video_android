package com.jyt.video.promotion

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.promotion.adapter.PromotionAdapter
import kotlinx.android.synthetic.main.act_promotion_record.*
import kotlinx.android.synthetic.main.act_web.*
import kotlinx.android.synthetic.main.act_web.refresh_layout
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/promotion/record")
class PromotionRecordAct:BaseAct(){


    var curPage = 1

    var adapter:PromotionAdapter?=null


    override fun initView() {
        adapter = PromotionAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(curPage+1)
            }

            override fun onRefreshing() {
                getData(1)
            }

        })

//        refresh_layout.autoRefresh()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_promotion_record
    }


    private fun getData(page:Int){

        adapter?.data?.add(Any())
        adapter?.data?.add(Any())
        adapter?.notifyDataSetChanged()

        if(adapter?.data?.size!=0){
            ll_empty.visibility = View.GONE
        }else{
            ll_empty.visibility = View.VISIBLE
        }

        refresh_layout.refreshComplete()
    }
}
