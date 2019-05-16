package com.jyt.video.promotion.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.promotion.vh.PromotionItemVH

class PromotionAdapter:BaseRcvAdapter<Any>(){
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        return PromotionItemVH(viewGroup)
    }

}