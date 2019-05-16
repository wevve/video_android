package com.jyt.video.home.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.vh_thumb_video.*
import java.util.*


class ThumbVideoVH(parent: View) : BaseVH<ThumbVideo>(LayoutInflater.from(parent.context).inflate(R.layout.vh_thumb_video,parent as ViewGroup,false)) {



    override fun onClick(v: View?) {
        when(v){
            itemView->{
                ARouter.getInstance().build("/video/play").navigation()
            }
            else->{
                super.onClick(v)
            }
        }
    }

    override fun bindData(data: ThumbVideo?) {
        super.bindData(data)

        Glide.with(itemView.context).load(data?.thumbnail).apply(GlideHelper.centerCrop()).into(img_cover)
        tv_duration.text = data?.play_time
        tv_title.text = data?.title
        tv_price.text = data?.gold.toString()
        tv_read_count.text = data?.click.toString()


        val calendar = Calendar.getInstance()
        calendar.time = Date((data?.update_time?:0)*1000)
        var year = calendar.get(Calendar.YEAR)
        var month  = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DATE)
        tv_create_time.text = "$year-$month-$day"

    }
}