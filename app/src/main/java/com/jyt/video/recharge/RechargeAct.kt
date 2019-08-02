package com.jyt.video.recharge

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.frag.BaseRechargeFrag
import com.jyt.video.recharge.frag.RechargeCoinFrag
import com.jyt.video.recharge.frag.RechargeMemberFrag
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.WalletServiceImpl
import kotlinx.android.synthetic.main.act_recharge.*

@Route(path = "/recharge/member")
class RechargeAct:BaseAct(){

    var adapter:FragmentViewPagerAdapter? = null

    lateinit var walletService: WalletService

    companion object{
        var payway:ArrayList<PayWay>? = null
        var coinRate = 1.0
    }


    var startIndex:Int = 0
    override fun initView() {
        walletService = WalletServiceImpl()


        startIndex = intent.getIntExtra("index",0)

        adapter = FragmentViewPagerAdapter(supportFragmentManager)

        view_pager.adapter = adapter

        getData()

        getPayWay()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_recharge
    }


    private fun getData(){
        walletService.getRechargeInfo(ServiceCallback{
            code, data ->
            if (data!=null){
                var vip = RechargeMemberFrag()
                vip.items = data.vip
                vip.walletService = walletService
                adapter?.addFragment(vip,"购买会员")

                var coin =  RechargeCoinFrag()
                coin.items = data.corn
                coin.walletService = walletService
                coinRate = data.cornCal.toDouble()
                coin.coinMoneyRate = data.cornCal.toDouble()
                adapter?.addFragment(coin,"购买金币")

                adapter?.notifyDataSetChanged()

                tab_layout.setViewPager(view_pager)

                view_pager.currentItem = startIndex
            }

        })
    }


    private fun getPayWay(){
        walletService.getPayWayList(ServiceCallback{
            code, data ->
            payway = data
        })
    }
}