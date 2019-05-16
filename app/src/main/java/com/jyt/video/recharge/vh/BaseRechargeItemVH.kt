package com.jyt.video.recharge.vh

import android.view.View
import com.jyt.video.common.base.BaseVH
import com.jyt.video.recharge.entity.RechargeItem

abstract class BaseRechargeItemVH(parent: View) : BaseVH<RechargeItem>(parent) {

    public var isCheck:Boolean = false
    set(value) {
        field = value
        data?.sel = value
        bindData(data)

    }

    abstract fun getPrice():Double
}