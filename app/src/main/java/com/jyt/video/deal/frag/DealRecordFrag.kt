package com.jyt.video.deal.frag

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.deal.adapter.DealRecordAdapter
import com.jyt.video.deal.entity.Record
import com.jyt.video.service.RecordService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.RecordServiceImpl
import kotlinx.android.synthetic.main.frag_deal_record.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

class DealRecordFrag:BaseFrag(){

    var adapter:DealRecordAdapter? = null

    var lastId = "0"

    var typeId = 0

    lateinit var recordService: RecordService
    override fun getLayoutId(): Int {
        return R.layout.frag_deal_record
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DealRecordAdapter()
        recordService = RecordServiceImpl()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(lastId)
            }

            override fun onRefreshing() {
                getData("0")
            }

        })

    }

    override fun onStart() {
        super.onStart()

        refresh_layout.autoRefresh()

    }


    private fun getData(lastId:String){


        recordService.getDealRecordList(typeId,lastId, ServiceCallback {
                code, data ->
            if (data!=null){
                if (lastId=="0"){
                    adapter?.data?.clear()
                }
                adapter?.data?.addAll(data)
                adapter?.notifyDataSetChanged()


                if (adapter?.data?.isEmpty()==true){
                    ll_empty?.visibility = View.VISIBLE

                }else{
                    ll_empty?.visibility = View.GONE

                    this@DealRecordFrag.lastId =( adapter?.data?.last() as Record?)?.orderID?:"0"
                }
            }else{
                ll_empty?.visibility = View.VISIBLE

            }

            refresh_layout?.refreshComplete()

        })
//        adapter?.data?.add("2010-10-10")
//        adapter?.data?.add(Record())
//        adapter?.data?.add(Record())
//        adapter?.data?.add(Record())
//
//        adapter?.notifyDataSetChanged()
//
//        if (adapter?.data?.size==0){
//            ll_empty.visibility = View.VISIBLE
//        }else{
//            ll_empty.visibility = View.GONE
//        }
//
//        refresh_layout.refreshComplete()
    }
}