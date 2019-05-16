package com.jyt.video.video.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.vh.VideoCollectionItemVH

class CollectionVideoAdapter : BaseRcvAdapter<Any>() {

    var showCheckBox:Boolean = false


    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {

        var holder = VideoCollectionItemVH(viewGroup)
        return holder
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, i: Int) {
        super.onBindViewHolder(holder, i)
        if (holder is VideoCollectionItemVH){
            holder.showCheckBox(showCheckBox)
        }

    }
    private fun setCheckBoxVisible(showCheckBox:Boolean){
        this.showCheckBox = showCheckBox
    }


}
