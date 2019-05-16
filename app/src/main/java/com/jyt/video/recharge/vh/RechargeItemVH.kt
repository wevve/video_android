package com.jyt.video.recharge.vh

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.recharge.entity.RechargeItem
import kotlinx.android.synthetic.main.vh_recharge_item.*

class RechargeItemVH(viewGroup: ViewGroup):BaseRechargeItemVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_recharge_item,viewGroup,false)){
    override fun getPrice(): Double {
        return data?.price?:0.0
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

    }
}