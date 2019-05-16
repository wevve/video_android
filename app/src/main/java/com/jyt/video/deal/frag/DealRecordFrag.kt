package com.jyt.video.deal.frag

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.deal.adapter.DealRecordAdapter
import com.jyt.video.deal.entity.Record
import kotlinx.android.synthetic.main.frag_deal_record.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

class DealRecordFrag:BaseFrag(){

    var adapter:DealRecordAdapter? = null

    var curPage = 1
    override fun getLayoutId(): Int {
        return R.layout.layout_refresh_recyclerview
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DealRecordAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(curPage+1)
            }

            override fun onRefreshing() {
                getData(1)
            }

        })
        refresh_layout.autoRefresh()

    }


    private fun getData(page:Int){

        adapter?.data?.add("2010-10-10")
        adapter?.data?.add(Record())
        adapter?.data?.add(Record())
        adapter?.data?.add(Record())

        adapter?.notifyDataSetChanged()

        if (adapter?.data?.size==0){
            ll_empty.visibility = View.VISIBLE
        }else{
            ll_empty.visibility = View.GONE
        }

        refresh_layout.refreshComplete()
    }
}