package com.jyt.video.setting.account

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
import com.jyt.video.setting.entity.BankCardAccount
import com.jyt.video.setting.vh.AddAccountVH
import com.jyt.video.setting.vh.BankCardAccountItemVH
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/setting/account/bankcard")
class BankCardListAct:BaseAct(), View.OnClickListener,BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>> {

    var adapter = AccountAdapter()

    var accountService: AccountService? = null

    var curPage=1

    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is AddAccountVH->{
                ARouter.getInstance().build("/setting/account/bankcard/add").navigation()
            }
            is BankCardAccountItemVH->{
                var data = holder.data
                when(event){
                    "delete"->{
                        removeALiPayAccount(data!!)
                    }
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

    override fun onResume() {
        super.onResume()

        refresh_layout.autoRefresh()

    }

    override fun getLayoutId(): Int {
        return R.layout.act_account_list
    }

    override fun initView() {
        accountService = AccountServiceImpl()

        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
//                getData(curPage + 1)
                refresh_layout.refreshComplete()
            }

            override fun onRefreshing() {
                getData()
            }

        })
        adapter.addAccountText = "添加银行卡"
        adapter.setOnTriggerListener(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter


        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)
    }

    private fun getData(){
        accountService?.getBankCardList( ServiceCallback<List<BankCardAccount>>{
                code, data ->
            if (data!=null){
//                if(page==1){
                    adapter.data.clear()
//                }
                adapter.data.addAll(data)
                adapter.notifyDataSetChanged()
            }
            refresh_layout.refreshComplete()
        })
    }

    private fun removeALiPayAccount(account:BankCardAccount){
        accountService?.deleteBankCardAccount(account,ServiceCallback{
                code,data->
            if (code==BaseJson.CODE_SUCCESS) {
                adapter.data.remove(account)
                adapter.notifyDataSetChanged()
            }
        })
    }
}
