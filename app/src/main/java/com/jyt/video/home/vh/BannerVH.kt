package com.jyt.video.home.vh

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.DensityUtil
import com.jyt.video.home.entity.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

class BannerVH(parent: View) : BaseVH<Any>(com.youth.banner.Banner(parent.context)) {

    init {
        itemView as com.youth.banner.Banner
        itemView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        var params =  ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dpToPx(itemView.context,177))
        itemView.layoutParams = params

        itemView.setImageLoader(object :ImageLoader(){
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {

                var option = RequestOptions()
                option.centerCrop()
                Glide.with(context).load((path as Banner.Data).images_url).apply(option).into(imageView)
            }

        })
        itemView.setOnBannerListener {
                ARouter.getInstance().build("/web/index").withString("url",(data as Banner).data[it].url).navigation()
        }

    }


    override fun bindData(data: Any?) {
        super.bindData(data)
        data as Banner
        itemView as com.youth.banner.Banner

        itemView.setImages(data.data)
        itemView.start()
    }


}