package com.jyt.video.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.entity.Gift
import kotlinx.android.synthetic.main.vh_award_item.*

class AwardDialogAdapter : BaseRcvAdapter<Any>() {
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        var holder = Holder(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_award_item,viewGroup,false))
        return holder;
    }


    inner class Holder(view: View):BaseVH<Any>(view){

        override fun bindData(data: Any?) {
            super.bindData(data)
            data as Gift

            Glide.with(itemView).load(data.images).into(img_gift)
            tv_gift.text = "${data.name}(¥${data.price})"
        }
    }
}