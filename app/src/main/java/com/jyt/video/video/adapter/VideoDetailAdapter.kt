package com.jyt.video.video.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.entity.CommentItem
import com.jyt.video.video.entity.VideoDetail
import com.jyt.video.video.entity.VideoInfoBean
import com.jyt.video.video.vh.CommentVH
import com.jyt.video.video.vh.IntroduceHeaderVH

class VideoDetailAdapter: BaseRcvAdapter<Any>() {
    companion object{
        val TYPE_INTRODUCE = 0
        val TYPE_COMMENT = 1
    }


    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        when(i){
            TYPE_INTRODUCE->{
                return IntroduceHeaderVH(viewGroup)
            }
            TYPE_COMMENT->{
                return CommentVH(viewGroup)
            }

        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        var data = data[position]
        when (data){
            is VideoDetail->{
                return TYPE_INTRODUCE
            }
            is CommentItem->{
                return TYPE_COMMENT
            }

        }
        return super.getItemViewType(position)
    }

}