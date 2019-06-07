package com.jyt.video.deal

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.deal.frag.DealRecordFrag
import kotlinx.android.synthetic.main.act_deal_record.*
import kotlinx.android.synthetic.main.layout_full_tablayout.*

@Route(path = "/deal/record")
class DealRecordAct:BaseAct(){

    var adapter:FragmentViewPagerAdapter? = null

    override fun initView() {
        tab_layout.setBackgroundColor(Color.WHITE)

        adapter = FragmentViewPagerAdapter(supportFragmentManager)

        var xfmx = DealRecordFrag()
        xfmx.typeId = 0
        adapter?.addFragment(xfmx,"消费明细")

        var czmx = DealRecordFrag()
        czmx.typeId = 1
        adapter?.addFragment(czmx,"充值明细")

        var tx = DealRecordFrag()
        tx.typeId = 2
        adapter?.addFragment(tx,"提现")

        var tgjb = DealRecordFrag()
        tgjb.typeId = 3
        adapter?.addFragment(tgjb,"推广金币")

        var tcsr = DealRecordFrag()
        tcsr.typeId = 4
        adapter?.addFragment(tcsr,"提成收入")
        view_pager.adapter=adapter
        tab_layout.setViewPager(view_pager)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_deal_record
    }

}