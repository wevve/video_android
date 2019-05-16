package com.jyt.video.deal.vh

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH

class DateVH(viewGroup: ViewGroup):BaseVH<String>(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_record_date,viewGroup,false)){
    override fun bindData(data: String?) {
        super.bindData(data)

    }
}