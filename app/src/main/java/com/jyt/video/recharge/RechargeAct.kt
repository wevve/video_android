package com.jyt.video.recharge

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.recharge.frag.BaseRechargeFrag
import com.jyt.video.recharge.frag.RechargeCoinFrag
import com.jyt.video.recharge.frag.RechargeMemberFrag
import kotlinx.android.synthetic.main.act_recharge.*

@Route(path = "/recharge/member")
class RechargeAct:BaseAct(){

    var adapter:FragmentViewPagerAdapter? = null

    override fun initView() {
        adapter = FragmentViewPagerAdapter(supportFragmentManager)
        adapter?.addFragment(RechargeMemberFrag(),"会员充值")
        adapter?.addFragment(RechargeCoinFrag(),"金币充值")
        view_pager.adapter = adapter
        tab_layout.setViewPager(view_pager)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_recharge
    }

}