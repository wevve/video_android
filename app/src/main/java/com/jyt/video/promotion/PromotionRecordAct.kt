package com.jyt.video.promotion

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.api.Constans
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.promotion.adapter.PromotionAdapter
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_promotion_record.*
import kotlinx.android.synthetic.main.act_web.*
import kotlinx.android.synthetic.main.act_web.refresh_layout
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/promotion/record")
class PromotionRecordAct:BaseAct(){


    var curPage = 1

    var adapter:PromotionAdapter?=null

    lateinit var userService: UserService

    override fun initView() {
        userService = UserServiceImpl()
        adapter = PromotionAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
//                getData(curPage+1)
                refresh_layout.refreshComplete()
            }

            override fun onRefreshing() {
                getData(1)
            }

        })

        refresh_layout.autoRefresh()

        tv_to_promotion.setOnClickListener {
            val uri = Uri.parse(Constans.BaseUrl+"/appapi/shareUrl/pid/"+ UserInfo.getUserId())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_promotion_record
    }


    private fun getData(page:Int){

//        adapter?.data?.add(Any())
//        adapter?.data?.add(Any())
//        adapter?.notifyDataSetChanged()

        userService.getPromotionUserList(ServiceCallback{
            code, data ->
            if (data!=null){
                tv_total_people.text = data.total.toString()

                tv_today_people.text = data.today.toString()

                adapter?.data?.clear()
                adapter?.data?.addAll(data.list)
                adapter?.notifyDataSetChanged()
            }

            if(adapter?.data?.size!=0){
                ll_empty.visibility = View.GONE
            }else{
                ll_empty.visibility = View.VISIBLE
            }

            refresh_layout.refreshComplete()

        })



    }
}
