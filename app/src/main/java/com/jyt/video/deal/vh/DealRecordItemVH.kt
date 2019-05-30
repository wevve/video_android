package com.jyt.video.deal.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.deal.entity.Record
import kotlinx.android.synthetic.main.vh_deal_record_item.*
import java.text.SimpleDateFormat

class DealRecordItemVH(parent: ViewGroup) : BaseVH<Record>(LayoutInflater.from(parent.context).inflate(R.layout.vh_deal_record_item,parent,false)){

    var simpleDateFormat=SimpleDateFormat("yyyy-MM-dd")

    override fun bindData(data: Record?) {
        super.bindData(data)

        tv_type.text = data?.title

        tv_order_no.text = "订单号：${data?.orderID}"
        tv_mark.text = "备注：${data?.remark}"
        tv_date.text = simpleDateFormat.format((data?.date?:0)*1000)
        tv_content.text = data?.content

        tv_state.text = data?.status

        if (data?.status.isNullOrEmpty()){
            tv_state.visibility = View.GONE
        }else{
            tv_state.visibility = View.VISIBLE

        }
    }
}