package com.jyt.video.promotion.vh

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.promotion.entity.PromotionUserListResult
import kotlinx.android.synthetic.main.vh_promotion_item.*
import java.text.SimpleDateFormat
import java.util.*

class PromotionItemVH(viewGroup: ViewGroup):BaseVH<Any>(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_promotion_item,viewGroup,false)){

    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun bindData(data: Any?) {
        super.bindData(data)

        data as PromotionUserListResult.ListBean
        tv_name.text=data.name

        tv_date.text = simpleDateFormat.format(Date( data.date*1000L))

    }
}