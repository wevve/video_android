package com.jyt.video.setting.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.setting.entity.BankCardAccount
import kotlinx.android.synthetic.main.vh_bank_card_account_item.*

class BankCardAccountItemVH:BaseVH<BankCardAccount>{
    fun setDeleteVisibility(showDelete: Boolean) {
        if (showDelete){
            tv_del.visibility = View.VISIBLE
        }else{
            tv_del.visibility = View.GONE
        }
    }

    constructor( parent: ViewGroup):super(LayoutInflater.from(parent.context).inflate(R.layout.vh_bank_card_account_item,parent,false)) {
        tv_del.setOnClickListener{
            onTriggerListener?.onTrigger(this,"delete")
        }
    }


}