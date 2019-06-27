package com.jyt.video.home.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.VideoGroupTitle
import kotlinx.android.synthetic.main.vh_video_group_title.*

class VideoGroupTitleVH(parent: View) : BaseVH<Any>(LayoutInflater.from(parent.context).inflate(R.layout.vh_video_group_title,parent as ViewGroup,
    false)){

    init {
        tv_more.setOnClickListener(this)

//        tv_more.visibility = View.GONE
    }


    override fun bindData(data: Any?) {
        super.bindData(data)

        data as VideoGroupTitle
        if (!data.text.isNullOrEmpty()){
            tv_title.text = data.text
        }
        if (data.videos==null){
            tv_more.visibility = View.GONE
        }else{
            tv_more.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v){
            tv_more->{
                ARouter.getInstance().build("/video/more")
                    .withParcelable("data",(data as VideoGroupTitle))
                    .navigation()
            }
            else->{
                super.onClick(v)
            }
        }
    }
}

