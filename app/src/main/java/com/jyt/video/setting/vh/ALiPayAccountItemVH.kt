package com.jyt.video.setting.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.setting.entity.AlipayAccount
import kotlinx.android.synthetic.main.vh_alipay_account_item.*

class ALiPayAccountItemVH : BaseVH<AlipayAccount> {

    constructor( parent:ViewGroup):super(LayoutInflater.from(parent.context).inflate(R.layout.vh_alipay_account_item,parent,false)) {
        tv_last_card_num.setOnClickListener {
            onTriggerListener?.onTrigger(this,"delete")
        }
    }

    override fun bindData(data: AlipayAccount?) {
        super.bindData(data)

        tv_account.text = data?.alipayAccount
        tv_name.text = "支付宝${this.adapterPosition+1}："
    }


    public fun setDeleteVisibility(show:Boolean){
        if (show){
            tv_last_card_num.visibility = View.VISIBLE
        }else{
            tv_last_card_num.visibility = View.GONE
        }
    }


}