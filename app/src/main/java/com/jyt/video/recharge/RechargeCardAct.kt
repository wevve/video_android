package com.jyt.video.recharge

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_recharge_card.*

@Route(path="/recharge/card")
class RechargeCardAct :BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_search->{

            }
            btn_recharge->{

            }
        }
    }

    override fun initView() {
        setListener()
    }

    private fun setListener(){
        btn_search.setOnClickListener(this)
        btn_recharge.setOnClickListener(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.act_recharge_card
    }

}