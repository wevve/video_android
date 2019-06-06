package com.jyt.video.recharge.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.recharge.entity.RechargeItem
import kotlinx.android.synthetic.main.vh_recharge_item.*

class RechargeItemVH(viewGroup: ViewGroup):BaseRechargeItemVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_recharge_item,viewGroup,false)){
    override fun getPrice(): Double {
        return (data?.money?:"0").toDouble()
    }

    init {

    }

    override fun bindData(data: RechargeItem?) {
        super.bindData(data)
        v_background.background = if (data?.sel==true){
            itemView.resources.getDrawable(R.drawable.shape_recharge_item_sel)
        }else{
            itemView.resources.getDrawable(R.drawable.shape_recharge_item_nor)
        }


        tv_discount.visibility = View.GONE
//        if (data?.tips)
//        tv_discount.text = data?.tips
        tv_title.text = data?.title

        if (isVip()){
            if (data?.time=="0"){
                tv_content.text = "VIP 永久"

            }else{
                tv_content.text = "VIP ${data?.time}天"

            }
            tv_price.text = data?.money
            tv_unit.visibility = View.VISIBLE
        }else{
            tv_unit.visibility = View.GONE
            tv_price.text = data?.corn
            tv_content.text = "¥ ${data?.money}"
        }

    }

    private fun isVip():Boolean{
        return data?.vipId!=null && data?.vipId!=0L
    }

}