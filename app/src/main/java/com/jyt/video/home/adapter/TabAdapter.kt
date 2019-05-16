package com.jyt.video.home.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.vh.TabVH

class TabAdapter:BaseRcvAdapter<TabEntity>(){
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<TabEntity>? {
        return TabVH(viewGroup)
    }

}