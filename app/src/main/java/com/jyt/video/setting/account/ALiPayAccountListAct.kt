package com.jyt.video.setting.account

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.entity.BaseJson
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
import java.io.Serializable

@Route(path = "/setting/account/alipay")
class ALiPayAccountListAct:BaseAct(), View.OnClickListener, BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>> {

    var adapter = AccountAdapter()

    var accountService:AccountService? = null



    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is AddAccountVH->{
                ARouter.getInstance().build("/setting/account/alipay/add").navigation()
            }
            is ALiPayAccountItemVH->{
                var data = holder.data
                when (event){
                    "delete"->
                        removeALiPayAccount(data!!)
                    "itemClick"->{

                        ARouter.getInstance().build("/setting/account/alipay/add")
                            .withSerializable("alc",data).navigation()
                    }

                }

            }
        }
    }

    override fun onResume() {
        super.onResume()


        refresh_layout.autoRefresh()

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
//                getData(curPage + 1)
                refresh_layout.refreshComplete()
            }

            override fun onRefreshing() {
                getData()
            }

        })
        adapter.addAccountText = "添加支付宝"
        adapter.setOnTriggerListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter


        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_account_list
    }

    private fun getData(){
        accountService?.getALiPayList( ServiceCallback<List<AlipayAccount>>{
                code, data ->
            if (data!=null){
                adapter.hideDelete()
//                if(page==1){
                    adapter.data.clear()
//                }
//                curPage = page
                adapter.data.addAll(data)
                adapter.notifyDataSetChanged()
            }
            refresh_layout.refreshComplete()
        })
    }

    private fun removeALiPayAccount(account:AlipayAccount){
        accountService?.deleteALiPayAccount(account,ServiceCallback{
            code,data->
            if (code==BaseJson.CODE_SUCCESS) {
                adapter.data.remove(account)
                adapter.notifyDataSetChanged()
            }
        })
    }
}