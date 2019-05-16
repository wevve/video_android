package com.jyt.video.deal.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.deal.entity.Record
import com.jyt.video.deal.vh.DateVH
import com.jyt.video.deal.vh.DealRecordItemVH

class DealRecordAdapter:BaseRcvAdapter<Any>(){

    companion object{
        public val TYPE_DATE = 1
        public val TYPE_RECORD = 2

    }

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {

        when(i){
            TYPE_DATE->{
                return DateVH(viewGroup) as BaseVH<Any>
            }
            TYPE_RECORD->{
                return DealRecordItemVH(viewGroup) as BaseVH<Any>
            }
        }
        return null
    }


    override fun getItemViewType(position: Int): Int {
        var data = data[position]
        when(data){
            is String->{
                return TYPE_DATE
            }
            is Record->{
                return TYPE_RECORD
            }
        }
        return super.getItemViewType(position)
    }
}