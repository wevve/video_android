package com.jyt.video.deal

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
        adapter = FragmentViewPagerAdapter(supportFragmentManager)
        adapter?.addFragment(DealRecordFrag(),"消费明细")
        adapter?.addFragment(DealRecordFrag(),"充值明细")
        adapter?.addFragment(DealRecordFrag(),"提现")
        adapter?.addFragment(DealRecordFrag(),"推广金币")
        adapter?.addFragment(DealRecordFrag(),"提成收入")
        view_pager.adapter=adapter
        tab_layout.setViewPager(view_pager)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_deal_record
    }

}