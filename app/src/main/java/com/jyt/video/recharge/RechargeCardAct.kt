package com.jyt.video.recharge

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct

@Route(path="/recharge/card")
class RechargeCardAct :BaseAct(){
    override fun initView() {

    }

    override fun getLayoutId(): Int {
        return R.layout.act_recharge_card
    }

}