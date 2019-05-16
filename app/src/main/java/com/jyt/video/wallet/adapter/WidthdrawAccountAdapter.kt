package com.jyt.video.wallet.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.wallet.vh.WidthdrawAccountItemVH

class WidthdrawAccountAdapter : BaseRcvAdapter<Any>() {
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        return WidthdrawAccountItemVH(viewGroup)
    }


}
