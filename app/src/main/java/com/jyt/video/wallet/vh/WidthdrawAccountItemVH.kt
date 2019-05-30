package com.jyt.video.wallet.vh

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.setting.entity.WithdrawAccount
import kotlinx.android.synthetic.main.vh_widthdraw_account_item.*

class WidthdrawAccountItemVH(parent:ViewGroup):BaseVH<Any>(LayoutInflater.from(parent.context).inflate(R.layout.vh_widthdraw_account_item
,parent,false)){

    init {

    }

    override fun bindData(data: Any?) {
        super.bindData(data)
        data as WithdrawAccount

        tv_account.text = data.getDisplayName()
    }

}