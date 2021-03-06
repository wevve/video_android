package com.jyt.video.setting.account

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.setting.adapter.AccountAdapter
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.vh.ALiPayAccountItemVH
import com.jyt.video.setting.vh.AddAccountVH
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/setting/account/alipay")
class ALiPayAccountListAct:BaseAct(), View.OnClickListener, BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>> {

    var adapter = AccountAdapter()

    var accountService:AccountService? = null

    var curPage=1


    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is AddAccountVH->{
                ARouter.getInstance().build("/setting/account/alipay/add").navigation()
            }
            is ALiPayAccountItemVH->{
                var data = holder.data
                if (event=="delete"){
                    removeALiPayAccount(data!!)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
                if (tv_right_function.text=="编辑"){
                    tv_right_function.text = "取消"
                    adapter.showDelete()
                }else{
                    tv_right_function.text = "编辑"
                    adapter.hideDelete()

                }
            }
        }
    }

    override fun initView() {
        accountService = AccountServiceImpl()

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(curPage + 1)
            }

            override fun onRefreshing() {
                getData(1)
            }

        })
        adapter.setOnTriggerListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        refresh_layout.autoRefresh()

        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_refresh_recyclerview
    }

    private fun getData(page:Int){
        accountService?.getALiPayList(page, ServiceCallback<List<AlipayAccount>>{
                code, data ->
            if (data!=null){
                if(page==1){
                    adapter.data.clear()
                }
                curPage = page
                adapter.data.addAll(data)
                adapter.notifyDataSetChanged()
            }
            refresh_layout.refreshComplete()
        })
    }

    private fun removeALiPayAccount(account:AlipayAccount){
        accountService?.deleteALiPayAccount(account,ServiceCallback{
            code,data->
            adapter.data.remove(account)
            adapter.notifyDataSetChanged()
        })
    }
}