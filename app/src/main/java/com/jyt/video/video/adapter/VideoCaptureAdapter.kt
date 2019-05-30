package com.jyt.video.video.adapter

import android.support.design.card.MaterialCardView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.DensityUtil

class VideoCaptureAdapter : BaseRcvAdapter<String>() {
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<String>? {


        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_video_capture_item,viewGroup,false))
    }

    class ViewHolder(view: View): BaseVH<String>(view)
}