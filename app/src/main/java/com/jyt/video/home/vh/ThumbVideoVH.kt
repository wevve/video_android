package com.jyt.video.home.vh

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.PlayVideoAct
import com.jyt.video.video.entity.ThumbVideo
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.vh_thumb_video.*
import java.io.Serializable
import java.util.*


class ThumbVideoVH(parent: View) : BaseVH<ThumbVideo>(LayoutInflater.from(parent.context).inflate(R.layout.vh_thumb_video,parent as ViewGroup,false)) {



    override fun onClick(v: View?) {
        when(v){
            itemView->{
                Logger.d("click")
                var appCxt = itemView.context.applicationContext
                var intent = Intent()
//                intent.putExtra("thumbVideo",data as Serializable)
                intent.putExtra("videoId",data?.id?:0)
                intent.setClass(appCxt,PlayVideoAct::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                appCxt.startActivity(intent)
////
//                ARouter.getInstance().build("/video/play").withLong("videoId",data?.id?:0)
//                   .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP ).navigation()
            }
            else->{
                super.onClick(v)
            }
        }
    }

    override fun bindData(data: ThumbVideo?) {
        super.bindData(data)
        var options = GlideHelper.centerCrop()
        options.placeholder(R.mipmap.video_holder)
        Glide.with(itemView.context).load(data?.thumbnail).apply(options).into(img_cover)
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