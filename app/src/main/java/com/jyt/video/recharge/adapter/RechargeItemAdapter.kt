package com.jyt.video.recharge.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.recharge.entity.RechargeItem
import com.jyt.video.recharge.vh.RechargeInputItemVH
import com.jyt.video.recharge.vh.RechargeItemVH

class RechargeItemAdapter:BaseRcvAdapter<RechargeItem>(){

    companion object{
        val TYPE_INPUT = 1
        val TYPE_LABEL = 2
    }

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<RechargeItem>? {
        return when(i){
            TYPE_INPUT->{
                RechargeInputItemVH(viewGroup)
            }
//            TYPE_LABEL
            else->{
                RechargeItemVH(viewGroup)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        var item = data.get(position)
        if (item.canInput){
            return TYPE_INPUT
        }
        return TYPE_LABEL
    }

}