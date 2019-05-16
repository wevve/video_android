package com.jyt.video.setting.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH

class AddAccountVH: BaseVH<Any> {

    constructor(parent: ViewGroup):super(LayoutInflater.from(parent.context).inflate(R.layout.vh_add_account_item,parent,false)){



    }
}