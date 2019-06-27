package com.jyt.video.home.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.Advertising
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.home.vh.*
import com.jyt.video.video.entity.ThumbVideo

class BaseVideoListAdapter:BaseRcvAdapter<Any>(){
    companion object{
        val TYPE_THUMB_VIDEO = 1
        val TYPE_BANNER = 2
        val TYPE_VIDEO_GROUP_TITLE = 3
        val TYPE_VIDEO_TYPE = 4
        val TYPE_ADVERTISING = 5
        val TYPE_EMPTY_HOLDER = 6
    }

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        return when(i){
            TYPE_THUMB_VIDEO->{
                ThumbVideoVH(viewGroup) as BaseVH<Any>
            }
            TYPE_BANNER->{
                BannerVH(viewGroup)
            }
            TYPE_VIDEO_GROUP_TITLE->{
                VideoGroupTitleVH(viewGroup)
            }
            TYPE_VIDEO_TYPE->{
                null
            }
            TYPE_ADVERTISING->{
                var holder = AdvertisingVH(viewGroup)
                holder.adapter = this
                holder
            }
            TYPE_EMPTY_HOLDER->{
                var holder = EmptyHolder(viewGroup)

                holder
            }
            else->{
                null
            }
        }
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, i: Int) {
        if (i>=data.size){

        }else{
            super.onBindViewHolder(holder, i)
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position>=data.size){
            if (position==data.size){
                return TYPE_EMPTY_HOLDER;
            }

        }else {
            var data = data[position]
            when (data) {
                is ThumbVideo -> {
                    return TYPE_THUMB_VIDEO
                }
                is Banner -> {
                    return TYPE_BANNER
                }
                is VideoGroupTitle -> {
                    return TYPE_VIDEO_GROUP_TITLE
                }
                is VideoType -> {
                    return TYPE_VIDEO_TYPE
                }
                is Advertising -> {
                    return TYPE_ADVERTISING
                }
            }
        }
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }
}