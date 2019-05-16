package com.jyt.video.home.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import kotlinx.android.synthetic.main.vh_video_group_title.*

class VideoGroupTitleVH(parent: View) : BaseVH<Any>(LayoutInflater.from(parent.context).inflate(R.layout.vh_video_group_title,parent as ViewGroup,
    false)){

    init {
        tv_more.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            tv_more->{
                ARouter.getInstance().build("/video/more").navigation()
            }
            else->{
                super.onClick(v)
            }
        }
    }
}

